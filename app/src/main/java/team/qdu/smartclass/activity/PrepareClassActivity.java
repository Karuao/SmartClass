package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;

/**
 * 创建加入班课之前的工作，保证个人信息完整
 * Created by 11602 on 2018/2/1.
 */

public class PrepareClassActivity extends SBaseActivity {

    TextView modifyUserAccount;
    TextView modifyUserName;
    AppCompatSpinner modifyUserGender;
    AppCompatSpinner modifyUserUniversity;
    AppCompatSpinner modifyUserDepartment;
    TextView modifyUserMotto;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_change);
        initView();
        SharedPreferences sharedPreferences=this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        this.userAppAction.getUserInfor(account,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                modifyUserAccount.setText(user.getAccount());
                modifyUserName.setText(user.getName());
                modifyUserMotto.setText(user.getStatus_message());
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView(){
        modifyUserAccount=(TextView) findViewById(R.id.modifyUserAccount);
        modifyUserName=(TextView)findViewById(R.id.modifyUserName);
        modifyUserGender=(AppCompatSpinner) findViewById(R.id.modifyUserGender);
        modifyUserUniversity=(AppCompatSpinner) findViewById(R.id.modifyUserUniversity);
        modifyUserDepartment=(AppCompatSpinner)findViewById(R.id.modifyUserDepartment);
        modifyUserMotto=(TextView) findViewById(R.id.modifyUserMotto);
        btn=(Button)findViewById(R.id.btn_personal_change);
    }

    public void confirmClick(View view) {
        SharedPreferences sharedPreferences=this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String oldAccount=sharedPreferences.getString("account",null);
        String userAccount=modifyUserAccount.getText().toString();
        String userName=modifyUserName.getText().toString();
        String userGender=modifyUserGender.getSelectedItem().toString();
        String userUniversity=modifyUserUniversity.getSelectedItem().toString();
        String userDepartment=modifyUserDepartment.getSelectedItem().toString();
        String userMotto=modifyUserMotto.getText().toString();
        this.userAppAction.modifyUserInformation(userAccount,userName,userGender,userUniversity,userDepartment
                ,userMotto,new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(PrepareClassActivity.this, CreateClassActivity.class));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
