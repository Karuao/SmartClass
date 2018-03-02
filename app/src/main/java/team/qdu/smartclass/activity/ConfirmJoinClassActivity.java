package team.qdu.smartclass.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.MainClassFragment;

/**
 * Created by 11602 on 2018/2/4.
 */

public class ConfirmJoinClassActivity extends SBaseActivity {

    private ImageView avatarImg;
    private TextView classnameTxt;
    private TextView courseTxt;
    private TextView teacherTxt;
    private Class data;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_confirmjoinclass);
        initView();
    }

    private void initView() {
        avatarImg = (ImageView) findViewById(R.id.img_avatar);
        classnameTxt = (TextView) findViewById(R.id.txt_classname);
        courseTxt = (TextView) findViewById(R.id.txt_course);
        teacherTxt = (TextView) findViewById(R.id.txt_teacher);
        data = (Class) getIntent().getSerializableExtra("data");
        classnameTxt.setText(data.getName());
        courseTxt.setText(data.getCourse());
        teacherTxt.setText("老师：" + data.getTeacher());
        classAppAction.getBitmap(data.getAvatar(), new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                avatarImg.setImageBitmap(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    public void confirmJoinClass(View view) {
        classAppAction.confirmJoinClass(data.getClass_id().toString(), getUserId(), new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data1, String message) {
                Toast.makeText(ConfirmJoinClassActivity.this, message, Toast.LENGTH_SHORT).show();
                setClassId(data.getClass_id().toString());
                setUserTitle("student");
                MainClassFragment.refreshFlag = true;
                application.clearActivity();
                finish();
                startActivity(new Intent(ConfirmJoinClassActivity.this, StuClassMainActivity.class));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ConfirmJoinClassActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //左上角返回点击事件
    public void toBack(View view) {
        finish();
    }
}