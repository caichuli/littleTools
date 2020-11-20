package com.cjq.littletools.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjq.littletools.R;
import com.cjq.littletools.customview.CircleSeekBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PathMeasureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PathMeasureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    float progress = 0;

    public PathMeasureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PathMeasureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PathMeasureFragment newInstance(String param1, String param2) {
        PathMeasureFragment fragment = new PathMeasureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_path_measure, container, false);
        final CircleSeekBar circleSeekBar = view.findViewById(R.id.progress_circle);
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                circleSeekBar.setProgress(progress);
                if(progress<100){
                    progress = progress+0.5f;
                }
                else{
                    progress = 0;
                }
                circleSeekBar.setProgress(progress);
            }
        },0,40);*/
        return view;

    }
}