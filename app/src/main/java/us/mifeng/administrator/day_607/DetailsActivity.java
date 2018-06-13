package us.mifeng.administrator.day_607;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String des = intent.getStringExtra("des");
        Parcelable latlun = intent.getParcelableExtra("latlun");
        TextView tv_details= (TextView) findViewById(R.id.tv_details);
        tv_details.setText(title);
        tv_details.append("\n");
        tv_details.append(des);
    }
}
