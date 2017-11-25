package com.baidu.vis.javacv_android_video_grabber;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.vis.javacv_android_video_grabber.filechooser.ChooserDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

import java.io.File;
import java.io.IOException;


@EActivity(R.layout.sample_video)
public class MainActivity extends Activity {
    private static final String TAG = "SampleVideo";
    @ViewById(R.id.image_view)
    ImageView img;

    @Click(R.id.parse_video)
    public void onClick() {
        new ChooserDialog().with(MainActivity.this)
                .withStartFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath())
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(final String path, File pathFile) {
                        startVideoParsing(path);
                    }
                })
                .build()
                .show();
    }

    @ViewById(R.id.output)
    TextView outputTv;

    @AfterViews
    void initPredictor() {
//        Predictor.init(this);
    }


    private void startVideoParsing(final String path) {
        Toast.makeText(MainActivity.this,
                "分析视频 " + path,
                Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doConvert(path);
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doConvert(String path) throws
            FrameGrabber.Exception,
            FrameRecorder.Exception,
            IOException {
        FFmpegFrameGrabber videoGrabber = new FFmpegFrameGrabber(path);
        Frame frame;
        int count = 0;
        videoGrabber.start();
        AndroidFrameConverter bitmapConverter = new AndroidFrameConverter();
        while (true) {
            long startRenderImage = System.nanoTime();
            frame = videoGrabber.grabFrame();
            if (frame == null) {
                break;
            }
            if (frame.image == null) {
                continue;
            }
            count++;

            final Bitmap currentImage = bitmapConverter.convert(frame);
//            final ArrayList<GestureBean> rst = Predictor.predict(currentImage, this);
            long endRenderImage = System.nanoTime();
            final Float renderFPS = 1000000000.0f / (endRenderImage - startRenderImage + 1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    outputTv.setText(String.format("读取数据FPS：%f,结果:%d", renderFPS, rst == null ? 0 : rst.size()));
                    img.setImageBitmap(currentImage);
                }
            });
        }
    }
}
