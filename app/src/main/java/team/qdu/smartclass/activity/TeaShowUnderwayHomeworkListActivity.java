package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import team.qdu.smartclass.adapter.HomeworkCommitAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkUncommitAdapter;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.ListViewUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 老师显示进行中作业的学生作业列表
 * Created by 11602 on 2018/3/1.
 */

public class TeaShowUnderwayHomeworkListActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView commitStuNumTxt;
    private TextView uncommitStuNumTxt;
    private HorizontalListView homeworkShowPhotoList;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView commitHomeworkList;
    private ListView uncommitHomeworkList;
    private ImageView commitFoldedImg;
    private ImageView commitUnFoldedImg;
    private ImageView unCommitFoldedImg;
    private ImageView unCommitUnFoldedImg;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private String homeworkId;
    //作业标题
    private String homeworkTitle;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_showunderwayhomeworklist);
        homeworkId = getIntent().getStringExtra("homeworkId");
        initView();
        initEvent();
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
                homeworkTitle = data.getName();
                homeworkTitleTxt.setText(data.getName());
                if (!TextUtils.isEmpty(data.getDetail())) {
                    homeworkDetailTxt.setText(data.getDetail());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowUnderwayHomeworkListActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowUnderwayHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已提交和未提交人数和列表
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
                commitHomeworkList.setAdapter(new HomeworkCommitAdapter(
                        TeaShowUnderwayHomeworkListActivity.this, commitHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        TeaShowUnderwayHomeworkListActivity.this, uncommitHomeworkAnswer));
                ListViewUtil.setListViewHeightBasedOnChildren(commitHomeworkList);
                ListViewUtil.setListViewHeightBasedOnChildren(uncommitHomeworkList);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //开始评价点击事件
    public void toEvaluate(View view) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int uncommitStuNum = Integer.parseInt(uncommitStuNumTxt.getText().toString().substring(0, 1));
        if (uncommitStuNum > 0) {
            alert = builder.setTitle("开始评价")
                    .setMessage(uncommitStuNum + "人未提交作业，开始评价后将无法再提交作业。")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeHomeworkStatus(homeworkId, "进行中");
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
        } else {
            alert = builder.setTitle("开始评价")
                    .setMessage("开始评价后将无法再提交作业。")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            changeHomeworkStatus(homeworkId, "进行中");
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create();
        }
        alert.show();
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

    //改变作业状态
    private void changeHomeworkStatus(final String homeworkId, final String homeworkStatus) {
        homeworkAppAction.changeHomeworkStatus(homeworkId, homeworkStatus, homeworkTitle, this, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                TeaHomeworkUnderwayFragment.refreshFlag = true;
                Intent intent = new Intent(TeaShowUnderwayHomeworkListActivity.this, TeaShowEvaluatedHomeworkListActivity.class);
                intent.putExtra("homeworkId", homeworkId);
                startActivity(intent);
                Toast.makeText(TeaShowUnderwayHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowUnderwayHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == homeworkShowPhotoList) {
            ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter, position);
        } else {
            //学生提交的作业点击事件
            String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
            Intent intent = new Intent(this, TeaShowUnderwayHomeworkActivity.class);
            intent.putExtra("homeworkAnswerId", homeworkAnswerId);
            startActivity(intent);
        }
    }
}
