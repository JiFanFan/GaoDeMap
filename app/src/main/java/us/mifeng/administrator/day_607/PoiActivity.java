package us.mifeng.administrator.day_607;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

public class PoiActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener{

    private EditText editText;
    PoiSearch.Query query;
    PoiSearch poiSearch;
    private MapView mapView;
    private AMap aMap;
    private ArrayList<Marker>markerList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        editText = (EditText) findViewById(R.id.editText);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
    }

    public void serach(View view) {
        String search_text=editText.getText().toString().trim();
        if (!TextUtils.isEmpty(search_text)){
            //构造Query对象
            query = new PoiSearch.Query(search_text, "", "北京");
            //keyWord表示搜索字符串，
            //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
            //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码

            //构造 PoiSearch 对象，并设置监听
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            //发送请求
            poiSearch.searchPOIAsyn();
        }else {
            Toast.makeText(this, "您输入的信息为空，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> pois = poiResult.getPois();
        initMarker(pois);
    }

    private void initMarker(ArrayList<PoiItem> pois) {
        for (final PoiItem poiItem:pois){
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            LatLng latLng=new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
            BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).icon(bitmapDescriptor).title(poiItem.getTitle()).snippet(poiItem.getSnippet()));
            markerList.add(marker);
            aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    View view=View.inflate(PoiActivity.this,R.layout.infowindow_layout,null);
                    ImageView image= (ImageView) view.findViewById(R.id.image);
                    TextView title= (TextView) view.findViewById(R.id.title);
                    TextView content= (TextView) view.findViewById(R.id.content);
                    image.setImageResource(R.mipmap.ic_launcher);
                    title.setText(marker.getTitle());
                    content.setText(marker.getSnippet());
                    return view;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    return null;
                }
            });

            //aMap点击事件
            aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    for (Marker marker:markerList){
                        if (marker.isInfoWindowShown()){
                            marker.hideInfoWindow();
                        }
                    }
                }
            });
            //marker点击事件
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (marker.isInfoWindowShown()){
                        marker.hideInfoWindow();
                    }else {
                        marker.showInfoWindow();
                    }
                    return true;
                }
            });
            //infowindow点击事件
            aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent=new Intent(PoiActivity.this,DetailsActivity.class);
                    intent.putExtra("latlun",marker.getPosition());
                    intent.putExtra("title",marker.getTitle());
                    intent.putExtra("des",marker.getSnippet());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
