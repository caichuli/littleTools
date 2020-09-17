package com.cjq.littletools.fragment;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LayoutAnimatorFragment extends Fragment {
    LinearLayout myContainer;
    int i = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animator_layout_fragment, null, false);

        Button addBtn = view.findViewById(R.id.add_btn);
        Button delBtn = view.findViewById(R.id.del_btn);
        //在ViewGroup中使用android:animateLayoutChanges="true"
        //可实现移除，添加的动画效果。
        myContainer = view.findViewById(R.id.linear_container);
        addBtn.setOnClickListener(new clickListener());
        delBtn.setOnClickListener(new clickListener());

        //使用LayoutTransition
        //能自定义ViewGroup中控件的出场/入场动画
        LayoutTransition transitioner = new LayoutTransition();
        PropertyValuesHolder outHolderX = PropertyValuesHolder.ofFloat("scaleX",1f,0f);
        PropertyValuesHolder outHolderY = PropertyValuesHolder.ofFloat("scaleY",1f,0f);
        ObjectAnimator animatorOut =  ObjectAnimator.ofPropertyValuesHolder(new View(this.getContext()),outHolderX,outHolderY);
        animatorOut.setDuration(2000);
        transitioner.setAnimator(LayoutTransition.DISAPPEARING,animatorOut);
        PropertyValuesHolder inHolderX = PropertyValuesHolder.ofFloat("scaleX",0f,1f);
        PropertyValuesHolder inHolderY = PropertyValuesHolder.ofFloat("scaleY",0f,1f);
        ObjectAnimator animatorIn = ObjectAnimator.ofPropertyValuesHolder(new View(this.getContext()),inHolderX,inHolderY);
        animatorIn.setDuration(2000);

        transitioner.setAnimator(LayoutTransition.APPEARING,animatorIn);
        //最后为容器设置LayoutTransition
        myContainer.setLayoutTransition(transitioner);


        //改变left，top
        //这里获取view的布局信息如left，top,width,height获取不到。是因为view此时还没有绘制完成。
        /*TextView tv = view.findViewById(R.id.ltrb);
        tv.setBackgroundColor(Color.RED);
        int r = tv.getRight();
        PropertyValuesHolder ltrbHolderLeft = PropertyValuesHolder.ofInt("left",0,tv.getRight());
        PropertyValuesHolder ltrbHolderRight = PropertyValuesHolder.ofInt("right",0,40);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tv,ltrbHolderLeft);
        objectAnimator.setDuration(1000);
        objectAnimator.start();*/




        return view;
    }
    class clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_btn:

                    Button btn = new Button(LayoutAnimatorFragment.this.getContext());
                    btn.setText("button"+(i+1));
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    myContainer.addView(btn,i);
                    i++;
                    break;
                case R.id.del_btn:
                    if(myContainer.getChildCount()!=0) {
                        myContainer.removeViewAt(0);
                        i--;
                    }
                    break;
            }
        }
    }

}
