package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
}
