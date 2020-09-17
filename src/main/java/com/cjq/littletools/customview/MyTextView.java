package com.cjq.littletools.customview;

import android.content.Context;
import android.util.AttributeSet;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView{

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCharText(Character charText){
        this.setText(String.valueOf(charText));
    }

}