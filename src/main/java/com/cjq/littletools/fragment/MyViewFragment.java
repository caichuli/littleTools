package com.cjq.littletools.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.cjq.littletools.R;
import com.cjq.littletools.customview.CircleDiagram;
import com.cjq.littletools.customview.MyTextView;
import com.cjq.littletools.customview.Painter;
import com.cjq.littletools.entity.PieData;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Key;
import java.security.KeyFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CircleDiagram mCircleDiagram;
    Painter mPainter;
    int currentIndex = 0;

    public MyViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyViewFragment newInstance(String param1, String param2) {
        MyViewFragment fragment = new MyViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_view2, container, false);
        mCircleDiagram = view.findViewById(R.id.pie_diagram);
        final TextView tv = view.findViewById(R.id.hatext);
        final TextView timeTv = view.findViewById(R.id.timeText);
        final TextView abc_text = view.findViewById(R.id.abc_text);

        List<PieData> dataList = new ArrayList<>();

        final String tag[] = {"1","2","3"};

        dataList.add(new PieData("土豆",20, Color.CYAN));
        dataList.add(new PieData("玉米",50,Color.MAGENTA));
        dataList.add(new PieData("番茄",66,Color.LTGRAY));
        dataList.add(new PieData("花生",73,Color.GRAY));
        dataList.add(new PieData("西瓜",39,Color.YELLOW));

        mCircleDiagram.setDataList(dataList);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,400);
        final ValueAnimator colorAnimator = ValueAnimator.ofArgb(0x8800ff00,0x880000ff,0x88ff00ff);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(9000);
        colorAnimator.setDuration(3000);

        final ValueAnimator timeAnimator = ValueAnimator.ofArgb(0x00000000,0xff000000,0x00000000);

        timeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        timeAnimator.setDuration(2000);
        timeAnimator.setEvaluator(new IntEvaluator());
        timeAnimator.setRepeatMode(ValueAnimator.RESTART);
        timeAnimator.setRepeatCount(ValueAnimator.INFINITE);


        //使用layout会把组件本身重新布局。相当于组件本身移动了，而setTranslationX是设置了translationX，同时对监听事件进行映射。
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCircleDiagram.layout(value,value,mCircleDiagram.getWidth()+value,mCircleDiagram.getHeight()+value);
                Log.d("----TAG----",value+"");

            }
        });
        /*valueAnimator.start();*/
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)colorAnimator.getAnimatedValue();
                tv.setTextColor(value);
            }
        });

        /*colorAnimator.start();*/

        timeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) timeAnimator.getAnimatedValue();
                timeTv.setTextColor(value);
            }
        });

        timeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                timeTv.setText(tag[0]);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                currentIndex++;
                timeTv.setText(tag[currentIndex%tag.length]);

            }
        });
        /*timeAnimator.start();*/




        //ofObject
        ValueAnimator charAnimation = ValueAnimator.ofObject(new CharAnimatorEvaluator(),new Character('中'),new Character('国'));
        charAnimation.setDuration(100000);
        charAnimation.setInterpolator(new LinearInterpolator());
        charAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                abc_text.setText(animation.getAnimatedValue().toString());
            }
        });
        /*charAnimation.start();*/


        //ObjectAnimator的使用，无需增加监听，指定动画的目标组件
        //这里的属性名会使用反射的形式，找到对应组件的setXXX方法，将动画进度值传入进去。因此可以在setter方法中更新组件，以实现相关的动画效果。
        //需要传入的属性值的类型要和ofXX方法对应,因为是根据这个类型来获取到具体方法的
        //1.当只传入一个值作为动画时，则会使用传入属性类型的初始值来作为动画的初始值，比如int为0。所以，如果动画属性值为object，则使用该对象的getXXX方法作为初始值。
        //2.如果该对象没有getter方法，则会抛出异常。
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv,"rotationY",0,359);
        objectAnimator.setDuration(2000);
        /*objectAnimator.start();*/

        //使用AnimatorSet控制动画同时播放或者顺序播放。

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(valueAnimator,colorAnimator,timeAnimator,objectAnimator);
       // animatorSet.playTogether(valueAnimator,colorAnimator,charAnimation,objectAnimator);
        animatorSet.start();
        /*animatorSet.start();*/
        /* animatorSet.playTogether(valueAnimator,colorAnimator,timeAnimator,objectAnimator);*/


        //使用Animator.Builder控制播放的细节
        //这里如果先播放的动画是循环的，则后面的动画也不会被播放。
        /*AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(valueAnimator)
                .with(colorAnimator)
                .before(timeAnimator)
                .after(objectAnimator);
        animatorSet2.start();*/



        //PropertyValuesHolder的使用
        //ofPropertyValues可以传入多个PropertyValuesHolder，同时执行这些动画
        TextView pro_tv = view.findViewById(R.id.pro_text);
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat("translationY",0,400);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("translationX",0,200);
        ObjectAnimator propertyAnimator = ObjectAnimator.ofPropertyValuesHolder(pro_tv,holder,holder2);
        propertyAnimator.start();




        //使用keyFrame（关键帧，）实现类似插值器的效果。和flash动画类似的。
        //也可以为frame添加差值器，
        //注意：使用keyFrame至少要有两帧，即开始帧和结束帧，否则报错
        Keyframe frame0 = Keyframe.ofFloat(0,0);
        Keyframe frame1 = Keyframe.ofFloat(1,200);
        //为当前帧添加回弹差值器，从上一帧到当前帧的计算会使用这个差值器。
        frame1.setInterpolator(new BounceInterpolator());
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofKeyframe("translationX",frame0,frame1);
        PropertyValuesHolder holder4 = PropertyValuesHolder.ofKeyframe("translationY",frame0,frame1);
        ObjectAnimator keyFrameAnimator = ObjectAnimator.ofPropertyValuesHolder(pro_tv,holder3,holder4);
        keyFrameAnimator.setDuration(3000);
        keyFrameAnimator.start();

        //使用KeyFrame的ofObject
        final MyTextView myTextView = view.findViewById(R.id.my_textView); //自定义的textView，添加了setCharText方法

        Keyframe oFrame0 = Keyframe.ofObject(0.0f,'A');
        Keyframe oFrame1 = Keyframe.ofObject(0.5f,'N');
        Keyframe oFrame2 = Keyframe.ofObject(1.0f,'Z');

        //差值器
        oFrame1.setInterpolator(new LinearInterpolator());
        oFrame2.setInterpolator(new DecelerateInterpolator());
        PropertyValuesHolder oHolder = PropertyValuesHolder.ofKeyframe("charText",oFrame0,oFrame1,oFrame2);
        oHolder.setEvaluator(new CharAnimatorEvaluator());  //为字母动画的holder设置差值器
        ObjectAnimator objectKeyFrameAnimator = ObjectAnimator.ofPropertyValuesHolder(myTextView,oHolder);
        objectKeyFrameAnimator.setDuration(8000);
        objectKeyFrameAnimator.start();




        //ViewPropertyAnimator：简化动画的使用，使用View.animate()方法获取该对象
        //该对象为场景的动画如alpha,Translation，scale,rotation等提供了简便的调用方式
        //相比ObjectAnimator，主要优势在于可以方便的创建常用的动画
        myTextView.setTextSize(60);
        ViewPropertyAnimator vp = myTextView.animate();
        vp.translationX(100).translationY(100).setDuration(1000);


        //同样可以为ViewPropertyAnimator设置监听器

        vp.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                myTextView.setTranslationX(0);
                myTextView.setTranslationY(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        //





        /*WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        View v2 = inflater.inflate(R.layout.fragment_time,null,false)
         wm.addView(v2,new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG));*/
        return view;
    }

    private void doAnimation() {
    }
    class CharAnimatorEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int start = startValue;
            int end = endValue;
            int value = (int) (start+fraction*(end-start));
            return (char)value;
        }
    }


}