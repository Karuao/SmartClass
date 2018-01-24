package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ApiResponse;
import team.qdu.model.User;
import team.qdu.smartclass.R;


/**
 * 找回密码页面2
 *
 * Created by Rock on 2017/4/23.
 */
public class Retrieve_two extends SBaseActivity {


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
        Intent intent2=getIntent();
        Bundle b2=intent2.getExtras();
        String question=b2.getString("question");
        quesView.setText(question);
    }

    public void toNext_two(View view) {
        final String inputAnswer = answerEdt.getText().toString();
        Intent intent3=getIntent();
        Bundle b3=intent3.getExtras();
        String answer=b3.getString("answer");
        Intent intent4=getIntent();
        Bundle b4=intent4.getExtras();
        final int id=b4.getInt("id");
        this.appAction.checkSecurityAnswer(inputAnswer,answer,new ActionCallbackListener<Void>(){
            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Retrieve_two.this, Retrieve_three.class);
                Bundle b5=new Bundle();
                b5.putInt("id",id);
                intent.putExtras(b5);
                startActivity(intent);
            }
        });
    }

    public void toBack(View view) {
        finish();
    }

}