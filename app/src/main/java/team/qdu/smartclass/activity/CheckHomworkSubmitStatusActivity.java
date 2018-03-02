package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/1.
 */

public class CheckHomworkSubmitStatusActivity extends SBaseActivity {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private ImageView homeworkPhotoImg;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_checkhomeworksubmitstatus);
        initView();
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_title);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkPhotoImg = (ImageView) findViewById(R.id.img_homework_photo);
        setData();
    }

    //给页面组件设置数据
    private void setData() {

    }
}
