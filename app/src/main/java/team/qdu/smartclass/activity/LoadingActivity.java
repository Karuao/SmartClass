package team.qdu.smartclass.activity;


import android.os.Bundle;

import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;

public class LoadingActivity extends SBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SApplication.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }
}