package com.example.thirdweekexamdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.thirdweekexamdemo.R;


/**
 * Created by Administrator on 2017/10/20,0020.
 */
public class NewsFragment extends Fragment {

    private ImageView iv;
    private Button bt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news,container,false);
        iv = view.findViewById(R.id.iv);
        bt = view.findViewById(R.id.bt);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
