package com.cjq.littletools.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.os.Environment.DIRECTORY_PICTURES;

public class PhotoFragment extends Fragment {
    ImageButton shot;
    ImageView mImageView;
    OutputStreamWriter osw;
    FileOutputStream fos;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_photo, null, false);
        shot = view.findViewById(R.id.shot_btn);
        mImageView = view.findViewById(R.id.image_photo);
        Log.e("TAG","共享文件目录："+ Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES));
        Log.e("TAG","app专属路径："+this.getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        Log.e("TAG", this.getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC).exists() ? "MUSIC目录存在" : "MuSIC目录不存在");
        File file = this.getContext().getDir("mytest", Context.MODE_PRIVATE);
        if(file.exists()){

        }
        else{
            file.mkdir();
        }
        File myfile = new File(file,"my.txt");
        Log.e("TAG","文件是否存在？"+file.exists());
        if(file.exists()){
            try {
                fos = new FileOutputStream(myfile);
                osw = new OutputStreamWriter(fos);
                osw.write("这是一个测试文件2020_0731");

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(osw!=null){
                        osw.close();

                    }
                    if(fos!=null){
                        fos.close();
                    }

                }
                catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
        Log.e("TAG","内部文件路径："+file.getAbsolutePath());
        //获取内部存储根目录,输出/data
        Log.e("TAG","内部存储目录："+Environment.getDataDirectory());
        //获取app内部存储文件目录，输出/data/user/0/<packageName>/files
        Log.e("TAG","内部存储目录："+this.getActivity().getFilesDir().getAbsolutePath());
        Log.e("TAG","内部存储："+this.getContext().getExternalFilesDir("haha"));
        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //判断目标Activiyt是否存在或者能启动
                if(take.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(take,2);
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        mImageView.setImageBitmap(bitmap);
    }
}
