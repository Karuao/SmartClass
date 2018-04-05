package team.qdu.smartclass.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;
import team.qdu.smartclass.adapter.SignInHistoryForStudentAdapter;


/**
 * 签到页面
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class StuMemberSigninActivity extends SBaseActivity {

    private ListView listView;
    private TextView signInRate;
    private SignInHistoryForStudentAdapter signInHistoryForStudentAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean refreshFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_signin_student);
        initView();
        initEvent();
        getStudentSignInHistory();
    }

    public void initView(){
        listView = (ListView)findViewById(R.id.list_signin_history_student);
        signInRate = (TextView)findViewById(R.id.iv_class_member_signInRate);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout_student_signing);
    }

    private void initEvent() {
        signInHistoryForStudentAdapter = new SignInHistoryForStudentAdapter(this);
        listView.setAdapter(signInHistoryForStudentAdapter);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSecondary);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefreshLayout.setRefreshing(true);
                // TODO 获取数据
                getStudentSignInHistory();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getStudentSignInHistory();
            refreshFlag = false;
        }
    }

    //学生签到
    public void signInforStudent(View view){
        this.memberAppAction.getAttendanceInfo(getClassId(), new ActionCallbackListener<List<Attendance>>() {
            @Override
            public void onSuccess(List<Attendance> data, String message) {
                if(data.size()==0){
                    Toast.makeText(context,"未开始签到或签到已结束！",Toast.LENGTH_SHORT).show();
                }else {
                    if (!data.get(0).getIf_open().equals("签到中")) {
                        Toast.makeText(context,"未开始签到或签到已结束！",Toast.LENGTH_SHORT).show();
                    } else {
                        StuMemberSigninActivity.this.memberAppAction.beginSignInForStudent(getUserId(), data.get(0).getAttendance_id().toString(), getClassUserId(),new ActionCallbackListener<Attendance_user>() {
                            @Override
                            public void onSuccess(Attendance_user data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(StuMemberSigninActivity.this,StuMemberSigninActivity.class));
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取签到历史记录
    private void getStudentSignInHistory() {
        this.memberAppAction.getStudentSignInHistory(getUserId(),getClassId(), new ActionCallbackListener<List<Attendance_user>>() {
            @Override
            public void onSuccess(List<Attendance_user> data, String message) {
                double attendanceNum=0.0;
                double totalNum;
                double rate;
                signInHistoryForStudentAdapter.setItems(data);
                totalNum = data.size();
                for (Attendance_user au:data) {
                    if(au.getAttendance_status().equals("已签到")){
                        attendanceNum++;
                    }
                }
                if(totalNum==0){
                    rate=0.0;
                }
                else {
                    rate = attendanceNum / totalNum * 100;
                }
                signInRate.setText(String.valueOf(rate));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}