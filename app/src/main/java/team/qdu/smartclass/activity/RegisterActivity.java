package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import team.qdu.smartclass.R;

/**
 * 注册页面
 *
 * Created by Rock on 2017/4/23.
 */

public class RegisterActivity extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

    }


    public void toRegister(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void toBack(View view) {
        finish();
    }
}
