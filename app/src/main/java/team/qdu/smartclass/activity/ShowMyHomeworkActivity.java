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
 * 展示自己提交的作业，不可修改
 * Created by 11602 on 2018/2/26.
 */

public class ShowMyHomeworkActivity extends SBaseActivity {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private ImageView homeworkPhotoImg;
    private RelativeLayout homeworkPhotoRlayout;
    private TextView answerDetailTxt;
    private RelativeLayout answerDetailRlayout;
    private ImageView answerPhotoImg;
    private RelativeLayout answerPhotoRlayout;
    private String homeworkAnswerId;
    private Bitmap homeworkPhoto;
    private Bitmap homeworkAnswerPhoto;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_stu_homework);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkPhotoImg = (ImageView) findViewById(R.id.img_homework_photo);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        answerDetailRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_detail);
        answerPhotoImg = (ImageView) findViewById(R.id.img_answer_photo);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        setData();
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                if (data.getHomework().getDetail() != null) {
                    homeworkDetailTxt.setText(data.getHomework().getDetail());
                }
                if (data.getHomework().getUrl() != null) {
                    setPhoto(homeworkPhotoImg, data.getHomework().getUrl(), false);
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
                if (data.getDetail() != null) {
                    answerDetailTxt.setText(data.getDetail());
                } else {
                    answerDetailRlayout.setVisibility(View.GONE);
                }
                if (data.getUrl() != null) {
                    setPhoto(answerPhotoImg, data.getUrl(), true);
                } else {
                    answerPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowMyHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
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
                    homeworkPhoto = data;
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowMyHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(ShowMyHomeworkActivity.this, ShowPhotoActivity.class);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "showPhoto.png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            homeworkPhoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    //点击图片展示图片点击事件
    public void toShowAnswerPhoto(View view) {
        Intent intent = new Intent(ShowMyHomeworkActivity.this, ShowPhotoActivity.class);
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

    public void toBack(View view) {
        finish();
    }
}
