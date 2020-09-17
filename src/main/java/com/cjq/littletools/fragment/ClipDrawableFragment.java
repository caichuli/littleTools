package com.cjq.littletools.fragment;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.util.Timer;
import java.util.TimerTask;

public class ClipDrawableFragment extends Fragment {
    Handler mHandler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_clip,null,false);
        final ImageView imageView = view.findViewById(R.id.image_clip);
        final ClipDrawable drawable = (ClipDrawable) imageView.getDrawable();
        final Timer timer = new Timer();


        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 0x1234){
                    drawable.setLevel(drawable.getLevel()+200);
                }
            }
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                mHandler.sendEmptyMessage(0x1234);
                if(drawable.getLevel()>=10000){
                    timer.cancel();
                }
                Log.e("-----drawable-----",""+drawable.getLevel());

            }
        },0,200);

        return view;
    }
}
