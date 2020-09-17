package com.cjq.littletools.fragment;

import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeFragment extends Fragment {
    TextView text_minute,text_second,text_mill;
    ImageView clockView;
    ImageButton btn_stop,btn_begin;
    ListView mListView ;
    ArrayAdapter adapter;
    private static int count = 0;

    List<String> time_list;
    boolean TIME_START_FLAG = false;   //计时器开始状态
    long start_time,pause_time,last_time,now_mill;
    long mill,second,minute;
    String millstr,seccondstr,minutestr;
    //UI Handler
    Handler mHandler;
    Timer timer,restartTimer;


    //动画

    Animation mAnimation;

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_time,null,false);
        time_list = new ArrayList<>();
        text_minute = view.findViewById(R.id.textView_minute);
        text_second = view.findViewById(R.id.textView_second);
        text_mill = view.findViewById(R.id.textView_mill);
        clockView = view.findViewById(R.id.image_clock);
        btn_stop = view.findViewById(R.id.btn_stop);
        btn_begin = view.findViewById(R.id.btn_begin);
        mListView = view.findViewById(R.id.time_list);

        adapter = new ArrayAdapter(this.getActivity(),R.layout.list_item,time_list);
        mListView.setAdapter(adapter);

        //动画
        mAnimation = AnimationUtils.loadAnimation(this.getActivity(),R.anim.anim_clock);
        mAnimation.setFillAfter(true);
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x1234){
                    text_mill.setText(millstr);
                    text_second.setText(seccondstr);
                    text_minute.setText(minutestr);
                }
            }
        };

        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击按钮则定时器状态改变
                TIME_START_FLAG = !TIME_START_FLAG;
                if(TIME_START_FLAG){
                    btn_begin.setImageResource(R.drawable.pauseicon);
                    //开始状态,获取开始时间
                    if(start_time == 0){
                        start_time = new Date().getTime();
                    }

                    setTime();

                    clockView.startAnimation(mAnimation);
                }
                else{
                    count++;
                    btn_begin.setImageResource(R.drawable.playicon);
                    //暂停状态，获取当前时间
                    last_time = new Date().getTime();
                    timer.cancel();
                    time_list.add("NO. "+count+":"+"    "+minutestr+"："+seccondstr+"："+millstr+"\n");
                    adapter.notifyDataSetChanged();
                    clockView.clearAnimation();
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                time_list.clear();
                text_minute.setText("00");
                text_second.setText("00");
                text_mill.setText("00");
                adapter.notifyDataSetChanged();
                clockView.clearAnimation();
                count = 0;
            }
        });
        return view;
    }
    private void setTime(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                now_mill = new Date().getTime();
                mill = (now_mill-start_time)%1000;
                second = ((now_mill-start_time)/1000)%60;
                minute = (now_mill-start_time)/1000/60%24;

                millstr = mill>100?new String(mill+"").substring(0,2):mill+"";
                millstr = millstr.length()<2?"0"+millstr:millstr+"";
                seccondstr = second<10?"0"+second:second+"";
                minutestr = minute<10?"0"+minute:minute+"";

                if(minute>59){
                    timer.cancel();
                }

                mHandler.sendEmptyMessage(0x1234);
            }
        },0,10L);
    }

    @Override
    public void onStop() {
        super.onStop();
        count = 0;
    }
}
