package com.example.hw_8;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.ExifInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private String[] permissions =  new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
    private int PERMISSION_REQUEST_CAMERA_PATH_CODE = 111;
    private Button buttonTakePhoto;
    private Button buttonTakeVideo;
    private SurfaceView mSurfaceView;
    private SurfaceHolder holder;
    private ImageView imageView;
    private VideoView videoView;
    private MediaRecorder mMediaRecorder;
    private String mp4Path;
    private boolean isRecording;

    Camera mCamera;

    public CameraActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_camera);

        buttonTakePhoto = findViewById(R.id.btn_take_photo);
        buttonTakeVideo = findViewById(R.id.btn_take_video);
        mSurfaceView = findViewById(R.id.sv_camera);
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        holder = mSurfaceView.getHolder();
        holder.addCallback(this);

        ActivityCompat.requestPermissions(this, permissions,PERMISSION_REQUEST_CAMERA_PATH_CODE );
        initCamera();


        final Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                FileOutputStream fos = null;
                String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "1.jpg";
                File file = new File(filePath);
                try {
                    fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();
                    displayImage(imageView,filePath);
                    videoView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mCamera.startPreview();
                    if(fos != null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        buttonTakePhoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.takePicture(null,null,mPictureCallback);
            }
        });

        buttonTakeVideo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });

    }

    private void initCamera(){

        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.set("orientation", "portrait");
        parameters.set("rotation", 90);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
    }

    public static void displayImage(ImageView imageView,String path){
        int targetWidth = imageView.getWidth();
        int targetHeight = imageView.getHeight();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;
        int scaleFactor = Math.min(photoHeight/targetHeight,photoWidth/targetWidth);
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        imageView.setImageBitmap(bitmap);
    }

    private boolean prepareVideoRecorder(){
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mp4Path = getOutputMediaPath();
        mMediaRecorder.setOutputFile(mp4Path);
        mMediaRecorder.setPreviewDisplay(holder.getSurface());
        mMediaRecorder.setOrientationHint(90);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            mMediaRecorder.release();
            return false;
        }
        return true;
    }

    public void record(View view){
        if(isRecording){
            buttonTakeVideo.setText("录制");
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();

            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(mp4Path);
            videoView.start();
        }else{
            if(prepareVideoRecorder()){
                buttonTakeVideo.setText("暂停");
                mMediaRecorder.start();
            }else return;
        }
        isRecording = !isRecording;
    }


    public String getOutputMediaPath(){
        File mediaDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaDir,"IMG_" + timeStamp + ".mp4");
        if(!mediaFile.exists()){
            mediaFile.getParentFile().mkdirs();
        }
        return mediaFile.getAbsolutePath();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera == null){
            initCamera();
        }
        mCamera.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(holder.getSurface() == null) return;
        try {
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
}
