package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import team.qdu.smartclass.adapter.HomeworkEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkUncommitAdapter;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.ListViewUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 老师显示已结束作业的学生作业列表
 * Created by 11602 on 2018/3/3.
 */

public class TeaShowFinishedHomeworkListActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView commitStuNumTxt;
    private TextView uncommitStuNumTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView commitHomeworkList;
    private ListView uncommitHomeworkList;
    private ImageView commitFoldedImg;
    private ImageView commitUnFoldedImg;
    private ImageView unCommitFoldedImg;
    private ImageView unCommitUnFoldedImg;
    private String homeworkId;
    //刷新标志
    public static boolean refreshFlag;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_showfinishedhomeworklist);
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
        commitStuNumTxt = (TextView) findViewById(R.id.txt_commitstu_num);
        uncommitStuNumTxt = (TextView) findViewById(R.id.txt_uncommitstu_num);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        commitHomeworkList = (ListView) findViewById(R.id.list_commithomework);
        uncommitHomeworkList = (ListView) findViewById(R.id.list_uncommithomework);
        commitFoldedImg = (ImageView) findViewById(R.id.img_commit_folded);
        commitUnFoldedImg = (ImageView) findViewById(R.id.img_commit_unfolded);
        unCommitFoldedImg = (ImageView) findViewById(R.id.img_uncommit_folded);
        unCommitUnFoldedImg = (ImageView) findViewById(R.id.img_uncommit_unfolded);
        setData();
    }

    private void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
        commitHomeworkList.setOnItemClickListener(this);
    }

    //给页面组件设置数据
    private void setData() {
        //设置作业内容和图片
        homeworkAppAction.getHomeworkDetail(homeworkId, this, new ActionCallbackListener<HomeworkWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getName());
                homeworkDetailTxt.setText(data.getDetail());
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowFinishedHomeworkListActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowFinishedHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已评价、未评价和未提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, this, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
            @Override
            public void onSuccess(List<HomeworkAnswerWithBLOBs> data, String message) {
                List<HomeworkAnswerWithBLOBs> commitHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> uncommitHomeworkAnswer = new ArrayList<>();
                for (HomeworkAnswerWithBLOBs homeworkAnswer : data) {
                    if ("是".equals(homeworkAnswer.getIf_submit())) {
                        commitHomeworkAnswer.add(homeworkAnswer);
                    } else {
                        uncommitHomeworkAnswer.add(homeworkAnswer);
                    }
                }
                commitStuNumTxt.setText(commitHomeworkAnswer.size() + "人");
                uncommitStuNumTxt.setText(uncommitHomeworkAnswer.size() + "人");
                commitHomeworkList.setAdapter(new HomeworkEvaluateAdapter(
                        TeaShowFinishedHomeworkListActivity.this, commitHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        TeaShowFinishedHomeworkListActivity.this, uncommitHomeworkAnswer));
                ListViewUtil.setListViewHeightBasedOnChildren(commitHomeworkList);
                ListViewUtil.setListViewHeightBasedOnChildren(uncommitHomeworkList);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //折叠/展开学生ListView点击事件
    public void foldListView(View view) {
        switch (view.getId()) {
            case R.id.rlayout_commit:
                if (commitHomeworkList.getVisibility() == View.VISIBLE) {
                    commitHomeworkList.setVisibility(View.GONE);
                    commitFoldedImg.setVisibility(View.VISIBLE);
                    commitUnFoldedImg.setVisibility(View.INVISIBLE);
                } else {
                    commitHomeworkList.setVisibility(View.VISIBLE);
                    commitFoldedImg.setVisibility(View.INVISIBLE);
                    commitUnFoldedImg.setVisibility(View.VISIBLE);
                }
                break;
            default:
                if (uncommitHomeworkList.getVisibility() == View.VISIBLE) {
                    uncommitHomeworkList.setVisibility(View.GONE);
                    unCommitFoldedImg.setVisibility(View.VISIBLE);
                    unCommitUnFoldedImg.setVisibility(View.INVISIBLE);
                } else {
                    uncommitHomeworkList.setVisibility(View.VISIBLE);
                    unCommitFoldedImg.setVisibility(View.INVISIBLE);
                    unCommitUnFoldedImg.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == homeworkShowPhotoList) {
            //点击上面显示作业图片的ListView中的图片执行浏览图片操作
            ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter, position);
        } else {
            //点击下面作业提交情况
            String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
            Intent intent = new Intent(this, TeaShowFinishedHomeworkActivity.class);
            intent.putExtra("homeworkAnswerId", homeworkAnswerId);
            startActivity(intent);
        }
    }
}