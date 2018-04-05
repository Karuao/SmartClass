package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance_user;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.NotSignInStudentAdapter;
import team.qdu.smartclass.adapter.SignInStudentAdapter;
import team.qdu.smartclass.view.SelectDialog;

/**
 * Created by asus on 2018/3/22.
 */

public class ShowSignInResultActivity extends SBaseActivity{

    private ListView signInStudent;
    private ListView notSignInStudent;
    private TextView signInMember;
    private TextView notSignInMember;
    private ImageButton folding1;
    private ImageButton folding2;
    private ImageButton unfolding1;
    private ImageButton unfolding2;
    //初始设置listview可见
    private  boolean visible1 = true;
    private  boolean visible2 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_signin_result);
        initView();
        getList();
        TeaMemberSigninActivity.refreshFlag = true;
    }

    public void initView(){
        signInStudent = (ListView)findViewById(R.id.list_signin_signIn_student);
        notSignInStudent = (ListView)findViewById(R.id.list_signin_notSignIn_student);
        signInMember = (TextView)findViewById(R.id.iv_class_member_signInMember);
        notSignInMember = (TextView)findViewById(R.id.iv_class_member_notSignInMember);
        folding1 = (ImageButton)findViewById(R.id.folding1);
        folding2 = (ImageButton)findViewById(R.id.folding2);
        unfolding1 = (ImageButton)findViewById(R.id.unfolding1);
        unfolding2 = (ImageButton)findViewById(R.id.unfolding2);
    }

    public void getList(){
        Intent intent = getIntent();
        String attendanceId = intent.getStringExtra("attendanceId");
        this.memberAppAction.getAttendanceUserInfo(attendanceId, new ActionCallbackListener<List<Attendance_user>>() {
            @Override
            public void onSuccess(List<Attendance_user> data, String message) {
                List<Attendance_user> list1= new ArrayList<>();
                List<Attendance_user> list2= new ArrayList<>();
                for (Attendance_user au:data) {
                    if(au.getAttendance_status().equals("已签到")){
                        list1.add(au);
                    }else {
                        list2.add(au);
                    }
                }
                    signInStudent.setAdapter(new SignInStudentAdapter(ShowSignInResultActivity.this, list1));
                    notSignInStudent.setAdapter(new NotSignInStudentAdapter(ShowSignInResultActivity.this, list2));
                    signInMember.setText(String.valueOf(list1.size()));
                    notSignInMember.setText(String.valueOf(list2.size()));
                    signInStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String attendanceUserId = ((TextView)findViewById(R.id.tv_class_sign_attendanceUserId)).getText().toString();
                            List<String> names = new ArrayList<>();
                            names.add("设为已签到");
                            names.add("设为未签到");
                            showDialog(new SelectDialog.SelectDialogListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            ShowSignInResultActivity.this.memberAppAction.setStudentSignIn(attendanceUserId, new ActionCallbackListener<Void>() {
                                                @Override
                                                public void onSuccess(Void data, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    getList();
                                                }

                                                @Override
                                                public void onFailure(String errorEvent, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                        case 1:
                                            ShowSignInResultActivity.this.memberAppAction.setStudentNotSignIn(attendanceUserId, new ActionCallbackListener<Void>() {
                                                @Override
                                                public void onSuccess(Void data, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    getList();
                                                }

                                                @Override
                                                public void onFailure(String errorEvent, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            },names);
                        }
                    });
                    notSignInStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String attendanceUserId = ((TextView)findViewById(R.id.tv_class_sign_attendanceUserId)).getText().toString();
                            List<String> names = new ArrayList<>();
                            names.add("设为已签到");
                            names.add("设为未签到");
                            showDialog(new SelectDialog.SelectDialogListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            ShowSignInResultActivity.this.memberAppAction.setStudentSignIn(attendanceUserId, new ActionCallbackListener<Void>() {
                                                @Override
                                                public void onSuccess(Void data, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    getList();
                                                }

                                                @Override
                                                public void onFailure(String errorEvent, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                        case 1:
                                            ShowSignInResultActivity.this.memberAppAction.setStudentNotSignIn(attendanceUserId, new ActionCallbackListener<Void>() {
                                                @Override
                                                public void onSuccess(Void data, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    getList();
                                                }

                                                @Override
                                                public void onFailure(String errorEvent, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            },names);
                        }
                    });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setVisible1(View view){
        if(visible1==true){
            signInStudent.setVisibility(View.GONE);
            folding1.setVisibility(View.VISIBLE);
            unfolding1.setVisibility(View.GONE);
            visible1=false;
        }else {
            signInStudent.setVisibility(View.VISIBLE);
            folding1.setVisibility(View.GONE);
            unfolding1.setVisibility(View.VISIBLE);
            visible1=true;
        }
    }

    public void setVisible2(View view){
        if(visible2==true){
            notSignInStudent.setVisibility(View.GONE);
            folding2.setVisibility(View.VISIBLE);
            unfolding2.setVisibility(View.GONE);
            visible2=false;
        }else {
            notSignInStudent.setVisibility(View.VISIBLE);
            folding2.setVisibility(View.GONE);
            unfolding2.setVisibility(View.VISIBLE);
            visible2=true;
        }
    }
}
