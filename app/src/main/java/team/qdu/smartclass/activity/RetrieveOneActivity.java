package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;

/**
 * 找回密码页面1
 *
 * Created by Rock on 2017/4/23.
 */

public class RetrieveOneActivity extends SBaseActivity {

    private EditText accountEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_1);
        initView();
    }

    public void initView() {
        accountEdt = (EditText) findViewById(R.id.edt_retrieveId);
    }

    public void toNext_one(View view) {
        final String account = accountEdt.getText().toString();
        this.userAppAction.checkAccount(account,new ActionCallbackListener<User>(){
            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(User user, String message) {
                Intent intent = new Intent(RetrieveOneActivity.this, RetrieveTwoActivity.class);
                Bundle b1=new Bundle();
                b1.putString("account",user.getAccount());
                intent.putExtras(b1);
                storeAccount(user.getAccount());
                application.addActivity(RetrieveOneActivity.this);
                startActivity(intent);
            }
        });
    }

    //将登录的用户的用户名存储进SharedPreferences
    public void storeAccount(String account) {
        SharedPreferences mySharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("account", account);
        editor.commit();
    }
}