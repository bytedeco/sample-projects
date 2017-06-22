package streaming_chunker.simplified_grabber;

import java.util.Objects;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class SimplifiedGrabberWrap implements SimplifiedGrabber {

    private final FrameGrabber grabber;

    public SimplifiedGrabberWrap(FrameGrabber grabber) {
        Objects.requireNonNull(grabber);
        this.grabber = grabber;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        Frame frame = grabber.grab();
        frame.timestamp = grabber.getTimestamp();
        return frame;
    }

    @Override
    public int getAudioChannels() {
        return grabber.getAudioChannels();
    }

    @Override
    public int getImageWidth() {
        return grabber.getImageWidth();
    }

    @Override
    public int getImageHeight() {
        return grabber.getImageHeight();
    }

    @Override
    public void start() throws FrameGrabber.Exception {
        grabber.start();
    }

    @Override
    public void stop() throws FrameGrabber.Exception {
        grabber.stop();
        grabber.release();
    }

    protected FrameGrabber getGrabber() {
        return grabber;
    }

}
