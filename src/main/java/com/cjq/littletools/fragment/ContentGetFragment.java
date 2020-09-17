package com.cjq.littletools.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

public class ContentGetFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_contentget,container,false);
        TextView textView = view.findViewById(R.id.textView_content);
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.parse("content://com.cjq.sharedapp.mycontentprovider/users");
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("uname"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            stringBuilder.append("id: "+id+"姓名："+name+" 年龄："+age+" 性别："+gender+"\n");
        }
        textView.setText(stringBuilder);
        return view;
    }
}
