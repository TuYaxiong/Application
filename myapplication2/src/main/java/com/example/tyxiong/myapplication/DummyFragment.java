package com.example.tyxiong.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tuyaxiong on 19/10/2016.
 * company Ltd
 * 53432973@qq.com
 */
public class DummyFragment extends Fragment
{
    public static final String ARG_SECTION_NUMBER = "section_number";
    // 该方法的返回值就是该Fragment显示的View组件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.button, null);
        TextView textView = (TextView) view.findViewById(R.id.button);
        Bundle bundle = new Bundle();

        textView.setText(String.valueOf(bundle.getInt(ARG_SECTION_NUMBER)));
        return textView;
    }
}