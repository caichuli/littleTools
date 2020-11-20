package com.cjq.littletools.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Painter extends View {
    float mWidth,mHeight;
    public Painter(Context context) {
        super(context);
    }

    public Painter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        //绘制线条
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setAntiAlias(true);
        p.setStrokeWidth(4);
        p.setStyle(Paint.Style.FILL);
/*        canvas.translate(mWidth/3,mHeight/3);*/
        /*Path path = new Path();
        path.moveTo(0,100);
        path.lineTo(100,0);
        path.lineTo(50,-50);
        path.lineTo(0,0);
        path.lineTo(-50,-50);
        path.lineTo(-100,0);
        *//*path.lineTo(0,100);*//*
        path.close();
        canvas.drawPath(path,p);*/

        //绘制弧线
        /*Path path = new Path();
        Paint  point = new Paint();
        point.setStyle(Paint.Style.FILL);
        point.setStrokeWidth(10);
        point.setColor(Color.GRAY);
        path.moveTo(10,10);
        RectF rectF = new RectF(100,10,200,100);
        path.arcTo(rectF,0,180,true); *///以x轴正方向为0度
        /*canvas.drawPoint(10,10,point);
        canvas.drawRect(rectF,point);*/

        //绘制Region
        /*Path path = new Path();
        path.addCircle(100,100,100, Path.Direction.CW);
        path.addOval(new RectF(200,0,500,200), Path.Direction.CW);
        Region region = new Region();
        region.setPath(path,new Region(new Rect(100,0,500,200)));
        drawRegion(canvas,region,p);
        */

        //裁剪画布
        //裁剪前需要关闭硬件加速功能

        /*setLayerType(LAYER_TYPE_SOFTWARE,null);
        //裁剪画布为给定矩形以外的区域：
        canvas.clipRect(new Rect(10,10,60,60), Region.Op.DIFFERENCE);
        canvas.drawColor(Color.RED);*/

        //区域操作

        /*Rect rect1 = new Rect(100,200,300,240);
        Rect rect2 = new Rect(180,120,220,320);
        canvas.drawRoundRect(new RectF(rect1),10,10,p);
        canvas.drawRoundRect(new RectF(rect2),10,10,p);
        Region region = new Region(rect1);
        region.op(rect2, Region.Op.INTERSECT);
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.FILL);
        p2.setColor(Color.GREEN);
        drawRegion(canvas,region,p2);*/

        //画布的保存和恢复
        //对画布的裁剪，旋转，扭曲等是不可逆的，这里给出保存画布状态的方法。
        canvas.drawColor(Color.GRAY);
        canvas.save();
        //裁剪画布
        canvas.clipRect(new Rect(100,100,200,200), Region.Op.DIFFERENCE);
        canvas.drawColor(Color.BLUE);

        canvas.restore();
        canvas.drawColor(Color.WHITE);

       //



    }
    void drawRegion(Canvas canvas,Region rgn,Paint p){
        RegionIterator it = new RegionIterator(rgn);
        int count = 0 ;
        Rect r = new Rect();
        while(it.next(r)){
            canvas.drawRect(r,p);
        }
    }
}
