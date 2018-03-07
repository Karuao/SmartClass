package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.StuClassInformFragment;

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
        tvDetial= (TextView) findViewById(R.id.txt_commitstu_sno);
        String detail=getIntent().getStringExtra("detail");
        tvDetial.setText(detail);
    }
    public void toBack(View view) {
        StuClassInformFragment.refreshFlag = true;
        finish();

    }
    @Override
    public void onBackPressed() {
        StuClassInformFragment.refreshFlag = true;
        super.onBackPressed();
    }
}
