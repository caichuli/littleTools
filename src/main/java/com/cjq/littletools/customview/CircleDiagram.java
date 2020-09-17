package com.cjq.littletools.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.cjq.littletools.entity.PieData;

import java.util.List;
import java.util.regex.Matcher;

public class CircleDiagram extends View {
    int mWidth,mHeight;
    List<PieData> mDataList;
    Paint mPaint = new Paint();
    float mCurrentStartAngle;
    public CircleDiagram(Context context) {
        super(context);
    }

    public CircleDiagram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public void setDataList(List<PieData> dataList) {
        mDataList = dataList;
        initData();
        invalidate();
    }

    public void setCurrentStartAngle(float currentStartAngle) {
        mCurrentStartAngle = currentStartAngle;
        invalidate();
    }

    void initData(){
        if(mDataList == null||mDataList.size()==0){
            return;
        }
        float sum = 0f;
        for(int i = 0;i<mDataList.size();i++){
            sum+=mDataList.get(i).getValue();
        }
        for(PieData p : mDataList){
            float percent = p.getValue()/sum;
            float angle = percent * 360;
            p.setPercent(percent);
            p.setAngle(angle);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2f,mHeight/2f);
        float r = Math.min(mWidth,mHeight)/2f;
        RectF rect = new RectF(-r,-r,r,r);
        for(int i = 0;i<mDataList.size();i++){
            PieData pie = mDataList.get(i);
            if(pie.getColor()!=0){
                mPaint.setColor(pie.getColor());
            }
            canvas.drawArc(rect,mCurrentStartAngle,pie.getAngle(),true,mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawText(pie.getName(),(float) Math.sin(mCurrentStartAngle)*r/2, (float)Math.cos(mCurrentStartAngle)*r/2,mPaint);
            mPaint.setColor(pie.getColor());
            mCurrentStartAngle += pie.getAngle();

        }

    }
}
