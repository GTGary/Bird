package com.bird.gary.bird.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bird.gary.bird.R;

/**
 * Created by Gary on 2018/3/26.
 */

public class UserFragment extends Fragment {
    private TextView title;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //引用创建好的xml布局
        View view = inflater.inflate(R.layout.user_fragment_layout,container,false);
//        linearLayout = view.findViewById(R.id.fid);
//        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        title = view.findViewById(R.id.text);
        title.setText("User");
        return view;
    }
}
