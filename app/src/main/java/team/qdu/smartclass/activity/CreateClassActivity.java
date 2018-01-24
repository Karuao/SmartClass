package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import team.qdu.smartclass.R;


/**
 * 创建班课
 *
 * Created by Rock on 2017/4/23.
 */
public class CreateClassActivity extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_class);

    }


    public void finishCreate(View view) {
        startActivity(new Intent(CreateClassActivity.this, TestActivity.class));
    }

    public void toBack(View view) {
        finish();
    }

}