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
 * 展示自己提交的作业，不可修改
 * Created by 11602 on 2018/2/26.
 */

public class ShowMyHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private RelativeLayout homeworkPhotoRlayout;
    private TextView answerDetailTxt;
    private RelativeLayout answerDetailRlayout;
    private HorizontalListView homeworkShowPhotoList1;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter1;
    private RelativeLayout answerPhotoRlayout;
    private String homeworkAnswerId;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_stu_homework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        answerDetailRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_detail);
        homeworkShowPhotoList1 = (HorizontalListView) findViewById(R.id.list_homework_showphoto1);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
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

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                if (data.getHomework().getDetail() != null) {
                    homeworkDetailTxt.setText(data.getHomework().getDetail());
                }
                if (data.getHomework().getUrl() != null) {
                    ImgUtil.initHomeworkPhotoList(ShowMyHomeworkActivity.this, homeworkShowPhotoAdapter, data.getHomework().getUrl(), data.getHomework().getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(data.getDetail())) {
                    answerDetailTxt.setText(data.getDetail());
                } else {
                    answerDetailRlayout.setVisibility(View.GONE);
                }
                if (data.getUrl() != null) {
                    ImgUtil.initHomeworkPhotoList(ShowMyHomeworkActivity.this, homeworkShowPhotoAdapter1, data.getUrl(), data.getUrl_file_num());
                } else {
                    answerPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowMyHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
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
