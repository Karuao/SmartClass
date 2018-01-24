package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.view.View;

import team.qdu.smartclass.R;


/**
 * 签到页面
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class SigninActivity extends SBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_signin);
    }

    public void toBack(View view) {
        finish();
    }
}