package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * 登陆
 *
 * Created by Rock on 2017/4/23.
 */

public class LoginActivity extends SBaseActivity {

    private EditText emailEdt;
    private EditText passwordEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    //初始化View
    private void initView() {
        emailEdt = (EditText) findViewById(R.id.edt_id);
        passwordEdt = (EditText) findViewById(R.id.edt_pass);
        loginBtn = (Button) findViewById(R.id.btn_login);
    }

    public void toLogin(View view) {
        String email = emailEdt.getText().toString();
        String password = passwordEdt.getText().toString();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.appAction.login(email, password, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
        startActivity(new Intent(LoginActivity.this,Retrieve_one.class));
    }
}
