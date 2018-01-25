package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * 登陆
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class LoginActivity extends SBaseActivity {

    private EditText accountEdt;
    private EditText passwordEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    //初始化View
    private void initView() {
        accountEdt = (EditText) findViewById(R.id.edt_id);
        passwordEdt = (EditText) findViewById(R.id.edt_pass);
        loginBtn = (Button) findViewById(R.id.btn_login);
    }

    public void toLogin(View view) {
        final String account = accountEdt.getText().toString();
        String password = passwordEdt.getText().toString();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.userAppAction.login(account, password, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle b1 = new Bundle();
                b1.putString("account", account);
                intent.putExtras(b1);
                storeAccount(account);
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void toFindPass(View view) {
        startActivity(new Intent(LoginActivity.this, RetrieveOneActivity.class));
    }

    //将登录的用户的用户名存储进SharedPreferences
    public void storeAccount(String account) {
        SharedPreferences mySharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("account", account);
        editor.commit();
    }
}
