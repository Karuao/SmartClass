package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;


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
        Intent intent1=getIntent();
        Bundle b1=intent1.getExtras();
        int id=b1.getInt("id");
        this.userAppAction.modifyPass(pass,passConfirm,id,new ActionCallbackListener<Void>(){
            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RetrieveThreeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toBack(View view) {
        finish();
    }
}