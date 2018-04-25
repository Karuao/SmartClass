package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.StuHomeworkUnderwayFragment;
import team.qdu.smartclass.util.FileUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * 学生提交作业
 * Created by 11602 on 2018/2/26.
 */

public class StuCommitHomeworkActivity extends HomeworkPhotoesUploadActivity implements AdapterView.OnItemClickListener{

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private RelativeLayout homeworkPhotoRlayout;
    private EditText answerDetailEdt;
    private Button homeworkCommitBtn;
    private String homeworkAnswerId;
    //作业id
    private String homeworkId;
    //作业标题
    private String homeworkTitle;
    //是否提交过
    private String ifSubmit;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_stu_commithomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        answerDetailEdt = (EditText) findViewById(R.id.edt_answer_detail);
        homeworkCommitBtn = (Button) findViewById(R.id.btn_homework_commit);
        setData();
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, this, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitle = data.getHomework().getName();
                homeworkTitleTxt.setText(homeworkTitle);
                if (!TextUtils.isEmpty(data.getHomework().getDetail())) {
                    homeworkDetailTxt.setText(data.getHomework().getDetail());
                }
                if (!TextUtils.isEmpty(data.getHomework().getUrl())) {
                    ImgUtil.initHomeworkPhotoList(StuCommitHomeworkActivity.this, homeworkShowPhotoAdapter, data.getHomework().getUrl(), data.getHomework().getUrl_file_num());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(data.getDetail())) {
                    answerDetailEdt.setText(data.getDetail());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(StuCommitHomeworkActivity.this, homeworkAddPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                    delPhotoesUrl = context.getExternalCacheDir() + File.separator + data.getUrl();
                }
                homeworkId = data.getHomework_id().toString();
                ifSubmit = data.getIf_submit();
                //加载完数据取消提交作业按钮的禁用
                homeworkCommitBtn.setEnabled(true);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //提交作业点击事件
    public void toSubmitHomework(View view) {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        String answerDetail = answerDetailEdt.getText().toString();
        List<String> imgPathList = new ArrayList<>();
        if (ifChangePhotoes) {
            //若修改了图片重传图片
            for (ImageItem imageItem : homeworkAddPhotoAdapter.getImages()) {
                imgPathList.add(imageItem.path);
            }
        } else {
            //为修改图片把要删除的图片Url置空
            delPhotoesUrl = null;
        }
        homeworkAppAction.commitHomework(homeworkAnswerId, homeworkId, getClassId(), getUserId(),
                ifSubmit, homeworkTitle, answerDetail, imgPathList, delPhotoesUrl, ifChangePhotoes ? "是" : "否", this, new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        StuHomeworkUnderwayFragment.refreshFlag = true;
                        finish();
//                        FileUtil.deleteCompressFiles(photoList);
                        FileUtil.deleteCacheFiles(delPhotoesUrl);
                        LoadingDialogUtil.closeDialog();//关闭加载中动画
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
//                        FileUtil.deleteCompressFiles(photoList);
                        LoadingDialogUtil.closeDialog();//关闭加载中动画
                    }
                });
    }
}
