package us.mifeng.administrator.day_607.gaode;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;

import us.mifeng.administrator.day_607.R;
import us.mifeng.administrator.day_607.gaode.overlay.DrivingRouteOverlay;

public class RouteActivity extends AppCompatActivity implements OnRouteSearchListener{
    RouteSearch routeSearch;
    private AMap aMap;
    private Location myLocation;
    private LatLng latlun;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initMapView(savedInstanceState);
        //initLocation();
        initIntent();
        initSearchDriveRoute();
    }

    private void initLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //实现定位功能
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //启动定位后1秒拿到地址
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                myLocation = aMap.getMyLocation();
                if(myLocation ==null){
                    Toast.makeText(RouteActivity.this, "无法获取当前位置", Toast.LENGTH_SHORT).show();
                }
            }
        },1000);
    }

    private void initSearchDriveRoute() {
        //初始化对象
        routeSearch = new RouteSearch(this);
        //设置数据回调监听
        routeSearch.setRouteSearchListener(this);
        myLocation=MyApplication.getMyLocation();
        if (myLocation!=null){
            LatLonPoint startLatLonPoint=new LatLonPoint(myLocation.getLatitude(),myLocation.getLongitude());
            LatLonPoint endLatLonPoint=new LatLonPoint(latlun.latitude,latlun.longitude);
            RouteSearch.FromAndTo fromAndTo=new RouteSearch.FromAndTo(startLatLonPoint,endLatLonPoint);
            // fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
            // 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, null, null, "");
            //发送请求
            routeSearch.calculateDriveRouteAsyn(query);
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent!=null){
            String title = intent.getStringExtra("title");
            String des = intent.getStringExtra("des");
            latlun = intent.getParcelableExtra("latlun");
        }
    }

    private void initMapView(Bundle savedInstanceState) {
        MapView mapView= (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    //接收数据
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int i) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    final DrivePath drivePath = result.getPaths()
                            .get(0);
                    if(drivePath == null) {
                        return;
                    }
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            RouteActivity.this, aMap, drivePath,
                            result.getStartPos(),
                            result.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                }

            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
