package com.cjq.littletools.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.cjq.littletools.entity.PieData;

import java.util.List;

public class ThunderView extends View {
    float mWidth,mHeight;
    float centerX,centerY;
    float angle; //单个区域的角度
    float radius;//多边形的最大半径
    int count = 6;
    List<PieData> mDataList;
    Paint mLinePaint = new Paint();
    Paint mGraphPaint = new Paint();
    public ThunderView(Context context) {
        super(context);
    }

    public ThunderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        centerX = mWidth/2;
        centerY = mHeight/2;
    }

    //数据更新后，onDraw之前调用
    void init(){
        if(mDataList==null||mDataList.size()==0){
            return;
        }
        radius = Math.min(mWidth,mHeight)*0.9f;
        angle = (float) (2*Math.PI/mDataList.size());   //计算区域角
        mLinePaint.setStrokeWidth(3);
        mGraphPaint.setStrokeWidth(4);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon();
    }
    void drawPolygon(){

    }
}
