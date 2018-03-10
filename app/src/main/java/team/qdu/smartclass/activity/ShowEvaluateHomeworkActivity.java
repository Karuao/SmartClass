package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkNotEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkUncommitAdapter;
import team.qdu.smartclass.fragment.TeaHomeworkFinishFragment;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ButtonUtil;

/**
 * Created by 11602 on 2018/3/3.
 */

public class ShowEvaluateHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView evaluateStuNumTxt;
    private TextView notEvaluateStuNumTxt;
    private TextView uncommitStuNumTxt;
    private ImageView homeworkPhotoImg;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView evaluateHomeworkList;
    private ListView notEvaluateHomeworkList;
    private ListView uncommitHomeworkList;
    private String homeworkId;
    private Bitmap homeworkPhoto;
    //刷新标志
    public static boolean refreshFlag;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_evaluate);
        homeworkId = getIntent().getStringExtra("homeworkId");
        initView();
        initEvent();
        refreshFlag = false;
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            setData();
            refreshFlag = false;
        }
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        evaluateStuNumTxt = (TextView) findViewById(R.id.txt_evaluatestu_num);
        notEvaluateStuNumTxt = (TextView) findViewById(R.id.txt_notevaluatestu_num);
        uncommitStuNumTxt = (TextView) findViewById(R.id.txt_uncommitstu_num);
        homeworkPhotoImg = (ImageView) findViewById(R.id.img_homework_photo);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        evaluateHomeworkList = (ListView) findViewById(R.id.list_evaluatehomework);
        notEvaluateHomeworkList = (ListView) findViewById(R.id.list_notevaluatehomework);
        uncommitHomeworkList = (ListView) findViewById(R.id.list_uncommithomework);
        setData();
    }

    private void initEvent() {
        evaluateHomeworkList.setOnItemClickListener(this);
        notEvaluateHomeworkList.setOnItemClickListener(this);
    }

    //给页面组件设置数据
    private void setData() {
        //设置作业内容和图片
        homeworkAppAction.getHomeworkDetail(homeworkId, new ActionCallbackListener<HomeworkWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getName());
                homeworkDetailTxt.setText(data.getDetail());
                if (data.getUrl() != null) {
                    setPhoto(homeworkPhotoImg, data.getUrl());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowEvaluateHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已评价、为评价和未提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
            @Override
            public void onSuccess(List<HomeworkAnswerWithBLOBs> data, String message) {
                List<HomeworkAnswerWithBLOBs> evaluateHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> notEvaluateHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> uncommitHomeworkAnswer = new ArrayList<>();
                for (HomeworkAnswerWithBLOBs homeworkAnswer : data) {
                    if ("是".equals(homeworkAnswer.getIf_submit())) {
                        if (homeworkAnswer.getExp() != null) {
                            evaluateHomeworkAnswer.add(homeworkAnswer);
                        } else {
                            notEvaluateHomeworkAnswer.add(homeworkAnswer);
                        }
                    } else {
                        uncommitHomeworkAnswer.add(homeworkAnswer);
                    }
                }
                evaluateStuNumTxt.setText(evaluateHomeworkAnswer.size() + "人");
                notEvaluateStuNumTxt.setText(notEvaluateHomeworkAnswer.size() + "人");
                uncommitStuNumTxt.setText(uncommitHomeworkAnswer.size() + "人");
                evaluateHomeworkList.setAdapter(new HomeworkEvaluateAdapter(
                        ShowEvaluateHomeworkActivity.this, evaluateHomeworkAnswer));
                notEvaluateHomeworkList.setAdapter(new HomeworkNotEvaluateAdapter(
                        ShowEvaluateHomeworkActivity.this, notEvaluateHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        ShowEvaluateHomeworkActivity.this, uncommitHomeworkAnswer));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(ShowEvaluateHomeworkActivity.this, ShowPhotoActivity.class);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "showPhoto.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            homeworkPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    //结束作业点击事件
    public void toFinishHomework(View view) {
        if (!ButtonUtil.isFastDoubleClick(view.getId())) {
            String notEvaluateStuNum = notEvaluateStuNumTxt.getText().toString();
            if ("0人".equals(notEvaluateStuNum)) {
                //结束作业
                AlertDialog alert;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结束作业")
                        .setMessage("确认结束作业？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                homeworkAppAction.getNotEvaluateStuNum(homeworkId, new ActionCallbackListener<Integer>() {
                                    @Override
                                    public void onSuccess(Integer data, String message) {
                                        changeHomeworkStatus(homeworkId, "评价中");
                                    }

                                    @Override
                                    public void onFailure(String errorEvent, String message) {
                                        Toast.makeText(ShowEvaluateHomeworkActivity.this,
                                                "结束班课失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
            } else {
                //取消操作并提示n人作业未评价
                Toast.makeText(ShowEvaluateHomeworkActivity.this, "仍有" + notEvaluateStuNum
                        + "作业未评价,请评价后再结束作业", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //改变作业状态
    private void changeHomeworkStatus(String homeworkId, final String homeworkStatus) {
        homeworkAppAction.changeHomeworkStatus(homeworkId, homeworkStatus, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                TeaHomeworkUnderwayFragment.refreshFlag = true;
                TeaHomeworkFinishFragment.refreshFlag = true;
                Toast.makeText(ShowEvaluateHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowEvaluateHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                homeworkPhoto = data;
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowEvaluateHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toBack(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
        Intent intent = new Intent(this, EvaluateHomworkActivity.class);
        intent.putExtra("homeworkAnswerId", homeworkAnswerId);
        startActivity(intent);
    }
}