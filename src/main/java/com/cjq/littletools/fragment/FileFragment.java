package com.cjq.littletools.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import org.apache.commons.io.FileUtils;
import com.cjq.littletools.R;
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

public class FileFragment extends Fragment {
    Button btn_download,btn_cancel;
    ProgressBar mProgressBar;//进度条
    TextView text_progress,text_message,text_count;
    EditText edit_search;
    File rootFile;
    static int count = 0;
    Handler mHandler;
    StringBuffer mStringBuffer;
    AsyncTask<String,String,String> task;
    FileCountThread thread;
    long allSize;//目录总大小
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service,null,false);


        rootFile =  Environment.getExternalStorageDirectory();
        Log.d("TAG","rootFile " + rootFile.getPath());
        text_progress = view.findViewById(R.id.tv_message);
        /*queryFile(rootFile);*/
        text_message = view.findViewById(R.id.tv_show);
        text_count = view.findViewById(R.id.text_count);
        edit_search = view.findViewById(R.id.edit_search);
        btn_download =view.findViewById(R.id.btn_download);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        mProgressBar = view.findViewById(R.id.progressBar);
        mStringBuffer = new StringBuffer();
        mHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //更新查询信息
                /*if(msg.what == 0x1234){
                    Log.d("TAG","handler " + Thread.currentThread().getName());
                    text_message.setText(mStringBuffer.toString());
                    text_count.setText("共找到"+count+"项");
                }*/
                //更新按钮状态

                if(msg.what == 0x121){
                    btn_download.setText("正在查询...");
                    task = new FileTask();
                    String suffix = edit_search.getText().toString();
                    task.execute(suffix);
                    Log.d("file","Thread " +Thread.currentThread().getName());

                }
            }
        };
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edit_search.getText())){
                    Toast.makeText(FileFragment.this.getActivity(), "您还未输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    btn_download.setEnabled(false);
                    btn_download.setText("准备查询...");
                    thread = new FileCountThread();
                    thread.start();

                }

            }
        });
        /*btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(this!=null&&task.getStatus() == Status.RUNNING){
                task.cancel(true);
            }
            }
        });*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File f2 = new File(text_message.getText().toString());
                File parent = new File(f2.getParent());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                    intent.setDataAndType(Uri.parse("content://"+f2.toString()),"file/*");
                }
                else{
                    intent.setDataAndType(Uri.fromFile(parent),"file/*");
                }
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivity(intent);

            }
        });
        return view;
    }

    private void queryFile(File file){
        if(file.isDirectory()){
            File files[] = file.listFiles();
            Log.e("---path---",file.getAbsolutePath());
            if(files!=null){
                for(File f:files){
                    queryFile(f);
                }
            }
            else{
                return;
            }
        }
        else{
            Log.e("---file---",file.getName());
            return;
        }
    }
    private int countFile(File directory){
        if(directory.isFile()){
            count++;
        }
        else{
            for(File f:directory.listFiles()) {
                countFile(f);
            }
        }
        return count;
    }
    private class FileTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            LinkedList<File> fileList = new LinkedList<>();
            long searchedSize = 0;
            if(rootFile!=null){
                File []files = rootFile.listFiles();
                if(files!=null){
                    for(int i = 0;i<files.length;i++){
                        if(isCancelled()){
                            break;
                        }
                        if(files[i].isDirectory()){
                            fileList.add(files[i]);
                        }
                        else{
                            Log.e("FILESIZE",searchedSize+"B");
                            searchedSize =  searchedSize+files[i].length();
                            if(files[i].getName().endsWith(strings[0])){
                                mStringBuffer.append(files[i].getAbsolutePath()+"\n");
                                count++;
                                /*mHandler.sendEmptyMessage(0x1234);*/
                                String progress = (int)(searchedSize*1.0/allSize*100)+"";
                                publishProgress(new String[]{"共找到"+count+"项",files[0].getAbsolutePath(),progress + "%",progress});
                            }
                        }

                    }
                }

                File tmp;
                while(!fileList.isEmpty()){
                    if(isCancelled()){
                        break;
                    }
                    tmp = fileList.removeFirst();
                    if(tmp.isDirectory()){
                        files = tmp.listFiles();
                        if(files==null){
                            continue;
                        }
                        for(int i = 0;i<files.length;i++){
                            Log.e("file",files[i].getAbsolutePath());
                            if(files[i].isDirectory()){
                                fileList.add(files[i]);
                            }
                            else{
                                searchedSize =  searchedSize+files[i].length();  //统计文件数量
                                Log.e("TAG","当前文件大小"+searchedSize+"总数："+allSize);
                                if(files[i].getName().endsWith(strings[0])){
                                    mStringBuffer.append(files[i].getAbsolutePath()+"\n");
                                    count++;
                                    /*mHandler.sendEmptyMessage(0x1234);*/
                                    String progress = (int)(searchedSize*1.0/allSize*100)+"";
                                    publishProgress(new String[]{"共找到"+count+"项",files[0].getAbsolutePath(),progress + "%",progress});
                                }

                            }
                            /*Log.d("TAG","Thread " +Thread.currentThread().getName()+"进度："+searchedSize/allSize+"%");*/
                        }
                    }
                }
            }
            return "查询结束！";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            text_count.setText(values[0]);
            /*mStringBuffer.append(values[1]);*/
            text_message.setText(values[1]);
            text_progress.setText(values[2]);
            mProgressBar.setProgress(Integer.valueOf(values[3]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            btn_download.setText("查询");
            btn_download.setEnabled(true);
            Toast.makeText(FileFragment.this.getActivity(), s, Toast.LENGTH_SHORT).show();

        }
    }

    class FileCountThread extends Thread{
        public FileCountThread() {
            super();
        }

        @Override
        public void run() {
            allSize = FileUtils.sizeOfDirectory(rootFile);
            mHandler.sendEmptyMessage(0x121);//统计返回
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        count = 0;
        if(task!=null){
            task.cancel(true);
        }

    }
}
