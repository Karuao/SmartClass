package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;


/**
 * 找回密码页面3
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class RetrieveThreeActivity extends SBaseActivity {

    private EditText newPass;
    private EditText newPassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_3);
        initView();
    }

    public void initView() {
        newPass = (EditText) findViewById(R.id.edt_retrievePass);
        newPassConfirm = (EditText) findViewById(R.id.edt_retrievePassConfirm);
    }

    public void toNext_three(View view) {
        String pass=newPass.getText().toString();
        String passConfirm=newPassConfirm.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", null);
        //修改用户密码
        this.userAppAction.modifyPass(pass,passConfirm,account,this,new ActionCallbackListener<Void>(){
            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                finish();
                SApplication.clearActivity();
            }
        });
    }
}