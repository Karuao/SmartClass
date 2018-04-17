package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by 11602 on 2018/1/31.
 */

public class JoinClassActivity extends SBaseActivity {

    private EditText invitecodeEdt;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_joinclass);
        initView();
    }

    private void initView() {
        invitecodeEdt = (EditText) findViewById(R.id.edt_invitecode);
    }

    //输入邀请码后加入班课点击事件
    public void toJoinClass(View view) {
        LoadingDialogUtil.createLoadingDialog(this, "加载中...");//加载中动画，用来防止用户重复点击
        classAppAction.joinClass(invitecodeEdt.getText().toString(), getUserId(), this, new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                Intent intent = new Intent(JoinClassActivity.this, ConfirmJoinClassActivity.class);
                intent.putExtra("data", data);
                SApplication.addActivity(JoinClassActivity.this);
                startActivity(intent);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(JoinClassActivity.this, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }

}
