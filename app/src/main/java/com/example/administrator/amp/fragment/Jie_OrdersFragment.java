package com.example.administrator.amp.fragment;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.administrator.amp.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Jie_OrdersFragment extends BaseFragment implements LocationSource, AMapLocationListener {

    private static Jie_OrdersFragment jieFragment = null;
    private MapView mapView;
    private AMap aMap;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    private OnLocationChangedListener mListener;
    public AMapLocationClientOption mLocationOption = null;
    private boolean isFirstLoc = true;

    protected static CameraPosition cameraPosition;

    public static Fragment newInstance(){
        if (jieFragment == null){
            synchronized (Jie_OrdersFragment.class){
                if (jieFragment == null){
                    jieFragment = new Jie_OrdersFragment();
                }
            }
        }
        return jieFragment;
    }

    @Override
    LatLng getTarget() {
        return pop();
    }

    @Override
    CameraPosition getCameraPosition() {
        return cameraPosition;
    }

    @Override
    void setCameraPosition(CameraPosition cameraPosition) {
        Jie_OrdersFragment.cameraPosition = cameraPosition;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mapLayout = inflater.inflate(R.layout.jie_orders, null);
        mapView = (MapView) mapLayout.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            aMap = mapView.getMap();
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
            if (getCameraPosition() == null) {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(getTarget(), 10, 0, 0)));
            }else {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition()));
            }
        }
        setUpMap();
        pop();
        return mapLayout;
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    private LatLng pop(){
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.person));
        LatLng point =null;
        double[][] testNum = {{26.452532, 106.524828}, {26.450548, 106.526748},
                {26.449218, 106.524757} };
        //模拟从服务器中获取到的商店信息
        String[] shopName = {"万州烤鱼","君荷酒店","中国工商银行"};
        Bundle bundle = new Bundle();
        for (int i = 0;i<testNum.length;i++){
            bundle.clear();
            bundle.putString("shopName",shopName[i]);
            point = new LatLng(testNum[i][0],testNum[i][1]);
            // 构建Marker图标
            MarkerOptions option = new MarkerOptions().position(point).title(shopName[i]);
            option.icon(bitmapDescriptor);
            option.visible(true);
            aMap.addMarker(option);
            option.draggable(true);//设置Marker可拖动
            option.setGps(true);//设置marker平贴地图效果
        }
        return  point;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        setCameraPosition(aMap.getCameraPosition());
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();//销毁定位客户端。
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**初始化定位*/
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
//           设置定位回调监听
            mLocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setNeedAddress(true);
            mLocationOption.setOnceLocation(false);//设置是否只定位一次,默认为false
            mLocationOption.setWifiActiveScan(true);
            mLocationOption.setMockEnable(false);
            mLocationOption.setInterval(2000);
            mLocationClient.setLocationOption(mLocationOption);//        //给定位客户端对象设置定位参数
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源
                aMapLocation.getLatitude();
                aMapLocation.getLongitude();
                aMapLocation.getAccuracy();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                aMapLocation.getAddress();
                aMapLocation.getCity();
                aMapLocation.getDistrict();
                aMapLocation.getStreet();

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    aMap.animateCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(),
                            aMapLocation.getLongitude())));
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getAddress() + "" + aMapLocation.getCity() + ""
                            + aMapLocation.getDistrict() + "" + aMapLocation.getStreet());
                }
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field field = Fragment.class.getDeclaredField("mChildFragmentManager");
            field.setAccessible(true);
            field.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
