package team.qdu.smartclass.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;
import team.qdu.smartclass.R;

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

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_evaluate);
        homeworkId = getIntent().getStringExtra("homeworkId");
        initView();
        initEvent();
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
        //设置已提交和为提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
            @Override
            public void onSuccess(List<HomeworkAnswerWithBLOBs> data, String message) {
                List<HomeworkAnswerWithBLOBs> evaluateHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> notEvaluateHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> uncommitHomeworkAnswer = new ArrayList<>();
//                for (HomeworkAnswerWithBLOBs homeworkAnswer : data) {
//                    if ("是".equals(homeworkAnswer.getIf_submit())) {
//                        if (homeworkAnswer)
//                        commitHomeworkAnswer.add(homeworkAnswer);
//                    } else {
//                        uncommitHomeworkAnswer.add(homeworkAnswer);
//                    }
//                }
//                commitStuNumTxt.setText(commitHomeworkAnswer.size() + "人");
//                uncommitStuNumTxt.setText(uncommitHomeworkAnswer.size() + "人");
//                commitHomeworkList.setAdapter(new HomeworkCommitAdapter(
//                        CheckHomworkCommitStatusActivity.this, commitHomeworkAnswer));
//                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
//                        CheckHomworkCommitStatusActivity.this, uncommitHomeworkAnswer));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}