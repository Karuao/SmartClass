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
 * Created by 11602 on 2018/3/3.
 */

public class ShowUnderwayHomeworkDetailActivity extends SBaseActivity {

    private TextView underwayHomeworkTitleTxt;
    private TextView underwayAnswerDetailTxt;
    private ImageView underwayAnswerPhotoImg;
    private RelativeLayout underwayAnswerPhotoRlayout;
    private String homeworkAnswerId;
    private Bitmap homeworkAnswerPhoto;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_underway_details);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
    }

    private void initView() {
        underwayHomeworkTitleTxt = (TextView) findViewById(R.id.txt_underwayhomework_name);
        underwayAnswerDetailTxt = (TextView) findViewById(R.id.txt_underwayanswer_detail);
        underwayAnswerPhotoImg = (ImageView) findViewById(R.id.img_underwayanswer_photo);
        underwayAnswerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_underwayanswer_photo);
        setData();
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                underwayHomeworkTitleTxt.setText(data.getHomework().getName());
                if (data.getDetail() != null) {
                    underwayAnswerDetailTxt.setText(data.getDetail());
                }
                if (data.getUrl() != null) {
                    setPhoto(underwayAnswerPhotoImg, data.getUrl());
                } else {
                    underwayAnswerPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowUnderwayHomeworkDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                homeworkAnswerPhoto = data;
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowUnderwayHomeworkDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(ShowUnderwayHomeworkDetailActivity.this, ShowPhotoActivity.class);
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
