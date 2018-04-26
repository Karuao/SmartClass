package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.bean.ImageItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkAddPhotoAdapter;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;
import team.qdu.smartclass.view.CustomDatePicker;
import team.qdu.smartclass.view.HorizontalListView;

/**
 * 老师发布作业
 * Created by 11602 on 2018/2/6.
 */

public class TeaPublishHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private EditText homeworkTitleEdt;
    private TextView homeworkDeadlineTxt;
    private EditText homeworkDetailEdt;
    private HorizontalListView homeworkAddPhotoList;
    private CustomDatePicker customDatePicker;

    private HomeworkAddPhotoAdapter homeworkAddPhotoAdapter;
    //是否触发Item事件,因为使用的HorizantalListView不能屏蔽父控件事件，用这个变量控制是否触发事件
    public boolean ifAllowItemEvent = true;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_publishhomework);
        initView();
        initEvent();
        initDatePicker();
//        photoList = new ArrayList<>();
    }

    private void initView() {
        homeworkTitleEdt = (EditText) findViewById(R.id.edt_homework_title);
        homeworkDeadlineTxt = (TextView) findViewById(R.id.txt_homework_deadline);
        homeworkDetailEdt = (EditText) findViewById(R.id.edt_homework_detail);
        homeworkAddPhotoList = (HorizontalListView) findViewById(R.id.list_homework_addphoto);
        initImagePicker();
    }

    private void initEvent() {
        homeworkAddPhotoAdapter = new HomeworkAddPhotoAdapter(this, ImgUtil.maxImgCount);
        homeworkAddPhotoList.setAdapter(homeworkAddPhotoAdapter);
        homeworkAddPhotoList.setOnItemClickListener(this);
    }

    //初始化ImagePicker图片选择库
    private void initImagePicker() {
        imagePicker.setSelectLimit(ImgUtil.maxImgCount);              //选中数量限制
    }

    //发布作业点击事件
    public void toPublish(View view) {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        String title = homeworkTitleEdt.getText().toString();
        String deadline = homeworkDeadlineTxt.getText().toString();
        String detail = homeworkDetailEdt.getText().toString();
        List<String> imgPathList = new ArrayList<>();
        for (ImageItem imageItem : homeworkAddPhotoAdapter.getImages()) {
            imgPathList.add(imageItem.path);
        }
//        ImgUtil.compressPhotoes(photoList, homeworkAddPhotoAdapter, this);
        homeworkAppAction.publishHomework(title, deadline, detail, imgPathList, getClassId(), this,
                new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(TeaPublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        TeaHomeworkUnderwayFragment.refreshFlag = true;
//                        FileUtil.deleteCompressFiles(photoList);
                        LoadingDialogUtil.closeDialog();
                        finish();
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(TeaPublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
//                        FileUtil.deleteCompressFiles(photoList);
                        LoadingDialogUtil.closeDialog();
                    }
                });
    }


    //截止日期点击事件
    public void toPickDate(View view) {
        customDatePicker.show(homeworkDeadlineTxt.getText().toString());
    }

    //初始化DatePicker截止时间时间选择器
    private void initDatePicker() {
        Date current = new Date();
        Date afterOneMinute = new Date(current.getTime() + 60 * 1000);
        Date afterOneDay = new Date(current.getTime() + 24 * 60 * 60 * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.YEAR, 1);//日期加一年
        Date afterOneYear = calendar.getTime();

        //设置作业截止日期默认时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        homeworkDeadlineTxt.setText(sdf.format(afterOneDay));

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                homeworkDeadlineTxt.setText(time);
            }
        }, sdf.format(afterOneMinute), sdf.format(afterOneYear)); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!ifAllowItemEvent) {
            ifAllowItemEvent = true;
            return;
        }
        ImgUtil.responseClickHomeworkAddPhotoListItem(this, homeworkAddPhotoAdapter, parent, position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImgUtil.setHomeworkAddList(homeworkAddPhotoAdapter, requestCode, resultCode, data);
    }
}
