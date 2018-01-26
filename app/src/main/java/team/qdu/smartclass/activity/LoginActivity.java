package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private ToggleButton tbPasswordVisibility;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sprfMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sprfMain.edit();
        //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
        if (sprfMain.getBoolean("main", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        setContentView(R.layout.login);
        initView();
    }

    //初始化View
    private void initView() {
        accountEdt = (EditText) findViewById(R.id.edt_id);
        passwordEdt = (EditText) findViewById(R.id.edt_pass);
        loginBtn = (Button) findViewById(R.id.btn_login);
        tbPasswordVisibility = (ToggleButton) findViewById(R.id.tb_password_visibility);
        this.tbPasswordVisibility.setOnCheckedChangeListener(new ToggleButtonClick());

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
                editorMain.putBoolean("main",true);
                editorMain.commit();
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

    private class ToggleButtonClick implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            //5、判断事件源的选中状态
            if (!isChecked) {
                //显示密码
                //etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // 隐藏密码
                //etPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            //6、每次显示或者关闭时，密码显示编辑的线不统一在最后，下面是为了统一
            passwordEdt.setSelection(passwordEdt.length());
        }
    }
    //将登录的用户的用户名存储进SharedPreferences
    public void storeAccount(String account) {
        SharedPreferences mySharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("account", account);
        editor.putString("userId", "4");
        editor.commit();
    }
}

