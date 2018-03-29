package com.bird.gary.bird.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
/**
 * Created by Gary on 2018/3/26.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Context context;
    //构造方法
    public MyAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }
    //得到item条目
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    //得到数量
    @Override
    public int getCount() {
        return fragments.size();
    }
}
