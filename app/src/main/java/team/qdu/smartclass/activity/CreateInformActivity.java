package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaClassInformFragment;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by n551 on 2018/2/1.
 */

public class CreateInformActivity extends SBaseActivity {
    private EditText detailEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_admin_push);
        initView();
    }

    private void initView() {
        detailEdt = (EditText) findViewById(R.id.Inform_Detail);
    }

    public void toPush(View view) throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");
        String classid = getClassId();
        String detail = detailEdt.getText().toString();

        this.informAppAction.createInform(classid, detail, this, new ActionCallbackListener<Void>() {

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                TeaClassInformFragment.refreshFlag = true;
                finish();
                LoadingDialogUtil.closeDialog();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();
            }
        });
    }
}
