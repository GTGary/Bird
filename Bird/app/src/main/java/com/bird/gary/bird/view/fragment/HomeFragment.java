package com.bird.gary.bird.view.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bird.gary.bird.adapter.ListRecycleViewAdapter;
import com.bird.gary.bird.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gary on 2018/3/26.
 */

public class HomeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView title;
    private LinearLayout linearLayout;
    private RecyclerView tapRecyclerView;
    private RecyclerView listRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager sLayoutManager;
    private NestedScrollView scrollView;
    private ListRecycleViewAdapter adapter;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
//        linearLayout = view.findViewById(R.id.fid);
//        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        title = view.findViewById(R.id.text);
//        title.setText("home");
        swipeRefreshLayout = view.findViewById(R.id.home_swiperefresh);
        tapRecyclerView = view.findViewById(R.id.tap_recyclerlist);
        scrollView = view.findViewById(R.id.home_scroll);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mLayoutManager.setAutoMeasureEnabled(true);
        sLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        sLayoutManager.setAutoMeasureEnabled(true);
        listRecyclerView = view.findViewById(R.id.vertical_recyclerlist);
        adapter = new ListRecycleViewAdapter(getData(),getContext());
        swipeRefreshLayout.setColorSchemeResources(R.color.basecolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMoreData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        tapRecyclerView.setLayoutManager(mLayoutManager);
        tapRecyclerView.setHasFixedSize(true);
        tapRecyclerView.setNestedScrollingEnabled(false);
        tapRecyclerView.setAdapter(adapter);
        listRecyclerView.setLayoutManager(sLayoutManager);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setNestedScrollingEnabled(false);
        listRecyclerView.setAdapter(adapter);
        scrollView.scrollTo(0, 0);
        adapter.setOnItemClickListener(new ListRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),"onLongClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(getContext(),"onClick事件       您长时间的点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
            }
        });
        handler.sendEmptyMessageDelayed(1, 100);
        return view;
    }
private ArrayList<String> data;
    private ArrayList<String> getData() {
        data = new ArrayList<>();
        String temp = " item";
        for (int i = 0; i < 6; i++) {
            data.add(i + temp);
        }

        return data;
    }

    public void resetpage(boolean isreset){
        if (isreset){
            handler.sendEmptyMessageDelayed(1, 100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(1, 100);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            scrollView.scrollTo(0, 0);
        }
    };

    private void loadMoreData() {
        List<String> moreList = new ArrayList<>();
        for (int i = 10; i < 13; i++) {
            moreList.add("加载更多的数据");
        }
        data.addAll(moreList);
    }

}
