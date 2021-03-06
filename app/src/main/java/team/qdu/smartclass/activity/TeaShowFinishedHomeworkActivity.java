package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 老师显示已结束作业的学生作业
 * Created by 11602 on 2018/3/4.
 */

public class TeaShowFinishedHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView answerDetailTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private RelativeLayout answerPhotoRlayout;
    private TextView answerExpTxt;
    private TextView evaluateRemarkTxt;
    private RelativeLayout evaluateRemarkRlayout;
    private HorizontalListView homeworkShowPhotoList1;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter1;
    private RelativeLayout evaluatePhotoRlayout;
    private String homeworkAnswerId;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_showfinishedhomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        answerExpTxt = (TextView) findViewById(R.id.txt_answer_exp);
        evaluateRemarkTxt = (TextView) findViewById(R.id.txt_evaluate_remark);
        evaluateRemarkRlayout = (RelativeLayout) findViewById(R.id.rlayout_evaluate_remark);
        homeworkShowPhotoList1 = (HorizontalListView) findViewById(R.id.list_homework_showphoto1);
        evaluatePhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_evaluate_photo);
        setData();
    }

    private void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
        homeworkShowPhotoAdapter1 = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList1.setAdapter(homeworkShowPhotoAdapter1);
        homeworkShowPhotoList1.setOnItemClickListener(this);
    }

    //组件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, this, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                if (!TextUtils.isEmpty(data.getDetail())) {
                    answerDetailTxt.setText(data.getDetail());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowFinishedHomeworkActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    answerPhotoRlayout.setVisibility(View.GONE);
                }
                if (data.getExp() != null) {
                    answerExpTxt.setText(data.getExp().toString());
                }
                if (!TextUtils.isEmpty(data.getRemark())) {
                    evaluateRemarkTxt.setText(data.getRemark());
                } else {
                    evaluateRemarkRlayout.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(data.getRemark_url())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowFinishedHomeworkActivity.this, homeworkShowPhotoAdapter1, data.getRemark_url(), data.getRemark_url_file_num());
                } else {
                    evaluatePhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowFinishedHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == homeworkShowPhotoList) {
            //点击上面显示作业图片的ListView中的图片执行浏览图片操作
            ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter, position);
        } else {
            //点击下面显示作业图片的ListView中的图片执行浏览图片操作
            ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter1, position);
        }
    }
}
