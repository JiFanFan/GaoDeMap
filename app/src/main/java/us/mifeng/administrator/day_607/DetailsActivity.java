package us.mifeng.administrator.day_607;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;

public class DetailsActivity extends AppCompatActivity {

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
                Intent intent1=new Intent(DetailsActivity.this,RouteActivity.class);
                intent1.putExtra("title",title);
                intent1.putExtra("des",des);
                intent1.putExtra("latlun",latlun);
                startActivity(intent1);
            }
        });
    }
}
