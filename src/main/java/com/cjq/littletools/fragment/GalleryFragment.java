package com.cjq.littletools.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.cjq.littletools.R;

public class GalleryFragment extends Fragment {
    GridView mGridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_gallery,null,false);
        mGridView = view.findViewById(R.id.gridview_image);
        Resources res = getContext().getResources();
        Drawable myImage = ResourcesCompat.getDrawable(res,R.drawable.progress_layer,null);
        Drawable drawable = ResourcesCompat.getDrawable(res,R.drawable.progress_layer,null);
        myImage.setAlpha(12);
        Log.d("TAG","myImage改变后，drawable的alpha,myImage的alpha"+myImage.getAlpha()+drawable.getAlpha());
        ImageView imageView_trans = view.findViewById(R.id.image_transition);

        TransitionDrawable transitionDrawable = (TransitionDrawable) imageView_trans.getDrawable();
        transitionDrawable.startTransition(2000);

        return new MyView(this.getContext());


        /*return view;*/
    }
    class MyView extends View{
        private ShapeDrawable mDrawable;
        public MyView(Context context) {
            super(context);
            int x = 10;
            int y = 10;
            int width = 300;
            int height = 50;
            mDrawable = new ShapeDrawable(new OvalShape());
            setContentDescription("定义的drawable");
            mDrawable.getPaint().setColor(Color.RED);
            mDrawable.setBounds(x,y,x+width,y+width);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            /*mDrawable.draw(canvas);*/
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAlpha(50);
            canvas.drawCircle(0,0,100,paint);
        }
    }

}
