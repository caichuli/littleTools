package com.cjq.littletools.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;


public class AnimationFragment extends Fragment {
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_animation, null, false);

        //逐帧动画
        ImageView imageView = view.findViewById(R.id.image_anim_frame);
        imageView.setImageResource(R.drawable.animation_frame);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        //补间动画
        ImageView imageView_trans = view.findViewById(R.id.image_anim_trans);
        Animation animation = AnimationUtils.loadAnimation(this.getContext(),R.anim.anim_trans);
        animation.setFillAfter(true);

        imageView_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnimationFragment.this.getActivity());
                AlertDialog dialog =  alertDialog.setTitle("哈哈")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                Window w = dialog.getWindow();
                w.setWindowAnimations(R.style.DialogCart);
                dialog.show();

            }
        });
        /*imageView_trans.startAnimation(animation);*/
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView_trans,"translationX",0,400);
        objectAnimator.setDuration(2000);
        objectAnimator.start();


        final Button trans_btn = view.findViewById(R.id.trans_button);
        trans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trans_btn.setTranslationY(500);
                Toast.makeText(AnimationFragment.this.getActivity(),"位置改变",Toast.LENGTH_SHORT).show();
            }
        });





        return view;
    }
}
