package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * Created by asus on 2018/3/16.
 */

public class TeaMemberSigniningActivity  extends SBaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_signining_teacher);
    }

    public void giveUpSignIn(View view){
        Intent intent = getIntent();
        String attendanceId = intent.getStringExtra("attendanceId");
        this.memberAppAction.giveUpSignIn(attendanceId, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void endSignIn(View view){
        Intent intent = getIntent();
        String attendanceId = intent.getStringExtra("attendanceId");
        this.memberAppAction.endSignIn(attendanceId, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
