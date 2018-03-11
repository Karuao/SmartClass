package team.qdu.smartclass.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkEvaluateAdapter;
import team.qdu.smartclass.adapter.HomeworkUncommitAdapter;

/**
 * Created by 11602 on 2018/3/3.
 */

public class ShowFinishHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private TextView evaluateStuNumTxt;
    private TextView uncommitStuNumTxt;
    private ImageView homeworkPhotoImg;
    private RelativeLayout homeworkPhotoRlayout;
    private ListView evaluateHomeworkList;
    private ListView uncommitHomeworkList;
    private String homeworkId;
    private Bitmap homeworkPhoto;
    //刷新标志
    public static boolean refreshFlag;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_finish);
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
        uncommitStuNumTxt = (TextView) findViewById(R.id.txt_uncommitstu_num);
        homeworkPhotoImg = (ImageView) findViewById(R.id.img_homework_photo);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        evaluateHomeworkList = (ListView) findViewById(R.id.list_evaluatehomework);
        uncommitHomeworkList = (ListView) findViewById(R.id.list_uncommithomework);
        setData();
    }

    private void initEvent() {
        evaluateHomeworkList.setOnItemClickListener(this);
    }

    //给页面组件设置数据
    private void setData() {
        //设置作业内容和图片
        homeworkAppAction.getHomeworkDetail(homeworkId, new ActionCallbackListener<HomeworkWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getName());
                homeworkDetailTxt.setText(data.getDetail());
                if (data.getUrl() != null) {
                    setPhoto(homeworkPhotoImg, data.getUrl());
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowFinishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        //设置已评价、未评价和未提交人数和列表
        homeworkAppAction.getHomeworkAnswerList(homeworkId, new ActionCallbackListener<List<HomeworkAnswerWithBLOBs>>() {
            @Override
            public void onSuccess(List<HomeworkAnswerWithBLOBs> data, String message) {
                List<HomeworkAnswerWithBLOBs> evaluateHomeworkAnswer = new ArrayList<>();
                List<HomeworkAnswerWithBLOBs> uncommitHomeworkAnswer = new ArrayList<>();
                for (HomeworkAnswerWithBLOBs homeworkAnswer : data) {
                    if ("是".equals(homeworkAnswer.getIf_submit())) {
                        evaluateHomeworkAnswer.add(homeworkAnswer);
                    } else {
                        uncommitHomeworkAnswer.add(homeworkAnswer);
                    }
                }
                evaluateStuNumTxt.setText(evaluateHomeworkAnswer.size() + "人");
                uncommitStuNumTxt.setText(uncommitHomeworkAnswer.size() + "人");
                evaluateHomeworkList.setAdapter(new HomeworkEvaluateAdapter(
                        ShowFinishHomeworkActivity.this, evaluateHomeworkAnswer));
                uncommitHomeworkList.setAdapter(new HomeworkUncommitAdapter(
                        ShowFinishHomeworkActivity.this, uncommitHomeworkAnswer));
            }

            @Override
            public void onFailure(String errorEvent, String message) {
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(ShowFinishHomeworkActivity.this, ShowPhotoActivity.class);
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

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                homeworkPhoto = data;
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(ShowFinishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_id)).getText().toString();
        Intent intent = new Intent(this, ShowFinishHomeworkDetialActivity.class);
        intent.putExtra("homeworkAnswerId", homeworkAnswerId);
        startActivity(intent);
    }
}