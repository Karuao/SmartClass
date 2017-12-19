package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import team.qdu.smartclass.R;


/**
 * 设置页面
 *
 * Created by Rock on 2017/4/23.
 */
public class SetActivity extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

    }


    public void toLogout(View view) {
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
    }

    public void toBack(View view) {
        finish();
    }

}