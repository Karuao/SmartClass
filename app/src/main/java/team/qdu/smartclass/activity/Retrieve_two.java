package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import team.qdu.smartclass.R;


/**
 * 找回密码页面2
 *
 * Created by Rock on 2017/4/23.
 */
public class Retrieve_two extends SBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_2);

    }


    public void toNext_two(View view) {
        startActivity(new Intent(Retrieve_two.this, Retrieve_three.class));
    }

    public void toBack(View view) {
        finish();
    }

}