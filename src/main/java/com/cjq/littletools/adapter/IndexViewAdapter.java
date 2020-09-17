package com.cjq.littletools.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cjq.littletools.R;
import com.cjq.littletools.entity.SingleTool;

import java.util.List;

public class IndexViewAdapter extends RecyclerView.Adapter<IndexViewAdapter.MyViewHolder>{
    private OnItemClickListener mListener;
    Context mContext;
    List<SingleTool> mToolList;
    public IndexViewAdapter(Context context, List<SingleTool> toolList) {
        mContext = context;
        mToolList = toolList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SingleTool singleTool = mToolList.get(position);
        if(singleTool.isEnabled()){
            holder.bind(singleTool);
        }
    }

    @Override
    public int getItemCount() {
        return mToolList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,getAdapterPosition());
                }
            });
            mImageView = itemView.findViewById(R.id.item_image);
            mTextView = itemView.findViewById(R.id.item_name);
        }
        public void bind(SingleTool singleTool){
            if(singleTool.getImage()!=-1){
                mImageView.setImageResource(singleTool.getImage());

            }
            mTextView.setText(singleTool.getName());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public static interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
}
