package team.qdu.smartclass.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/4.
 */

public class ShowFinishHomeworkDetialActivity extends SBaseActivity {

    private TextView homeworkTitleTxt;
    private TextView answerDetailTxt;
    private ImageView answerPhotoImg;
    private RelativeLayout answerPhotoRlayout;
    private TextView answerExpTxt;
    private TextView evaluateRemarkTxt;
    private RelativeLayout evaluateRemarkRlayout;
    private ImageView evaluatePhotoImg;
    private RelativeLayout evaluatePhotoRlayout;
    private String homeworkAnswerId;
    private Bitmap homeworkAnswerPhoto;
    private Bitmap evaluatePhoto;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_finish_details);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        answerPhotoImg = (ImageView) findViewById(R.id.img_answer_photo);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        answerExpTxt = (TextView) findViewById(R.id.txt_answer_exp);
        evaluateRemarkTxt = (TextView) findViewById(R.id.txt_evaluate_remark);
        evaluateRemarkRlayout = (RelativeLayout) findViewById(R.id.rlayout_evaluate_remark);
        evaluatePhotoImg = (ImageView) findViewById(R.id.img_evaluate_photo);
        evaluatePhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_evaluate_photo);
        setData();
    }

    //组件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                if (data.getDetail() != null) {
                    answerDetailTxt.setText(data.getDetail());
                }
                if (data.getUrl() != null) {
                    setPhoto(answerPhotoImg, data.getUrl(), true);
                } else {
                    answerPhotoRlayout.setVisibility(View.GONE);
                }
                if (data.getExp() != null) {
                    answerExpTxt.setText(data.getExp().toString());
                }
                if (data.getRemark() != null) {
                    evaluateRemarkTxt.setText(data.getRemark());
                } else {
                    evaluateRemarkRlayout.setVisibility(View.GONE);
                }
                if (data.getRemark_url() != null) {
                    setPhoto(evaluatePhotoImg, data.getRemark_url(), false);
                } else {
                    evaluatePhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowFinishHomeworkDetialActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url, final boolean ifAnswerPhoto) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                if (ifAnswerPhoto) {
                    homeworkAnswerPhoto = data;
                } else {
                    evaluatePhoto = data;
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowFinishHomeworkDetialActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowAnswerPhoto(View view) {
        Intent intent = new Intent(ShowFinishHomeworkDetialActivity.this, ShowPhotoActivity.class);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "showPhoto.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            homeworkAnswerPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    //点击评价图片展示图片点击事件
    public void toShowEvaluatePhoto(View view) {
        Intent intent = new Intent(ShowFinishHomeworkDetialActivity.this, ShowPhotoActivity.class);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "showPhoto.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            evaluatePhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    public void toBack(View view) {
        finish();
    }
}
