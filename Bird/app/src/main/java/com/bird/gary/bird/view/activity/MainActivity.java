package com.bird.gary.bird.view.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.bird.gary.bird.R;
import com.bird.gary.bird.adapter.HomePageAdapter;
import com.bird.gary.bird.view.fragment.FondFragment;
import com.bird.gary.bird.view.fragment.HomeFragment;
import com.bird.gary.bird.view.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //fragment管理器
    private FragmentManager mFragmentManager;
    //fragment容器
    private List<Fragment> fragments;

    private BottomNavigationView bottomNavigationView;

    private ViewPager myViewPager;
    private HomeFragment homeFragment;
    private FondFragment fondFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        myViewPager = (ViewPager) findViewById(R.id.fist_pager);
        myViewPager.setOnPageChangeListener(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        item.setIcon(getResources().getDrawable(R.drawable.bottom_home_true));
                        myViewPager.setCurrentItem(0);
                        homeFragment.resetpage(true);
                        return true;
                    case R.id.navigation_dashboard:
                        item.setIcon(getResources().getDrawable(R.drawable.bottom_find_true));

                        myViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        item.setIcon(getResources().getDrawable(R.drawable.bottom_user_true));

                        myViewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
        fragments = new ArrayList<Fragment>();
        //实例化Fragment
        homeFragment = new HomeFragment();
        fondFragment = new FondFragment();
        userFragment = new UserFragment();
        fragments.add(homeFragment);
        fragments.add(fondFragment);
        fragments.add(userFragment);
        mFragmentManager = getSupportFragmentManager();
        HomePageAdapter myAdapter = new HomePageAdapter(mFragmentManager, fragments, this);
        //设置适配器
        myViewPager.setAdapter(myAdapter);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        bottomNavigationView.setSelectedItemId(positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
