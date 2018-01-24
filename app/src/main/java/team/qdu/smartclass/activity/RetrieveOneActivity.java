package team.qdu.smartclass.activity;

import android.content.Intent;
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
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RetrieveOneActivity.this, RetrieveTwoActivity.class);
                Bundle b1=new Bundle();
                Bundle b2=new Bundle();
                Bundle b3=new Bundle();
                b1.putString("question",user.getSecurity_question());
                b2.putString("answer",user.getSecurity_answer());
                b3.putInt("id",user.getUser_id());
                intent.putExtras(b1);
                intent.putExtras(b2);
                intent.putExtras(b3);
                startActivity(intent);
            }
        });
    }

    public void toBack(View view) {
        finish();
    }
}