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
 * 老师显示进行中作业的学生提交的作业
 * Created by 11602 on 2018/3/3.
 */

public class TeaShowUnderwayHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView underwayHomeworkTitleTxt;
    private TextView underwayAnswerDetailTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private RelativeLayout underwayAnswerPhotoRlayout;
    private String homeworkAnswerId;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_showunderwayhomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    private void initView() {
        underwayHomeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        underwayAnswerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        underwayAnswerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_underwayanswer_photo);
        setData();
    }

    private void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, this, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                underwayHomeworkTitleTxt.setText(data.getHomework().getName());
                if (!TextUtils.isEmpty(data.getDetail())) {
                    underwayAnswerDetailTxt.setText(data.getDetail());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowUnderwayHomeworkActivity.this,
                            homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    underwayAnswerPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowUnderwayHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击上面显示作业图片的ListView中的图片执行浏览图片操作
        ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter, position);
    }
}
