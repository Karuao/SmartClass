package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaClassInformFragment;

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
        initView();
    }

    private void initView() {
        tvDetial = (TextView) findViewById(R.id.tv_class_teainform_details);
        LoutRead_number = (LinearLayout) findViewById(R.id.ll_class_inform_readnumber);
        tvTime = (TextView) findViewById(R.id.tv_class_inform_time);
        tvRead_number= (TextView) findViewById(R.id.tv_class_inform_readnumber);
        tvUnRead_number= (TextView) findViewById(R.id.tv_class_inform_unreadnumber);
        LoutUnRead_number = (LinearLayout) findViewById(R.id.ll_class_inform_unreadnumber);
        LoutRead_number.setOnClickListener(this);
        LoutUnRead_number.setOnClickListener(this);

        String detail = getIntent().getStringExtra("detail");
        String time = getIntent().getStringExtra("time");
        String read_num = getIntent().getStringExtra("read_num");

        tvDetial.setText(detail);
        tvRead_number.setText(read_num);
        tvTime.setText(time);
    }

    public void toDeleteInform(View view) {
        String inform_id = getIntent().getStringExtra("informid");
        this.informAppAction.deleteInform(inform_id, new ActionCallbackListener<Void>() {

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                TeaClassInformFragment.refreshFlag = true;
                finish();
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
}
