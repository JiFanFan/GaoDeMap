package us.mifeng.administrator.day_607.gaode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import us.mifeng.administrator.day_607.R;

public class CategrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categray);
    }

    public void btn1(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void btn2(View view) {
        startActivity(new Intent(this,PoiActivity.class));
    }
}
