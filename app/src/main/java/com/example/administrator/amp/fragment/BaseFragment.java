package com.example.administrator.amp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;

/**
 * Created by Administrator on 2017/4/12.
 */

public abstract class BaseFragment extends Fragment {

    abstract LatLng getTarget();
    abstract CameraPosition getCameraPosition() ;
    abstract void setCameraPosition(CameraPosition cameraPosition);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
