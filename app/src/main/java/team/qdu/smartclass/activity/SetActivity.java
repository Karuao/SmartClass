package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import team.qdu.smartclass.R;


/**
 * 设置页面
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class SetActivity extends SBaseActivity {

    private TextView userAccount;

    //初始化View
    private void initView() {
        userAccount = (TextView) findViewById(R.id.set_userAccount);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        initView();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", null);
        userAccount.setText(account);
        //显示用户名
    }


    public void toLogout(View view) {
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
    }

    public void toBack(View view) {
        finish();
    }
}