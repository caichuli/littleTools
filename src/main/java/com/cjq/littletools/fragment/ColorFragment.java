package com.cjq.littletools.fragment;


import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.util.regex.Pattern;

public class ColorFragment extends Fragment {
    EditText mEditText_color;
    TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_color,null,false);
        mEditText_color = view.findViewById(R.id.edit_color);
        mTextView = view.findViewById(R.id.text_color_preview);
        mEditText_color.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String colorStr = charSequence.toString();
                if(colorStr.length()<6){
                    for(int j = 0;j<=6-colorStr.length();j++){
                        colorStr = "0"+colorStr;
                    }
                }
                try {
                    Log.e("-----error-----","颜色代码"+colorStr);
                    int red = Integer.parseInt(colorStr.substring(0,2),16);
                    int green = Integer.parseInt(colorStr.substring(2,4),16);
                    int blue = Integer.parseInt(colorStr.substring(4,6),16);
                    mTextView.setText("预览窗口");
                    mTextView.setBackgroundColor(Color.rgb(red,green,blue));
                    Log.e("----color----"+"red:",red+"green："+green+"blue:"+blue);
                }
                catch (Exception e){
                    Log.e("-----error ------","颜色转换错误");
                    mTextView.setText("颜色代码错误！");
                    mTextView.setBackgroundResource(android.R.drawable.alert_light_frame);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}
