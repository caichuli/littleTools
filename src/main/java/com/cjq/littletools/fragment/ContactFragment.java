package com.cjq.littletools.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    ListView mListView;
    List<String> contactList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_contact,null,false);
        mListView = view.findViewById(R.id.listview_contact);

        getData();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(),R.layout.listview_item,contactList);
        mListView.setAdapter(arrayAdapter);
        return view;
    }
    private void getData(){
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String data = name+" : "+phone;
                contactList.add(data);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();

        if(actionBar!=null){
           actionBar.hide();
        }

    }

}
