package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;

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
        classAppAction.joinClass(invitecodeEdt.getText().toString(), getUserId(), new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                Intent intent = new Intent(JoinClassActivity.this, ConfirmJoinClassActivity.class);
                intent.putExtra("data", data);
                application.addActivity(JoinClassActivity.this);
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(JoinClassActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
