package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkAddPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.fragment.StuHomeworkUnderwayFragment;
import team.qdu.smartclass.util.FileUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 学生提交作业
 * Created by 11602 on 2018/2/26.
 */

public class StuCommitHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener{

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private RelativeLayout homeworkPhotoRlayout;
    private EditText answerDetailEdt;
    private HorizontalListView homeworkShowPhotoList;
    private HorizontalListView homeworkAddPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private HomeworkAddPhotoAdapter homeworkAddPhotoAdapter;
    private String homeworkAnswerId;
    //作业id
    private String homeworkId;
    //作业标题
    private String homeworkTitle;
    //是否提交过
    private String ifSubmit;
    //要删除的图片Url，如果重新上传图片则在服务器上删除之前的图片
    String delPhotoesUrl;
    //存放压缩后的上传图片
    List<File> photoList;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_stu_commithomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        answerDetailEdt = (EditText) findViewById(R.id.edt_answer_detail);
        homeworkAddPhotoList = (HorizontalListView) findViewById(R.id.list_homework_addphoto);
        setData();
    }

    private void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
        homeworkAddPhotoAdapter = new HomeworkAddPhotoAdapter(this, ImgUtil.maxImgCount);
        homeworkAddPhotoList.setAdapter(homeworkAddPhotoAdapter);
        homeworkAddPhotoList.setOnItemClickListener(this);
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
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
                    delPhotoesUrl = data.getUrl();
                }
                homeworkId = data.getHomework_id().toString();
                ifSubmit = data.getIf_submit();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //提交作业点击事件
    public void toSubmitHomework(View view) throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        String answerDetail = answerDetailEdt.getText().toString();
        photoList = new ArrayList<>();
        ImgUtil.compressPhotoes(photoList, homeworkAddPhotoAdapter, this);
        homeworkAppAction.commitHomework(homeworkAnswerId, homeworkId, getClassId(), getUserId(),
                ifSubmit, homeworkTitle, answerDetail, photoList, delPhotoesUrl, new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        StuHomeworkUnderwayFragment.refreshFlag = true;
                        finish();
                        FileUtil.deleteCompressFiles(photoList);
                        FileUtil.deleteCacheFiles(delPhotoesUrl);
                        LoadingDialogUtil.closeDialog();//关闭加载中动画
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(StuCommitHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        FileUtil.deleteCompressFiles(photoList);
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
            //点击下面添加作业图片的ListView中的图片执行的操作
            ImgUtil.responseClickHomeworkAddPhotoListItem(this, homeworkAddPhotoAdapter, parent, position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImgUtil.setHomeworkAddList(homeworkAddPhotoAdapter, requestCode, resultCode, data);
    }
}
