package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.widget.TextView;

import team.qdu.smartclass.R;

/**
 * Created by n551 on 2018/2/2.
 */

public class StuInformDetailActivity extends SBaseActivity {
    private TextView tvDetial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_inform_details);
        initView();
    }

    private void initView() {
        tvDetial= (TextView) findViewById(R.id.tv_class_inform_details);
        String detail=getIntent().getStringExtra("detail");
        tvDetial.setText(detail);
    }
}
