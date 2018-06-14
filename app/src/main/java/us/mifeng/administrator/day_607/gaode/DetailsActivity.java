package us.mifeng.administrator.day_607.gaode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

import us.mifeng.administrator.day_607.R;

public class DetailsActivity extends AppCompatActivity implements INaviInfoCallback {

    private String title;
    private String des;
    private LatLng latlun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        des = intent.getStringExtra("des");
        latlun = intent.getParcelableExtra("latlun");
        TextView tv_details= (TextView) findViewById(R.id.tv_details);
        tv_details.setText(title);
        tv_details.append("\n");
        tv_details.append(des);
        tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent1=new Intent(DetailsActivity.this,RouteAndNavActivity.class);
                intent1.putExtra("title",title);
                intent1.putExtra("des",des);
                intent1.putExtra("latlun",latlun);
                startActivity(intent1);*/

                AMapLocation myLocation = MyApplication.getMyLocation();

                Poi start = new Poi(myLocation.getPoiName(), new LatLng(myLocation.getLatitude(),myLocation.getLongitude()), "");
                /**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
                Poi end = new Poi(title,latlun, "B000A83M61");
//        List<Poi> wayList = new ArrayList();//途径点目前最多支持3个。
//        wayList.add(new Poi("团结湖", new LatLng(39.93413,116.461676), ""));
//        wayList.add(new Poi("呼家楼", new LatLng(39.923484,116.461327), ""));
//        wayList.add(new Poi("华润大厦", new LatLng(39.912914,116.434247), ""));
                AmapNaviPage.getInstance().showRouteActivity(DetailsActivity.this, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER),DetailsActivity.this);
            }
        });
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
