package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import team.qdu.smartclass.adapter.SignInHistoryForStudentAdapter;
import team.qdu.smartclass.adapter.SignInHistoryForTeacherAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by asus on 2018/3/15.
 */

public class TeaMemberSigninActivity extends SBaseActivity{

    private ListView listView;
    public static boolean refreshFlag = false;
    private SignInHistoryForTeacherAdapter signInHistoryForTeacherAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_admin_signin);
        initView();
        initEvent();
        getTeacherSignInHistory();
    }


    public void initView(){
        listView = (ListView)findViewById(R.id.list_signin_history_teacher);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout_teacher_signIn);
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            getTeacherSignInHistory();
            refreshFlag = false;
        }
    }

    private void initEvent() {
        signInHistoryForTeacherAdapter = new SignInHistoryForTeacherAdapter(this);
        listView.setAdapter(signInHistoryForTeacherAdapter);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSecondary);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefreshLayout.setRefreshing(true);
                // TODO 获取数据
                getTeacherSignInHistory();
            }
        });
    }

    public void signInforTeacher(View view){
        LoadingDialogUtil.createLoadingDialog(this,"加载中...");
        this.memberAppAction.beginSignInForTeacher(getClassId(), new ActionCallbackListener<Attendance>() {
            @Override
            public void onSuccess(Attendance data, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeaMemberSigninActivity.this, TeaMemberSigniningActivity.class);
                    intent.putExtra("attendanceId", data.getAttendance_id().toString());
                    startActivity(intent);
                    LoadingDialogUtil.closeDialog();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();
            }
        });
    }

    //获取签到历史记录
    private void getTeacherSignInHistory() {
        this.memberAppAction.getTeacherSignInHistory(getClassId(), new ActionCallbackListener<List<Attendance>>() {
            @Override
            public void onSuccess(final List<Attendance> data, String message) {
                signInHistoryForTeacherAdapter.setItems(data);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String attendanceId = data.get(position).getAttendance_id().toString();
                        Intent intent = new Intent(TeaMemberSigninActivity.this,ShowSignInResultActivity.class);
                        intent.putExtra("attendanceId",attendanceId);
                        startActivity(intent);
                    }
                });
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
