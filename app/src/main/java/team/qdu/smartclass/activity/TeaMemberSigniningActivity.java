package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;
import team.qdu.smartclass.adapter.SignInStudentAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by asus on 2018/3/16.
 */

public class TeaMemberSigniningActivity extends SBaseActivity {

    private TextView classTotalMember;
    public TextView signInStuNum;
    private ListView stuSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_signining_teacher);
        initView();
        SApplication.addActivity(this);
        getSignInStudent();
    }

    public void initView() {
        classTotalMember = (TextView) findViewById(R.id.class_member_signining_allstudent);
        stuSignIn = (ListView) findViewById(R.id.signIn_list_student_forTeacher);
        signInStuNum = (TextView) findViewById(R.id.class_member_signining_signinstudent);
    }

    //放弃签到
    public void giveUpSignIn(View view) {
        Intent intent = getIntent();
        final String attendanceId = intent.getStringExtra("attendanceId");
        new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                .setTitle("提示")
                .setMessage("确定要放弃本次签到？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                        TeaMemberSigniningActivity.this.memberAppAction.giveUpSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                SApplication.clearActivity();
                                finish();
                                LoadingDialogUtil.closeDialog();
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                LoadingDialogUtil.closeDialog();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //结束签到
    public void endSignIn(View view) {
        Intent intent = getIntent();
        final String attendanceId = intent.getStringExtra("attendanceId");
        new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                .setTitle("提示")
                .setMessage("确定要结束本次签到？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                        TeaMemberSigniningActivity.this.memberAppAction.endSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                finish();
                                SApplication.clearActivity();
                                Intent intent1 = new Intent(TeaMemberSigniningActivity.this, ShowSignInResultActivity.class);
                                intent1.putExtra("attendanceId", attendanceId);
                                startActivity(intent1);
                                LoadingDialogUtil.closeDialog();
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                LoadingDialogUtil.closeDialog();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    public void getSignInStudent() {
        this.memberAppAction.getAttendanceInfo(getClassId(), this, new ActionCallbackListener<List<Attendance>>() {
            @Override
            public void onSuccess(List<Attendance> data, String message) {
                classTotalMember.setText(data.get(0).getStu_num().toString());
                signInStuNum.setText(data.get(0).getAttendance_stu_count().toString());
                final String attendanceId = data.get(0).getAttendance_id().toString();
                TeaMemberSigniningActivity.this.memberAppAction.getAttendanceUserInfo(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<List<Attendance_user>>() {
                    @Override
                    public void onSuccess(final List<Attendance_user> data, String message) {
                        List<Attendance_user> list = new ArrayList<>();
                        for (Attendance_user au : data) {
                            if (au.getAttendance_status().equals("已签到")) {
                                list.add(au);
                            }
                        }
                        stuSignIn.setAdapter(new SignInStudentAdapter(TeaMemberSigniningActivity.this, list));
                        if (list.size() == data.size()) {
                            new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                                    .setTitle("提示")
                                    .setMessage("所有班课成员已签到，是否结束本次签到？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                                            TeaMemberSigniningActivity.this.memberAppAction.endSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                                                @Override
                                                public void onSuccess(Void data, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    SApplication.clearActivity();
                                                    Intent intent1 = new Intent(TeaMemberSigniningActivity.this, ShowSignInResultActivity.class);
                                                    intent1.putExtra("attendanceId", attendanceId);
                                                    startActivity(intent1);
                                                    LoadingDialogUtil.closeDialog();
                                                }

                                                @Override
                                                public void onFailure(String errorEvent, String message) {
                                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                    LoadingDialogUtil.closeDialog();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toBack3(View view) {
        Intent intent = getIntent();
        final String attendanceId = intent.getStringExtra("attendanceId");
        new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                .setTitle("提示")
                .setMessage("确定要放弃本次签到？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                        TeaMemberSigniningActivity.this.memberAppAction.giveUpSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                SApplication.clearActivity();
                                finish();
                                LoadingDialogUtil.closeDialog();
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                LoadingDialogUtil.closeDialog();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
                Intent intent = getIntent();
                final String attendanceId = intent.getStringExtra("attendanceId");
                new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                        .setTitle("提示")
                        .setMessage("确定要放弃本次签到？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                                TeaMemberSigniningActivity.this.memberAppAction.giveUpSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                                    @Override
                                    public void onSuccess(Void data, String message) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        SApplication.clearActivity();
                                        finish();
                                        LoadingDialogUtil.closeDialog();
                                    }

                                    @Override
                                    public void onFailure(String errorEvent, String message) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        LoadingDialogUtil.closeDialog();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        final String attendanceId = intent.getStringExtra("attendanceId");
        new AlertDialog.Builder(TeaMemberSigniningActivity.this)
                .setTitle("提示")
                .setMessage("确定要放弃本次签到？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(TeaMemberSigniningActivity.this, "加载中...");
                        TeaMemberSigniningActivity.this.memberAppAction.giveUpSignIn(attendanceId, TeaMemberSigniningActivity.this, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                SApplication.clearActivity();
                                finish();
                                LoadingDialogUtil.closeDialog();
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                LoadingDialogUtil.closeDialog();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
