package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/2/2.
 */

public class TeaInformDetailActivity extends SBaseActivity {
    private TextView tvDetial;
    private TextView tvRead_number;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_admin_details);
        initView();
    }

    private void initView() {
        tvDetial= (TextView) findViewById(R.id.tv_class_teainform_details);
        tvRead_number= (TextView) findViewById(R.id.tv_class_inform_readnumber);
        tvTime= (TextView) findViewById(R.id.tv_class_inform_time);


        String detail=getIntent().getStringExtra("detail");
        String time=getIntent().getStringExtra("time");
        String read_num=getIntent().getStringExtra("read_num");

        tvDetial.setText(detail);
        tvRead_number.setText(read_num);
        tvTime.setText(time);
    }

    public void toDeleteInform(View view){
        String inform_id=getIntent().getStringExtra("informid");
        this.informAppAction.deleteInform(inform_id,new ActionCallbackListener<Void>(){

            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeaInformDetailActivity.this, TeaClassMainActivity.class);
                intent.putExtra("classId", getIntent().getStringExtra("classId"));
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
