package com.cjq.littletools.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.cjq.littletools.R;

public class CircleSeekBar extends View {
    private GestureDetector mGestureDetector;
    private Paint mProgressPaint;
    private Paint mSeekPaint;
    private Paint mArrowPaint;    //arrow内部
    private Paint mArrowBoderPaint;
    private Paint mTextPaint;
    private float mProgress = 30;
    int mProgressColor;
    int mSeekColor;
    int mProgressWidth;
    int radius;
    int mArrowColor;
    int mArrowBorderColor;
    int mTextSize;

    Region pathRegion;
    //设置图形绘制默认的padding
    private int padding = 50;

    public CircleSeekBar(Context context) {
        super(context,null);
    }

    public CircleSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSeekBar);
        mProgressColor = typedArray.getColor(R.styleable.CircleSeekBar_progressColor,Color.WHITE );
        mSeekColor = typedArray.getColor(R.styleable.CircleSeekBar_seekBarColor,Color.BLACK);
        mProgressWidth = typedArray.getDimensionPixelSize(R.styleable.CircleSeekBar_progressWidth,30);
        mArrowColor = typedArray.getColor(R.styleable.CircleSeekBar_arrowColor,Color.GRAY);
        mArrowBorderColor = typedArray.getColor(R.styleable.CircleSeekBar_arrowBorderColor,Color.BLACK);
        mTextSize = (int) typedArray.getDimension(R.styleable.CircleSeekBar_progressTextSize,30);
        mProgressPaint = new Paint();
        mSeekPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mSeekPaint.setAntiAlias(true);

        mProgressPaint.setColor(mProgressColor);
        mSeekPaint.setColor(mSeekColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mSeekPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setShadowLayer(0.0001f,0f,0f,Color.GRAY);
        mSeekPaint.setStyle(Paint.Style.STROKE);
        mSeekPaint.setStrokeWidth(mProgressWidth);

        mArrowPaint = new Paint();
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setColor(mArrowColor);
        mArrowPaint.setAntiAlias(true);

        mArrowBoderPaint = new Paint();
        mArrowBoderPaint.setAntiAlias(true);
        mArrowBoderPaint.setStyle(Paint.Style.STROKE);
        mArrowBoderPaint.setColor(mArrowBorderColor);
        mArrowBoderPaint.setStrokeWidth(8);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.GRAY);
        //设置基线上的点位置( left ,center, right)对应基线的左端点，居中以及右端点
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener(){

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("cjq","singleTapup");
                int x = (int) e.getX();
                int y = (int) e.getY();
                if(pathRegion.contains(x,y)){
                    setProgress(mProgress+5);
                    Log.e("cjq","tap up on region");
                    return true;
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(this.getWidth()/2,this.getHeight()/2);
        //设置默认值
        //计算宽高最短边，用于计算环形进度半径，且这里减去进度width防止超出view范围。
        int shortest = this.getWidth()>this.getHeight()?this.getHeight():this.getWidth();
        if(radius == 0){
            radius = shortest/2-mProgressWidth;
        }
        //绘制进度条背景
        canvas.drawCircle(0,0,radius,mProgressPaint);
        Path sourcePath = new Path();
        sourcePath.addCircle(0,0,radius, Path.Direction.CW);
        Path seekPath = new Path();
        PathMeasure pathMeasure = new PathMeasure(sourcePath,false);

        //计算进度对应角度
        int angle = (int) ((mProgress / 100) * 360);
        float pos[] = new float[2];
        float tan[] = new float[2];
        float distance = (float) ((mProgress / 100.0) * 2 * Math.PI * radius);
        pathMeasure.getSegment(0, distance ,seekPath,true);
        pathMeasure.getPosTan(distance,pos,tan);

        //获取绘制进度文本的矩形区
        float widthRect  = (float) (Math.sqrt(2)/2 * radius);
        RectF targetRect = new RectF(-widthRect,-widthRect,widthRect,widthRect);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //计算基线右边
        int baseline = (int) (targetRect.centerY() - (fontMetrics.bottom + fontMetrics.top) / 2);

        canvas.drawPath(seekPath,mSeekPaint);
        canvas.drawCircle(pos[0],pos[1],mProgressWidth/2-4,mArrowPaint);
        canvas.drawCircle(pos[0],pos[1],mProgressWidth/2,mArrowBoderPaint);
        canvas.drawText(mProgress+" %",targetRect.centerX(),baseline,mTextPaint);

        Path mBorderPath = new Path();
        mProgressPaint.getFillPath(sourcePath,mBorderPath);
        mBorderPath.close();
        pathRegion = new Region();
        pathRegion.setPath(mBorderPath,new Region(-(radius+mProgressWidth),-radius-mProgressWidth,radius+mProgressWidth,radius+mProgressWidth));
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        drawRegion(canvas,pathRegion,paint);
        canvas.restore();
    }
    private void drawRegion(Canvas canvas,Region region,Paint paint){
        RegionIterator iter = new RegionIterator(region);
        Rect r = new Rect();
        while(iter.next(r)){
            canvas.drawRect(r,paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    public float getProgress() {
        return mProgress;
    }
}
