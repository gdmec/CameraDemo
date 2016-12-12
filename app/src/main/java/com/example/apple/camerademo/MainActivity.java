package com.example.apple.camerademo;


import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private ImageView imageView;
    private File file;
    private Camera camera;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mSurfaceView= (SurfaceView) findViewById(R.id.surfaceView1);
        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            camera.setPreviewDisplay(surfaceHolder);
        }catch (Exception e){
            e.printStackTrace();
            camera.release();
            camera = null;
        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void takePhoto(View v){
        camera.takePicture(null,null,pictureCallback);
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            savePicture(bytes);
        }
    };

    private void savePicture(byte[] data){
        try{
            String imageId = System.currentTimeMillis()+"";
            String imgpath = Environment.getExternalStorageDirectory().getPath()+"/"+imageId+".jpg";
            File file = new File(imgpath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            Toast.makeText(this,"图片保存在"+imgpath,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
