package com.cjq.littletools.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.cjq.littletools.R;

public class MainActivity2 extends AppCompatActivity {
    private  static final int CAPTURE_VIDEO_REQUEST_CODE = 1;


    VideoView mVideoView;
    Button mCaptureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mCaptureButton = findViewById(R.id.capture);
        mVideoView = findViewById(R.id.videoView);
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchCapture();
            }
        });
    }
    private void dispatchCapture(){
        Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(captureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(captureIntent,CAPTURE_VIDEO_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_VIDEO_REQUEST_CODE && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();
        }
    }
}