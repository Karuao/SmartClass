package team.qdu.smartclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;


/**
 * 找回密码页面2
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class RetrieveTwoActivity extends SBaseActivity {

    private EditText answerEdt;
    private TextView quesView;

    public void initView() {
        answerEdt = (EditText) findViewById(R.id.edt_retrieveAnswer);
        quesView = (TextView) findViewById(R.id.tv_question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_2);
        initView();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user",Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", null);
        this.userAppAction.getUserInfor(account,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                quesView.setText(user.getSecurity_question());
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toNext_two(View view) {
        final String inputAnswer = answerEdt.getText().toString();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", null);
        this.userAppAction.getUserInfor(account,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                String answer=user.getSecurity_answer();
                userAppAction.checkSecurityAnswer(inputAnswer, answer, new ActionCallbackListener<Void>() {
                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Void data, String message) {
                        Intent intent = new Intent(RetrieveTwoActivity.this, RetrieveThreeActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toBack(View view) {
        finish();
    }
}