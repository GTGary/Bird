package com.bird.gary.bird.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bird.gary.bird.adapter.ListRecycleViewAdapter;
import com.bird.gary.bird.R;
import com.bird.gary.bird.adapter.TapRecycleViewAdapter;

import java.util.ArrayList;

/**
 * Created by Gary on 2018/3/26.
 */

public class FondFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private TapRecycleViewAdapter adapter;
    boolean isLoading;
    private Handler handler = new Handler();
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fond_fragment_layout,container,false);
        swipeRefreshLayout = view.findViewById(R.id.fond_swiperefresh);
        recyclerView = view.findViewById(R.id.fond_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new TapRecycleViewAdapter(getData(),getContext(),true);
        swipeRefreshLayout.setColorSchemeResources(R.color.basecolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.add("你好啊");
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getContext(), "刷新了一条数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
//                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                data.add("你好啊");
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "刷新了一条数据", Toast.LENGTH_SHORT).show();
                                adapter.notifyItemRemoved(adapter.getItemCount());

                            }
                        }, 1000);
                    }else {

                    }
                }
            }
        });

        return view;
    }
    private ArrayList<String> data;
    private ArrayList<String> getData() {
        data = new ArrayList<>();
        String temp = " item";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }


}
