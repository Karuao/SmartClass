package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * 注册页面
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class RegisterActivity extends SBaseActivity {

    EditText accountEdt;
    EditText passwordEdt;
    EditText passwordEdtConfirm;
    AppCompatSpinner spinQues;
    EditText ansEdt;
    CheckBox checkEdt;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }

    private void initView() {
        accountEdt = (EditText) findViewById(R.id.edt_regId);
        passwordEdt = (EditText) findViewById(R.id.edt_regPass);
        passwordEdtConfirm = (EditText) findViewById(R.id.edt_regPassConfirm);
        spinQues = (AppCompatSpinner) findViewById(R.id.spin_question);
        ansEdt = (EditText) findViewById(R.id.edt_regAnswer);
        checkEdt = (CheckBox) findViewById(R.id.chk_protocol);
        registerBtn = (Button) findViewById(R.id.btn_register);
    }


    public void toRegister(View view) {
        String account = accountEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        String passwordConfirm = passwordEdtConfirm.getText().toString();
        String question = spinQues.getSelectedItem().toString();
        boolean check = checkEdt.isChecked();
        String answer = ansEdt.getText().toString();

        this.userAppAction.register(account, password, passwordConfirm, question, answer, check, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
