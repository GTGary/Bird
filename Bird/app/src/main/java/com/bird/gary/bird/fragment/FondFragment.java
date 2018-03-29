package com.bird.gary.bird.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bird.gary.bird.ListRecycleViewAdapter;
import com.bird.gary.bird.R;

import java.util.ArrayList;

/**
 * Created by Gary on 2018/3/26.
 */

public class FondFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListRecycleViewAdapter adapter;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.fond_fragment_layout,container,false);
        swipeRefreshLayout = view.findViewById(R.id.fond_swiperefresh);
        recyclerView = view.findViewById(R.id.fond_recyclerview);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new ListRecycleViewAdapter(getData());
        swipeRefreshLayout.setColorSchemeResources(R.color.basecolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }
    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " item";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }


}
