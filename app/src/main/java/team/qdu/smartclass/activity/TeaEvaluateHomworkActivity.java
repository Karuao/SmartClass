package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import team.qdu.smartclass.util.FileUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by 11602 on 2018/3/3.
 */

public class TeaEvaluateHomworkActivity extends HomeworkPhotoesUploadActivity {

    private TextView homeworkTitleTxt;
    private TextView answerDetailTxt;
    private RelativeLayout answerPhotoRlayout;
    private EditText answerExpEdt;
    private EditText evaluateRemarkEdt;
    private Button homeworkEvaluateBtn;
    private String homeworkAnswerId;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_evaluatehomework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        answerExpEdt = (EditText) findViewById(R.id.edt_answer_exp);
        evaluateRemarkEdt = (EditText) findViewById(R.id.edt_evaluate_remark);
        homeworkEvaluateBtn = (Button) findViewById(R.id.btn_homework_evaluate);
        answerExpEdt.requestFocus();
        setData();
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
                    delPhotoesUrl = context.getExternalCacheDir() + File.separator + data.getRemark_url();
                }
                //加载完数据取消评价作业按钮的禁用
                homeworkEvaluateBtn.setEnabled(true);
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
    public void toSubmitEvaluation(View view) {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        String answerExp = answerExpEdt.getText().toString();
        String evaluateRemark = evaluateRemarkEdt.getText().toString();
//        photoList = new ArrayList<>();
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
//        ImgUtil.compressPhotoes(photoList, homeworkAddPhotoAdapter, this);
        homeworkAppAction.commitHomeworkEvaluation(homeworkAnswerId, answerExp, evaluateRemark,
                imgPathList, delPhotoesUrl, ifChangePhotoes ? "是" : "否", this, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(TeaEvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
                TeaShowEvaluatedHomeworkListActivity.refreshFlag = true;
                finish();
//                FileUtil.deleteCompressFiles(photoList);
                FileUtil.deleteCacheFiles(delPhotoesUrl);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(TeaEvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
//                FileUtil.deleteCompressFiles(photoList);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
            }
        });

    }
}
