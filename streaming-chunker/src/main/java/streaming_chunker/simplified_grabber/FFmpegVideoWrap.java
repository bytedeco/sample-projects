package streaming_chunker.simplified_grabber;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class FFmpegVideoWrap extends SimplifiedGrabberWrap {

    public FFmpegVideoWrap(FFmpegFrameGrabber grabber) {
        super(grabber);
    }

    @Override
    public int getAudioChannels() {
        return 0;
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        Frame frame = ((FFmpegFrameGrabber) getGrabber()).grabImage();
        frame.timestamp = getGrabber().getTimestamp();
        return frame;
    }

}
