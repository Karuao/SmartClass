package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.SignInHistoryForTeacherAdapter;

/**
 * Created by asus on 2018/3/15.
 */

public class TeaMemberSigninActivity extends SBaseActivity{

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_admin_signin);
        initView();
        getTeacherSignInHistory();
    }


    public void initView(){
        listView = (ListView)findViewById(R.id.list_signin_history_teacher);
    }

    public void signInforTeacher(View view){
        this.memberAppAction.beginSignInForTeacher(getClassId(), new ActionCallbackListener<Attendance>() {
            @Override
            public void onSuccess(Attendance data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeaMemberSigninActivity.this, TeaMemberSigniningActivity.class);
                    intent.putExtra("attendanceId", data.getAttendance_id().toString());
                    JPushInterface.setAlias(context,123,getUserId());
                    startActivity(intent);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取签到历史记录
    private void getTeacherSignInHistory() {
        this.memberAppAction.getTeacherSignInHistory(getClassId(), new ActionCallbackListener<List<Attendance>>() {
            @Override
            public void onSuccess(final List<Attendance> data, String message) {
                listView.setAdapter(new SignInHistoryForTeacherAdapter(context,data));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String attendanceId = data.get(position).getAttendance_id().toString();
                        Intent intent = new Intent(TeaMemberSigninActivity.this,ShowSignInResultActivity.class);
                        intent.putExtra("attendanceId",attendanceId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
