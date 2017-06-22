package streaming_chunker.simplified_grabber;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Dmitriy Gerashenko <d.a.gerashenko@gmail.com>
 */
public class FFmpegAudioWrap extends SimplifiedGrabberWrap {

    public FFmpegAudioWrap(FFmpegFrameGrabber grabber) {
        super(grabber);
    }

    @Override
    public Frame grab() throws FrameGrabber.Exception {
        Frame frame = ((FFmpegFrameGrabber) getGrabber()).grabSamples();
        frame.timestamp = getGrabber().getTimestamp();
        return frame;
    }

    
}
