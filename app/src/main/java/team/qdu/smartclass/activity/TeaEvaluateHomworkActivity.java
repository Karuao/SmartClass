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
import team.qdu.smartclass.util.FileUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * Created by 11602 on 2018/3/3.
 */

public class TeaEvaluateHomworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView answerDetailTxt;
    private HorizontalListView homeworkShowPhotoList;
    private HorizontalListView homeworkAddPhotoList;
    private HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    private HomeworkAddPhotoAdapter homeworkAddPhotoAdapter;
    private RelativeLayout answerPhotoRlayout;
    private EditText answerExpEdt;
    private EditText evaluateRemarkEdt;
    private String homeworkAnswerId;
    //要删除的图片Url，如果重新上传图片则在服务器上删除之前的图片
    String delPhotoesUrl;
    //存放压缩后的上传图片
    List<File> photoList;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_evaluatehomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        answerExpEdt = (EditText) findViewById(R.id.edt_answer_exp);
        evaluateRemarkEdt = (EditText) findViewById(R.id.edt_evaluate_remark);
        homeworkAddPhotoList = (HorizontalListView) findViewById(R.id.list_homework_addphoto);
        answerExpEdt.requestFocus();
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

    //组件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                if (!TextUtils.isEmpty(data.getDetail())) {
                    answerDetailTxt.setText(data.getDetail());
                }
                if (!TextUtils.isEmpty(data.getUrl())) {
                    ImgUtil.initHomeworkPhotoList(TeaEvaluateHomworkActivity.this, homeworkShowPhotoAdapter, data.getUrl(), data.getUrl_file_num());
                } else {
                    answerPhotoRlayout.setVisibility(View.GONE);
                }
                if (data.getExp() != null) {
                    answerExpEdt.setText(data.getExp().toString());
                }
                if (!TextUtils.isEmpty(data.getRemark())) {
                    evaluateRemarkEdt.setText(data.getRemark());
                }
                if (!TextUtils.isEmpty(data.getRemark_url())) {
                    ImgUtil.initHomeworkPhotoList(TeaEvaluateHomworkActivity.this, homeworkAddPhotoAdapter, data.getRemark_url(), data.getRemark_url_file_num());
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaEvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //经验LinerLayout点击事件,聚焦经验EditText
    public void toFocusExp(View view) {
        answerExpEdt.requestFocus();
    }

    //提交评价点击事件
    public void toSubmitEvaluation(View view) throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        String answerExp = answerExpEdt.getText().toString();
        String evaluateRemark = evaluateRemarkEdt.getText().toString();
        photoList = new ArrayList<>();
        ImgUtil.compressPhotoes(photoList, homeworkAddPhotoAdapter, this);
        homeworkAppAction.commitHomeworkEvaluation(homeworkAnswerId, answerExp, evaluateRemark, photoList, delPhotoesUrl, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(TeaEvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
                TeaShowEvaluatedHomeworkListActivity.refreshFlag = true;
                finish();
                FileUtil.deleteCompressFiles(photoList);
                FileUtil.deleteCacheFiles(delPhotoesUrl);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaEvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
                FileUtil.deleteCompressFiles(photoList);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImgUtil.setHomeworkAddList(homeworkAddPhotoAdapter, requestCode, resultCode, data);
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
}
