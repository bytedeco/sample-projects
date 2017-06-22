package streaming_chunker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import streaming_chunker.simplified_grabber.SimplifiedGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class StreamingChunker {

    private final static long MIN_CHUNK_DURATION = 20000000;

    private Consumer<File> chunkHandler = null;
    private final FpsCalculator fpsCalculator = new FpsCalculator();
    private final ChunkDetector chunkDetector;
    private boolean hasVideo = false;
    private double fps = 25;
    private final Supplier<SimplifiedGrabber> grabberSupplier;
    private SimplifiedGrabber grabber = null;
    private FrameRecorder recorder = null;
    private final List<Frame> probeFrames = new ArrayList<>();
    private boolean started = false;
    private long begin = -1;

    public StreamingChunker(Supplier<SimplifiedGrabber> grabberSupplier, File targetDir) {
        this(grabberSupplier, targetDir, MIN_CHUNK_DURATION);
    }

    public StreamingChunker(Supplier<SimplifiedGrabber> grabberSupplier, File targetDir, long duration) {
        Objects.requireNonNull(grabberSupplier);
        Objects.requireNonNull(targetDir);
        if (duration < MIN_CHUNK_DURATION) {
            throw new IllegalArgumentException();
        }
        targetDir.mkdirs();
        if (!targetDir.canWrite()) {
            throw new RuntimeException("Target directory isn't writable.");
        }
        this.grabberSupplier = grabberSupplier;

        chunkDetector = new ChunkDetector(duration);
        chunkDetector.setChunkListener(new ChunkListener() {

            File outputFile;

            @Override
            public void onChunkBegin() throws Exception {
                if (hasVideo) {
                    fps = fpsCalculator.getFpsRounded();
                    fpsCalculator.reset();
                }

                outputFile = new File(targetDir, (begin + chunkDetector.getCalculatedChunkBegin()) + ".mp4");

                recorder = new FFmpegFrameRecorder(
                        outputFile,
                        grabber.getImageWidth(),
                        grabber.getImageHeight());
                recorder.setFormat("mp4");
                recorder.setAudioChannels(grabber.getAudioChannels() > 0 ? 1 : 0);
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setVideoOption("preset", "ultrafast");
                //recorder.setVideoOption("crf", "0");
                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
                recorder.setFrameRate(fps);
                recorder.start();
            }

            @Override
            public void onChunkEnd() throws Exception {
                recorder.stop();

                if (chunkHandler != null) {
                    chunkHandler.accept(outputFile);
                }
            }
        });
    }

    public void start() throws Exception {
        for (int pfn = 0, vfn = 0; pfn < 100 && vfn < 10; pfn++) {
            Frame frame = grab().clone();
            if (frame.image != null) {
                hasVideo = true;
                vfn++;
            }
            probeFrames.add(frame);
        }

        started = true;
    }

    public void stop() throws Exception {
        started = false;
        fpsCalculator.reset();
        chunkDetector.reset();
        hasVideo = false;
        fps = 25;
        begin = -1;
        if (grabber != null) {
            grabber.stop();
            grabber = null;
        }
        if (recorder != null) {
            recorder.stop();
            recorder = null;
            boolean gcRequired = !probeFrames.isEmpty();
            probeFrames.clear();
            if (gcRequired) {
                System.gc();
            }
        }
    }

    public boolean next() throws Exception {
        if (!started) {
            throw new IllegalStateException();
        }
        Frame frame;
        if (!probeFrames.isEmpty()) {
            frame = probeFrames.remove(0);
            if (probeFrames.isEmpty()) {
                System.gc();
            }
        } else {
            frame = grab();
        }
        if (frame == null) {
            return false;
        }
        record(frame);
        return true;
    }

    private Frame grab() throws Exception {
        if (grabber == null) {
            grabber = grabberSupplier.get();
            grabber.start();

            begin = System.currentTimeMillis() * 1000;
        }

        Frame frame = grabber.grab();
        if (frame != null) {
            if (frame.image != null) {
                fpsCalculator.addTimestamp(frame.timestamp);
            }
        }

        return frame;
    }

    private void record(Frame frame) throws Exception {
        chunkDetector.next(frame.keyFrame && frame.image != null, frame.timestamp);
        recorder.record(frame);
    }

    public Consumer<File> getChunkHandler() {
        return chunkHandler;
    }

    public void setChunkHandler(Consumer<File> chunkHandler) {
        this.chunkHandler = chunkHandler;
    }

}
