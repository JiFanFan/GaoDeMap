package us.mifeng.administrator.day_607;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import us.mifeng.administrator.day_607.baidu.HomeActivity;
import us.mifeng.administrator.day_607.gaode.CategrayActivity;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    public void gaoDe(View view) {
        startActivity(new Intent(this, CategrayActivity.class));
    }

    public void baiDu(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void tengXun(View view) {

    }
}
