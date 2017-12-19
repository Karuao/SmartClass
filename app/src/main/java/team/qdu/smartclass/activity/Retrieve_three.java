package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import team.qdu.smartclass.R;


/**
 * 找回密码页面3
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class Retrieve_three extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_3);

    }


    public void toNext_three(View view) {
        startActivity(new Intent(Retrieve_three.this, LoginActivity.class));
    }

    public void toBack(View view) {
        finish();
    }

}