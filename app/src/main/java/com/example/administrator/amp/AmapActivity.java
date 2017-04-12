package com.example.administrator.amp;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.amp.fragment.Jie_OrdersFragment;
import com.example.administrator.amp.fragment.MainText_Fragment;
import com.example.administrator.amp.fragment.Xia_OrdersFragment;
import com.example.administrator.amp.setting_personal.PersonCenter;

/**
 * Created by Administrator on 2017/3/31.
 */

public class AmapActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {//implements LocationSource, AMapLocationListener

    private BottomNavigationBar bottomNavigationBar;
    private Jie_OrdersFragment jie_ordersFragment;
    private Xia_OrdersFragment xia_ordersFragment;
    private MainText_Fragment mainText_Fragment;

    private Toolbar toolbar;
    private TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.amap_text);

        //定位权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        textTitle = (TextView) findViewById(R.id.textTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.person);
        textTitle.setText("爱帮忙");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AmapActivity.this, PersonCenter.class);
                startActivity(intent);
            }
        });
        bottomBar();
    }

    public void bottomBar() {

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(bottomNavigationBar.MODE_DEFAULT);
        bottomNavigationBar.setBackgroundStyle(bottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.jiedan, "下单")
                .setActiveColor(R.color.navigationBarColor));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.xiadan, "发布")
                .setActiveColor(R.color.navigationBarColor));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.jiedan, "接单")
                .setActiveColor(R.color.navigationBarColor));

        bottomNavigationBar.setFirstSelectedPosition(1).initialise();
        bottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
    }
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mainText_Fragment = (MainText_Fragment) MainText_Fragment.newInstance();
        transaction.replace(R.id.fragment, mainText_Fragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (position) {
            case 0: {//下单
                textTitle.setText("下单");
                if (xia_ordersFragment == null) {
                    xia_ordersFragment = (Xia_OrdersFragment) Xia_OrdersFragment.newInstance();
                }
                transaction.replace(R.id.fragment, xia_ordersFragment);
            }
            break;
            case 1: {//发布
                textTitle.setText("爱帮忙");
                if (mainText_Fragment == null) {
                    mainText_Fragment = (MainText_Fragment) MainText_Fragment.newInstance();
                }
                transaction.replace(R.id.fragment, mainText_Fragment);
                Intent intent = new Intent(AmapActivity.this, Announce_Activity.class);
                startActivity(intent);
            }
            break;
            case 2: {//接单
                textTitle.setText("接单");
                if (jie_ordersFragment == null) {
                    jie_ordersFragment = (Jie_OrdersFragment) Jie_OrdersFragment.newInstance();
                }
                transaction.replace(R.id.fragment, jie_ordersFragment);
            }
            break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}