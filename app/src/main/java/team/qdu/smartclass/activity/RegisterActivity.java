package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView mProtocol;
    private DialogInterface.OnClickListener neutralCallback;

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
        mProtocol = (TextView)findViewById(R.id.tv_main_protocol);
        toProtocol();
        mProtocol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showProtocolDialog();
            }
        });
    }

    private void toProtocol() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mProtocol.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan lineSpan = new UnderlineSpan();
        builder.setSpan(lineSpan,8,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //下划线
        builder.setSpan(blueSpan,8,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //字体颜色

        mProtocol.setText(builder);
    }
    private void showProtocolDialog(){
        new AlertDialog.Builder(this)
                .setTitle("用户注册协议")
                .setMessage("1.在使用本系统的所有功能之前,请您务必仔细阅读并透彻理解本声明。您可以选择不使用本 系统,但如果您使用本系统,您的使用行为将被视为对本声明全部内容的认可。\n" +
                        "\n" +
                        "2.关于隐私权:访问者在本系统注册时提供的一些个人资料,本系统除您本人同意外不 会将用户的任何资料以任何方式泄露给第三方。当政府部门、司法机关等依照法定程序要求 本系统披露个人资料时,本系统将根据执法单位之要求或为公共安全之目的提供个人资料, 在此情况下的披露,本系统不承担任何责任。\n"+
                        "\n"+
                        "3.感谢您帮助我们测试学堂小助手APP\n如果发现bug请加群649561970")
                .setNeutralButton("已同意",  new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkEdt.setChecked(true);
                    }
                })
                .create().show();

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
                LoadingDialogUtil.closeDialog();//关闭加载中动画
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }
}
