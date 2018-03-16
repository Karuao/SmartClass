package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageViewActivity;

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
import team.qdu.smartclass.util.ButtonUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * Created by 11602 on 2018/3/1.
 */

public class ShowUnderwayHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView commitStuNumTxt;
    private TextView uncommitStuNumTxt;
    private HorizontalListView homeworkShowPhotoList;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView commitHomeworkList;
    private ListView uncommitHomeworkList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private String homeworkId;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_checkhomeworkcommitstatus);
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
        homeworkAppAction.getHomeworkDetail(homeworkId, new ActionCallbackListener<HomeworkWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getName());
                if (data.getDetail() != null) {
                    homeworkDetailTxt.setText(data.getDetail());
                }
                if (data.getUrl() != null) {
                    ImgUtil.initHomeworkPhotoAdapter(ShowUnderwayHomeworkActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowUnderwayHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已提交和未提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
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
                        ShowUnderwayHomeworkActivity.this, commitHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        ShowUnderwayHomeworkActivity.this, uncommitHomeworkAnswer));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //开始评价点击事件
    public void toEvaluate(View view) {
        if (!ButtonUtil.isFastDoubleClick(view.getId())) {
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
    }

    //改变作业状态
    private void changeHomeworkStatus(final String homeworkId, final String homeworkStatus) {
        homeworkAppAction.changeHomeworkStatus(homeworkId, homeworkStatus, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                TeaHomeworkUnderwayFragment.refreshFlag = true;
                Intent intent = new Intent(ShowUnderwayHomeworkActivity.this, ShowEvaluateHomeworkActivity.class);
                intent.putExtra("homeworkId", homeworkId);
                startActivity(intent);
                Toast.makeText(ShowUnderwayHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowUnderwayHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == homeworkShowPhotoList) {
            Intent intentView = new Intent(this, ImageViewActivity.class);
            intentView.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) homeworkShowPhotoAdapter.getImages());
            intentView.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intentView.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
           startActivity(intentView);
        } else {
            //学生提交的作业点击事件
            String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
            Intent intent = new Intent(this, ShowUnderwayHomeworkDetailActivity.class);
            intent.putExtra("homeworkAnswerId", homeworkAnswerId);
            startActivity(intent);
        }
    }
}
