package com.bird.gary.bird.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bird.gary.bird.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Gary on 2018/3/27.
 */

public class ListRecycleViewAdapter extends RecyclerView.Adapter<ListRecycleViewAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener= onItemClickListener;
    }
    public ListRecycleViewAdapter(ArrayList<String> data, Context context) {
        this.mData = data;
        this.context = context;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 绑定数据
        holder.title.setText(mData.get(position));
        holder.contents.setText("此处是简介");
        Glide.with(context).load("http://www.qinqinxiaodai.com/Images/nav-banner1.png").fitCenter().placeholder(R.drawable.bottom_find_true).into(holder.imageView);
        if( mOnItemClickListener!= null){
            holder. itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView contents;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_tv);
            contents = (TextView) itemView.findViewById(R.id.item_content);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}


