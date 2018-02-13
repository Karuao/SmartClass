package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
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
        this.userAppAction.getUserInforByAccount(account,new ActionCallbackListener<User>() {
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
        resetSprfMain();
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
        SetActivity.this.finish();

    }
    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.commit();
    }

    public void toBack(View view) {
        finish();
    }
}