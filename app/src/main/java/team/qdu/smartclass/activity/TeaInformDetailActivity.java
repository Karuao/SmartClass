package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Inform;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaClassInformFragment;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by n551 on 2018/2/2.
 */

public class TeaInformDetailActivity extends SBaseActivity implements View.OnClickListener {
    private TextView tvDetial;
    private LinearLayout LoutRead_number;
    private LinearLayout LoutUnRead_number;
    private TextView tvRead_number;
    private TextView tvUnRead_number;
    private TextView tvTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_admin_details);

        try {
            initView();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initView() throws URISyntaxException {
        tvDetial = (TextView) findViewById(R.id.tv_class_teainform_details);
        LoutRead_number = (LinearLayout) findViewById(R.id.ll_class_inform_readnumber);
        tvTime = (TextView) findViewById(R.id.tv_class_inform_time);
        tvRead_number = (TextView) findViewById(R.id.tv_class_inform_readnumber);
        tvUnRead_number = (TextView) findViewById(R.id.tv_class_inform_unreadnumber);
        LoutUnRead_number = (LinearLayout) findViewById(R.id.ll_class_inform_unreadnumber);
        String detail = getIntent().getStringExtra("detail");
        String time = getIntent().getStringExtra("time");


        String inform_id = getIntent().getStringExtra("informid");
        getUnreadNum(inform_id);
        tvDetial.setText(detail);

        tvTime.setText(time);
        LoutRead_number.setOnClickListener(this);
        LoutUnRead_number.setOnClickListener(this);

    }

    public void toDeleteInform(final View view) {
        new AlertDialog.Builder(TeaInformDetailActivity.this)
                .setTitle("提示")
                .setMessage("确定要删除此通知？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(TeaInformDetailActivity.this, "加载中...");
                        String inform_id = getIntent().getStringExtra("informid");
                        TeaInformDetailActivity.this.informAppAction.deleteInform(inform_id, TeaInformDetailActivity.this, new ActionCallbackListener<Void>() {

                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                TeaClassInformFragment.refreshFlag = true;
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
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_class_inform_readnumber:
                Intent intent = new Intent(TeaInformDetailActivity.this, InformReadActivity.class);
                intent.putExtra("informid", getIntent().getStringExtra("informid"));
                startActivity(intent);
                break;
            case R.id.ll_class_inform_unreadnumber:
                Intent intent2 = new Intent(TeaInformDetailActivity.this, InformUnReadActivity.class);
                intent2.putExtra("informid", getIntent().getStringExtra("informid"));
                startActivity(intent2);
                break;
        }
    }

    private void getUnreadNum(String informid) {
        LoadingDialogUtil.createLoadingDialog(this, "加载中...");
        TeaInformDetailActivity.this.informAppAction.getUnreadNum(informid, this, new ActionCallbackListener<Inform>() {

            @Override
            public void onSuccess(Inform data, String message) {
                tvRead_number.setText(Integer.toString(data.getRead_num())+ "人已读");
                tvUnRead_number.setText(message + "人未读");
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }
    @Override
    public void toBack(View view) {
        TeaClassInformFragment.refreshFlag = true;
        finish();
    }
    @Override
    public void onBackPressed() {
        TeaClassInformFragment.refreshFlag = true;
        super.onBackPressed();
    }
}
