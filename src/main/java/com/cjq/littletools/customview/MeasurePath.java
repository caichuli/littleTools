package com.cjq.littletools.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

public class MeasurePath extends View {
    float mCurrentAnim = 0f;
    Paint arr;
    int radius_path = 80;
    int radius_arr = 20;
    int arr_offset = 60;


    public MeasurePath(Context context) {
        this(context,null);


    }

    public MeasurePath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        arr = new Paint();
        arr.setStyle(Paint.Style.STROKE);
        arr.setStrokeWidth(5);
        arr.setAntiAlias(true);
        arr.setColor(Color.BLACK);

        setLayerType(LAYER_TYPE_SOFTWARE,null);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        valueAnimator.setDuration(2500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentAnim = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(this.getWidth()/2,this.getHeight()/2);
        Paint paint = new Paint();
        Paint segPaint = new Paint();
        segPaint.setColor(Color.GREEN);
        segPaint.setStrokeWidth(10);
        segPaint.setStyle(Paint.Style.STROKE);
        segPaint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        Path path = new Path();
        path.addCircle(0,0,radius_path, Path.Direction.CCW);
        /*path.addCircle(0,0,100, Path.Direction.CW);
        path.addCircle(0,0,200, Path.Direction.CCW);
        path.addCircle(0,0,300, Path.Direction.CW);*/
        PathMeasure pathMeasure = new PathMeasure();
       /* PathMeasure pathMeasure2 = new PathMeasure();*/
        pathMeasure.setPath(path,true);
        /*pathMeasure2.setPath(path,false);*/

       /* Log.e("cjq","未闭合状态：长度 = "+pathMeasure2.getLength());*/
        do{
            //打印当前曲线的长度，PathMeasure.getLength()默认获取第一条曲线路径长度
            Log.e("cjq","强制闭合状态：长度 = "+pathMeasure.getLength());

        }while (pathMeasure.nextContour());  //切换到下一条路径（如果存在）  return true/false;
        /*canvas.drawPath(path,paint);*/


        //使用getSegment截取路径片段，并存入已有的path，然后绘制。
        /*PathMeasure pathMeasure2 = new PathMeasure();
        pathMeasure2.setPath(path,false);
        do{
            Path p = new Path();
            p.moveTo(0,0);
            p.lineTo(0,10);
            //这里最后一个参数startWithMoveTo标识是否将路径绘制的起始点移动到新添加path的起始点，所以ture则会分段绘制，保留原始路径效果。false则会使用上一个path的结束点与
            //-》下一个path起始点连接。也即是保持连续性
            pathMeasure2.getSegment(0,pathMeasure2.getLength()*mCurrentAnim,p,true);

            canvas.drawPath(p,segPaint);

        }while (pathMeasure2.nextContour());
        */

        /*
        根据进度改变路径的start
        使用getPosTan获取某点stop点位置，并对该点绘制
         */
        float[] pos = new float[2];
        float[] tan = new float[2];

        PathMeasure pathMeasure3 = new PathMeasure();
        pathMeasure3.setPath(path,false);

        do{
            float stop = pathMeasure3.getLength()*mCurrentAnim;
            float start;
            Path p = new Path();
            if(mCurrentAnim<0.5){
                start = 0;
            }
            else{
                start = pathMeasure3.getLength()*mCurrentAnim;
                stop = start+arr_offset;
            }

            //获取到stop点的位置和正切值
            /*pathMeasure3.getPosTan(stop,pos,tan);
            Log.e("stop点： ","位置："+pos[0]+","+pos[1]+"正切："+tan);
*/

            p.reset();
            pathMeasure3.getSegment(start,stop,p,true);


            float angle = (float) (2* Math.asin((float)radius_arr/(2*60)));
            Log.e("stop点：","angle"+angle);
            float l = (float) (angle/Math.PI*radius_path);

            //使用该函数获取指定路径长度处的位置坐标和正切。
            pathMeasure3.getPosTan(stop+l,pos,tan);
            Log.e("stop点： ","L长度："+l+"位置："+pos[0]+","+pos[1]+"正切："+tan);

            canvas.drawPath(p,segPaint);
            canvas.drawCircle(pos[0],pos[1],radius_arr,arr);

        }while(pathMeasure3.nextContour());




        //





    }
}
