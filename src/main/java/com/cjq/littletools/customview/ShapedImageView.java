package com.cjq.littletools.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class ShapedImageView extends androidx.appcompat.widget.AppCompatImageView {
    public ShapedImageView(Context context) {
        super(context);
    }

    public ShapedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = width>height?height:width;
        canvas.translate(width/2,height/2);
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0,0,radius,circlePaint);
        /*canvas.drawCircle(0,0,radius,circlePaint);*/

        Rect rect = new Rect(-radius,-radius,radius,radius);

        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        circlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(drawable.getBitmap(),-radius,-radius,circlePaint);
        circlePaint.setXfermode(null);
        Paint borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(10);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(0,0,radius,borderPaint);

        circlePaint.setStrokeWidth(0,2);




    }


}
