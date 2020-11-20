package com.cjq.littletools.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cjq.littletools.adapter.IndexViewAdapter;
import com.cjq.littletools.R;
import com.cjq.littletools.entity.SingleTool;
import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<SingleTool> mToolList;
    FragmentManager fm;
    private Context mContext;
    private final  static String packageStr = "com.cjq.littletools.fragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mToolList = new ArrayList<>();
        SingleTool colorTool = new SingleTool("16进制颜色",R.drawable.coloricon,packageStr+".ColorFragment");
        mToolList.add(colorTool);
        SingleTool gallery = new SingleTool("图片浏览",R.drawable.imageicon,packageStr+".GalleryFragment");
        mToolList.add(gallery);
        SingleTool gpsTool = new SingleTool("地理位置",R.drawable.gpsicon,packageStr+".GPSFragment");
        mToolList.add(gpsTool);
        SingleTool clipTool = new SingleTool("自定义进度条",R.drawable.baricon,packageStr+".ClipDrawableFragment");
        mToolList.add(clipTool);
        SingleTool animTool = new SingleTool("动画效果",R.drawable.animicon,packageStr+".AnimationFragment");
        mToolList.add(animTool);
        SingleTool contactTool = new SingleTool("电话簿",R.drawable.bookicon,packageStr+".ContactFragment");
        mToolList.add(contactTool);
        SingleTool timeTool = new SingleTool("计时器",R.drawable.timeicon,packageStr+".TimeFragment");
        mToolList.add(timeTool);
        SingleTool contentTool = new SingleTool("访问内容",R.drawable.contenticon,packageStr+".ContentGetFragment");
        mToolList.add(contentTool);
        SingleTool serviceTool = new SingleTool("文件遍历",R.drawable.icon_play,packageStr+".FileFragment");
        mToolList.add(serviceTool);
        SingleTool photoTool = new SingleTool("Camera", R.drawable.icon_camera, packageStr + ".PhotoFragment");
        mToolList.add(photoTool);
        SingleTool viewTool = new SingleTool("自定义视图",R.drawable.icon_view,packageStr+".MyViewFragment");
        mToolList.add(viewTool);
        SingleTool layoutAnimatorTool = new SingleTool("布局动画",R.drawable.icon_view,packageStr+".LayoutAnimatorFragment");
        mToolList.add(layoutAnimatorTool);
        SingleTool pathMeasure = new SingleTool("路径动画",R.drawable.icon_view,packageStr+".PathMeasureFragment");
        mToolList.add(pathMeasure);
        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_index,null,false);
        fm = getActivity().getSupportFragmentManager();


        IndexViewAdapter indexViewAdapter = new IndexViewAdapter(this.getActivity(),mToolList);
        indexViewAdapter.setOnItemClickListener(new IndexViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                /*Toast.makeText(mContext, "点击"+position, Toast.LENGTH_SHORT).show();*/
                try {
                    Class<?> fragment = Class.forName(mToolList.get(position).getFragment());
                    if(fragment != null){
                        fm.beginTransaction()
                                .setCustomAnimations(R.anim.anim_clock,R.anim.anim_trans)
                                .add(R.id.fragment_container, (Fragment) fragment.newInstance())
                                .addToBackStack(null)
                                .hide(IndexFragment.this)
                                .commit();
                    }

                }
                catch (ClassNotFoundException e) {
                    Toast.makeText(mContext, "点击"+position, Toast.LENGTH_SHORT).show();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        mRecyclerView = view.findViewById(R.id.recyclerview_index);
        mRecyclerView.setAdapter(indexViewAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
        return view;

    }
    //建立工具栏菜单项


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_first,menu);
    }
}
