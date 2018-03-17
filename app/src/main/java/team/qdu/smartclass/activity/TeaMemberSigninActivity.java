package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.SignInHistoryForTeacherAdapter;

/**
 * Created by asus on 2018/3/15.
 */

public class TeaMemberSigninActivity extends SBaseActivity{

    private ListView listView;
    private TextView classMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_admin_signin);
        initView();
        Intent intent = getIntent();
        String totalMember = intent.getStringExtra("classMember");
        classMember.setText(totalMember);
        getTeacherSignInHistory();
    }


    public void initView(){
        listView = (ListView)findViewById(R.id.list_signin_history_teacher);
        classMember = (TextView)findViewById(R.id.classMember);
    }

    public void signInforTeacher(View view){
        this.memberAppAction.beginSignInForTeacher(getClassId(), new ActionCallbackListener<Attendance>() {
            @Override
            public void onSuccess(Attendance data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeaMemberSigninActivity.this, TeaMemberSigniningActivity.class);
                intent.putExtra("attendanceId",data.getAttendance_id().toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取登录用户的班课成员列表
    private void getTeacherSignInHistory() {
        this.memberAppAction.getTeacherSignInHistory(getClassId(), new ActionCallbackListener<List<Attendance>>() {
            @Override
            public void onSuccess(List<Attendance> data, String message) {
                listView.setAdapter(new SignInHistoryForTeacherAdapter(context,data));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
