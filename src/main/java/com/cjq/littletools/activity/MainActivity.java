package com.cjq.littletools.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.cjq.littletools.R;
import com.cjq.littletools.fragment.IndexFragment;

public class MainActivity extends AppCompatActivity {
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }*/

        //判断用户是否具有相应权限

        //用户没有权限
        if(ActivityCompat.checkSelfPermission(this,PERMISSIONS[0])!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,PERMISSIONS,122);
        }

        FragmentManager fm = getSupportFragmentManager();
        //这里判断如果该Activity的FragmentManager里仍然存在fragment,则不重新创建
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fm.beginTransaction()
                    .add(R.id.fragment_container,new IndexFragment())
                    .commit();
        }



    }
    //判断应用是否具有权限
    private boolean hasContactPermission(){
        int result = ContextCompat.checkSelfPermission(this,PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==122){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "用户拒绝了授权！", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
