package com.bird.gary.bird.adapter;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bird.gary.bird.R;

import java.util.List;

/**
 * Created by Gary on 2018/3/27.
 */

public class TapRecycleViewAdapter extends Adapter<ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List data;
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public TapRecycleViewAdapter(List data, Context context,boolean hasMore) {
        this.context = context;
        this.data = data;
        this.hasMore = hasMore;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    View itemview;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_rv_item, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_footer, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder)holder).tv.setText(data.get(position).toString());
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }else {
            if (hasMore){
                ((FootViewHolder) holder).footLayout.setVisibility(View.VISIBLE);
                ((FootViewHolder)holder).progressBar.setVisibility(View.VISIBLE);
                ((FootViewHolder)holder).textView.setText("努力加载中...");
            }else {
                ((FootViewHolder)holder).progressBar.setVisibility(View.INVISIBLE);
                ((FootViewHolder)holder).textView.setText("没有更多数据");
                // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏提示条
                        ((FootViewHolder) holder).footLayout.setVisibility(View.GONE);
                        // 将fadeTips设置true
                        fadeTips = true;
                        // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                        hasMore = true;
                    }
                }, 1500);
            }
        }
    }


    static class ItemViewHolder extends ViewHolder {

        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.item_content);
        }
    }

    static class FootViewHolder extends ViewHolder {

        private ProgressBar progressBar;
        private TextView textView;
        private RelativeLayout footLayout;

        public FootViewHolder(View view) {
            super(view);
            footLayout = view.findViewById(R.id.foot_layout);
            progressBar =  view.findViewById(R.id.progressBar);
            textView =  view.findViewById(R.id.foot_text);
        }
    }
    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<String> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            data.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
}


