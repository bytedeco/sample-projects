package streaming_chunker.simplified_grabber;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class FFmpegMixerWrap implements SimplifiedGrabber {

    private final FFmpegFrameGrabber videoGrabber;
    private final FFmpegFrameGrabber audioGrabber;
    private ExecutorService dualExecutor = null;
    private boolean started = false;
    private Frame videoFrame = null;
    private Frame audioFrame = null;

    public FFmpegMixerWrap(FFmpegFrameGrabber videoGrabber, FFmpegFrameGrabber audioGrabber) {
        Objects.requireNonNull(videoGrabber);
        Objects.requireNonNull(audioGrabber);
        this.videoGrabber = videoGrabber;
        this.audioGrabber = audioGrabber;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        if (!started) {
            throw new IllegalStateException();
        }
        Future<Frame> vf = null;
        if (videoFrame == null) {
            vf = dualExecutor.submit(() -> {
                Frame frame = videoGrabber.grabImage();
                frame.timestamp = videoGrabber.getTimestamp();
                return frame;
            });
        }
        Future<Frame> af = null;
        if (audioFrame == null) {
            af = dualExecutor.submit(() -> {
                Frame frame = audioGrabber.grabSamples();
                frame.timestamp = audioGrabber.getTimestamp();
                return frame;
            });
        }
        try {
            if (vf != null) {
                videoFrame = vf.get();
            }
            if (af != null) {
                audioFrame = af.get();
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new FrameGrabber.Exception(null, ex);
        }
        Frame frameToReturn;
        if (audioFrame.timestamp > videoFrame.timestamp) {
            frameToReturn = videoFrame;
            videoFrame = null;
        } else {
            frameToReturn = audioFrame;
            audioFrame = null;
        }
        return frameToReturn;
    }

    @Override
    public int getAudioChannels() {
        return audioGrabber.getAudioChannels();
    }

    @Override
    public int getImageWidth() {
        return videoGrabber.getImageWidth();
    }

    @Override
    public int getImageHeight() {
        return videoGrabber.getImageHeight();
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        if (started) {
            throw new IllegalStateException();
        }
        dualExecutor = Executors.newFixedThreadPool(2);
        Future<Void> vf = dualExecutor.submit(() -> {
            videoGrabber.start();
            return null;
        });
        Future<Void> af = dualExecutor.submit(() -> {
            audioGrabber.start();
            return null;
        });
        try {
            vf.get();
            af.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new FrameGrabber.Exception(null, ex);
        }
        started = true;
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        if (!started) {
            throw new IllegalStateException();
        }
        try {
            Future<Void> vf = dualExecutor.submit(() -> {
                videoGrabber.stop();
                videoGrabber.release();
                return null;
            });
            Future<Void> af = dualExecutor.submit(() -> {
                audioGrabber.stop();
                audioGrabber.release();
                return null;
            });
            try {
                vf.get();
                af.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new FrameGrabber.Exception(null, ex);
            }
            started = false;
        } finally {
            dualExecutor.shutdown();
            dualExecutor = null;
        }
    }
}
