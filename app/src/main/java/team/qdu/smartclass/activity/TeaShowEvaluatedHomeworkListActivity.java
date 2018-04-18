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
import team.qdu.smartclass.adapter.HomeworkEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkNotEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkUncommitAdapter;
import team.qdu.smartclass.fragment.TeaHomeworkFinishFragment;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.ListViewUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 老师显示进入的评价中作业的学生作业列表
 * Created by 11602 on 2018/3/3.
 */

public class TeaShowEvaluatedHomeworkListActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView evaluateStuNumTxt;
    private TextView notEvaluateStuNumTxt;
    private TextView uncommitStuNumTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView evaluateHomeworkList;
    private ListView notEvaluateHomeworkList;
    private ListView uncommitHomeworkList;
    private ImageView evaluateFoldedImg;
    private ImageView evaluateUnFoldedImg;
    private ImageView notEvaluateFoldedImg;
    private ImageView notEvaluateUnFoldedImg;
    private ImageView unCommitFoldedImg;
    private ImageView unCommitUnFoldedImg;
    private String homeworkId;
    //作业标题
    private String homeworkTitle;
    //刷新标志
    public static boolean refreshFlag;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_showevaluatehomeworklist);
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
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        evaluateHomeworkList = (ListView) findViewById(R.id.list_evaluatehomework);
        notEvaluateHomeworkList = (ListView) findViewById(R.id.list_notevaluatehomework);
        uncommitHomeworkList = (ListView) findViewById(R.id.list_uncommithomework);
        evaluateFoldedImg = (ImageView) findViewById(R.id.img_evaluate_folded);
        evaluateUnFoldedImg = (ImageView) findViewById(R.id.img_evaluate_unfolded);
        notEvaluateFoldedImg = (ImageView) findViewById(R.id.img_notevaluate_folded);
        notEvaluateUnFoldedImg = (ImageView) findViewById(R.id.img_notevaluate_unfolded);
        unCommitFoldedImg = (ImageView) findViewById(R.id.img_uncommit_folded);
        unCommitUnFoldedImg = (ImageView) findViewById(R.id.img_uncommit_unfolded);
        setData();
    }

    private void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
        evaluateHomeworkList.setOnItemClickListener(this);
        notEvaluateHomeworkList.setOnItemClickListener(this);
    }

    //给页面组件设置数据
    private void setData() {
        //设置作业内容和图片
        homeworkAppAction.getHomeworkDetail(homeworkId, this, new ActionCallbackListener<HomeworkWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkWithBLOBs data, String message) {
                homeworkTitle = data.getName();
                homeworkTitleTxt.setText(homeworkTitle);
                homeworkDetailTxt.setText(data.getDetail());
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaShowEvaluatedHomeworkListActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowEvaluatedHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已评价、为评价和未提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, this, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
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
                        TeaShowEvaluatedHomeworkListActivity.this, evaluateHomeworkAnswer));
                notEvaluateHomeworkList.setAdapter(new HomeworkNotEvaluateAdapter(
                        TeaShowEvaluatedHomeworkListActivity.this, notEvaluateHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        TeaShowEvaluatedHomeworkListActivity.this, uncommitHomeworkAnswer));
                ListViewUtil.setListViewHeightBasedOnChildren(evaluateHomeworkList);
                ListViewUtil.setListViewHeightBasedOnChildren(notEvaluateHomeworkList);
                ListViewUtil.setListViewHeightBasedOnChildren(uncommitHomeworkList);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //结束作业点击事件
    public void toFinishHomework(View view) {
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
                            LoadingDialogUtil.createLoadingDialog(TeaShowEvaluatedHomeworkListActivity.this, "加载中...");//加载中动画，用来防止用户重复点击
                            homeworkAppAction.getNotEvaluateStuNum(homeworkId, TeaShowEvaluatedHomeworkListActivity.this, new ActionCallbackListener<Integer>() {
                                @Override
                                public void onSuccess(Integer data, String message) {
                                    changeHomeworkStatus(homeworkId, "评价中");
                                }

                                @Override
                                public void onFailure(String errorEvent, String message) {
                                    Toast.makeText(TeaShowEvaluatedHomeworkListActivity.this,
                                            "结束班课失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                    LoadingDialogUtil.closeDialog();//关闭加载中动画
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
            Toast.makeText(TeaShowEvaluatedHomeworkListActivity.this, "仍有" + notEvaluateStuNum
                    + "作业未评价,请评价后再结束作业", Toast.LENGTH_SHORT).show();
        }
    }

    //折叠/展开学生ListView点击事件
    public void foldListView(View view) {
        switch (view.getId()) {
            case R.id.rlayout_evaluate:
                if (evaluateHomeworkList.getVisibility() == View.VISIBLE) {
                    evaluateHomeworkList.setVisibility(View.GONE);
                    evaluateFoldedImg.setVisibility(View.VISIBLE);
                    evaluateUnFoldedImg.setVisibility(View.INVISIBLE);
                } else {
                    evaluateHomeworkList.setVisibility(View.VISIBLE);
                    evaluateFoldedImg.setVisibility(View.INVISIBLE);
                    evaluateUnFoldedImg.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rlayout_notevaluate:
                if (notEvaluateHomeworkList.getVisibility() == View.VISIBLE) {
                    notEvaluateHomeworkList.setVisibility(View.GONE);
                    notEvaluateFoldedImg.setVisibility(View.VISIBLE);
                    notEvaluateUnFoldedImg.setVisibility(View.INVISIBLE);
                } else {
                    notEvaluateHomeworkList.setVisibility(View.VISIBLE);
                    notEvaluateFoldedImg.setVisibility(View.INVISIBLE);
                    notEvaluateUnFoldedImg.setVisibility(View.VISIBLE);
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
    private void changeHomeworkStatus(String homeworkId, final String homeworkStatus) {
        homeworkAppAction.changeHomeworkStatus(homeworkId, homeworkStatus, homeworkTitle, this, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                TeaHomeworkUnderwayFragment.refreshFlag = true;
                TeaHomeworkFinishFragment.refreshFlag = true;
                Toast.makeText(TeaShowEvaluatedHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaShowEvaluatedHomeworkListActivity.this, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == homeworkShowPhotoList) {
            //点击上面显示作业图片的ListView中的图片执行浏览图片操作
            ImgUtil.responseClickHomeworkShowPhotoListItem(this, homeworkShowPhotoAdapter, position);
        } else {
            //点击下面成员作业情况ListView进入评价作业Activity
            String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
            Intent intent = new Intent(this, TeaEvaluateHomworkActivity.class);
            intent.putExtra("homeworkAnswerId", homeworkAnswerId);
            startActivity(intent);
        }
    }
}