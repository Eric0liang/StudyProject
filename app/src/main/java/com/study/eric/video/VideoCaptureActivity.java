package com.study.eric.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.eric.R;
import com.study.eric.base.BaseFragmentActivity;
import com.study.eric.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liangjiangli on 2017/6/6.
 */

public class VideoCaptureActivity extends BaseFragmentActivity implements SurfaceHolder.Callback {

    public static void actStart(Context context) {
        Intent intent = new Intent(context, VideoCaptureActivity.class);
        context.startActivity(intent);
    }

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;

    private Timer mTimer;
    private int mTimeCount;
    private long time;
    private String fileName;
    private int cameraCount; //摄像头的个数
    private int currentCameraSpacing = CameraInfo.CAMERA_FACING_BACK;

    @Bind(R.id.btn_record)
    CustomProgress customProgress;
    @Bind(R.id.btn)
    ImageView btn;
    @Bind(R.id.btn2)
    ImageView btn2;
    @Bind(R.id.media_camera)
    SurfaceView mSurfaceView;
    @Bind(R.id.parent)
    FrameLayout parent;
    @Bind(R.id.progress_layout)
    RelativeLayout progressLayout;
    @Bind(R.id.img_finish)
    ImageView imgFinish;
    @Bind(R.id.img_change_camera)
    ImageView imgChangeCamera;
    @Bind(R.id.txt_tips)
    TextView txtTips;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            customProgress.setProgress(mTimeCount);
        }
    };
    private Runnable sendVideo = new Runnable() {
        @Override
        public void run() {
            recordStop();
        }
    };
    private MediaPlayer mediaPlayer;

    private final int MAX_TIME = 1000;
    private boolean isRecording = false;
    private boolean isPlay = false;
    private boolean isTake = false; //拍照？
    private int quality = CamcorderProfile.QUALITY_1080P;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_capture;
    }

    @Override
    protected void initView() {
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        cameraCount = Camera.getNumberOfCameras();
        if (cameraCount < 2) {
            imgChangeCamera.setVisibility(View.GONE);
        }

        customProgress.setmTotalProgress(MAX_TIME);
        customProgress.bringToFront();
        customProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下 开始录像
                        if (!isRecording) {
                            time = Calendar.getInstance().getTimeInMillis();
                            customProgress.changeAttrs();
                            imgFinish.setVisibility(View.GONE);
                            imgChangeCamera.setVisibility(View.GONE);
                            txtTips.setVisibility(View.GONE);
                            if (prepareVideoRecorder()) {
                                mMediaRecorder.start();
                                time = Calendar.getInstance().getTimeInMillis(); //倒计时
                                isRecording = true;
                                mTimer = new Timer();
                                mTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        mTimeCount++;
                                        mainHandler.post(updateProgress);
                                        if (mTimeCount == MAX_TIME) {
                                            mainHandler.post(sendVideo);
                                        }
                                    }
                                }, 0, 10);
                            } else {
                                releaseMediaRecorder();
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起 停止录像
                        recordStop();
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.btn, R.id.btn2, R.id.img_finish, R.id.img_change_camera})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (isPlay) {
                    releaseMediaPlayer();
                }
                isPlay = false;
                isTake = false;
                openVideo(CameraInfo.CAMERA_FACING_BACK);

                btn.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
                progressLayout.bringToFront();
                imgFinish.setVisibility(View.VISIBLE);
                if (cameraCount > 1) {
                    imgChangeCamera.setVisibility(View.VISIBLE);
                }
                txtTips.setVisibility(View.VISIBLE);
                customProgress.changeInitAttrs();
                break;
            case R.id.btn2:
                break;
            case R.id.img_finish:
                finish();
                this.overridePendingTransition(0, R.anim.p_bottom_out);
                break;
            case R.id.img_change_camera:
                currentCameraSpacing = (currentCameraSpacing == CameraInfo.CAMERA_FACING_BACK
                        ? CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK);
                releaseCamera();
                openVideo(currentCameraSpacing);
                break;
        }
    }

    private boolean isPause;

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            isPause = false;
            if (isPlay) {
                playVideo();
            } else if (!isTake) {
                openVideo(currentCameraSpacing);
            }
        }
    }


    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }


    /**
     * 初始化 mMediaRecorder 用于录像
     *
     * @return
     */
    private boolean prepareVideoRecorder() {

        if (mCamera == null) return false;
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        //声音
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        //视频
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置分辨率
        mMediaRecorder.setProfile(CamcorderProfile.get(quality));
        //路径
        mMediaRecorder.setOutputFile(getOutputMediaFile().toString());
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        try {
            //旋转90度 保持竖屏
            mMediaRecorder.setOrientationHint(90);
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            LogUtils.d("IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            LogUtils.d("IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        isPlay = true;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Uri uri = Uri.parse(fileName);
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //轮播
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.setDisplay(mSurfaceHolder);
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });
    }

    /**
     * 录制视频
     */
    private void openVideo(int spacing) {
        if (!isPlay && !isTake) {
            if (mCamera != null) {
                releaseCamera();
            }
            currentCameraSpacing = spacing;
            mCamera = Camera.open(spacing);
            try {
                mCamera.stopPreview();
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.setDisplayOrientation(90);
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.set("orientation", "portrait");
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (Exception e) {
                LogUtils.d("Error starting camera preview: " + e.getMessage());
            }
        }
    }


    /**
     * 停止录制
     */
    private void recordStop() {
        isRecording = false;
        try {
            //释放录像相关资源
            if (mTimer != null) mTimer.cancel();
            mTimeCount = 0;
            customProgress.setProgress(mTimeCount);
            mainHandler.removeCallbacks(updateProgress);
            releaseMediaRecorder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isTake = Calendar.getInstance().getTimeInMillis() - time < 1000;
        if (!isTake) {
            releaseCamera();
            playVideo();
        } else {
            takePicture();
            releaseCamera();
        }

        //界面控制
        progressLayout.setVisibility(View.GONE);
        btn.bringToFront();
        btn2.bringToFront();
        btn.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
    }

    /**
     * 拍照
     */
    private void takePicture() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Canvas canvas = mSurfaceHolder.lockCanvas();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                canvas.drawBitmap(bitmap, new Matrix(), new Paint());
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        });
    }

    /**
     * 视频存放路径
     */

    private File getOutputMediaFile() {
        String appName = getPackageName();
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + appName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        fileName = dir + "/video_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
        return new File(fileName);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        openVideo(currentCameraSpacing);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isPause = true;
        mainHandler.removeCallbacks(updateProgress);
        releaseMediaRecorder();
        releaseCamera();
        releaseMediaPlayer();
    }
}
