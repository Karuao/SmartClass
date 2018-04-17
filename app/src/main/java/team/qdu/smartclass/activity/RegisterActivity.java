package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.util.LoadingDialogUtil;

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
        LoadingDialogUtil.createLoadingDialog(this, "注册中...");
        String account = accountEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        String passwordConfirm = passwordEdtConfirm.getText().toString();
        String question = spinQues.getSelectedItem().toString();
        boolean check = checkEdt.isChecked();
        String answer = ansEdt.getText().toString();

        this.userAppAction.register(account, password, passwordConfirm, question, answer, check, this,new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                finish();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }
}
