package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;


/**
 * 设置页面
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class SetActivity extends SBaseActivity {
    Button logout;
    TextView setAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        logout= (Button) findViewById(R.id.logout);
        initView();
        SharedPreferences sharedPreferences=this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        this.userAppAction.getUserInforByAccount(account,this,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                setAccount.setText(user.getAccount());
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView(){
        setAccount=(TextView) findViewById(R.id.set_userAccount);
    }


    public void toLogout(View view) {
        application.addActivity(SetActivity.this);
        setUserId(null);
        application.clearActivity();
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
    }

    public void toBack2(View view){
        finish();
        application.removeMainActivity(application.activityList);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
                application.removeMainActivity(application.activityList);
            }
        }
            return super.dispatchKeyEvent(event);
        }
}