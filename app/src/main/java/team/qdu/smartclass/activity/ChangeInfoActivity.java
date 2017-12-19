package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import team.qdu.smartclass.R;

/**
 * 修改个人信息
 *
 * Created by Rock on 2017/4/23.
 */

public class ChangeInfoActivity extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_change);

    }


    public void confirmClick(View view) {
        finish();
    }
}
