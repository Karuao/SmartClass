package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;

/**
 * 修改个人信息
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class ChangeInfoActivity extends SBaseActivity {

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
                setSpinnerItemSelectedByValue(modifyUserGender,user.getGender());
                setSpinnerItemSelectedByValue(modifyUserUniversity,user.getUniversity());
                setSpinnerItemSelectedByValue(modifyUserDepartment,user.getDepartment());
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
                startActivity(new Intent(ChangeInfoActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSpinnerItemSelectedByValue(AppCompatSpinner spinner,String value){
        SpinnerAdapter spinnerAdapter=spinner.getAdapter();
        int k=spinnerAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(spinnerAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);
                break;
            }
        }
    }
}
