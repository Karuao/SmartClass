package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import team.qdu.smartclass.R;

/**
 * 找回密码页面1
 *
 * Created by Rock on 2017/4/23.
 */

public class Retrieve_one extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_1);

    }


    public void toNext_one(View view) {
        startActivity(new Intent(Retrieve_one.this, Retrieve_two.class));
    }

    public void toBack(View view) {
        finish();
    }
}