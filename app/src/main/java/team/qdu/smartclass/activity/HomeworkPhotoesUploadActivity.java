package team.qdu.smartclass.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkAddPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.view.HorizontalListView;

public class HomeworkPhotoesUploadActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    protected HorizontalListView homeworkShowPhotoList;
    protected HorizontalListView homeworkAddPhotoList;
    protected HomeworkShowPhotoAdapter homeworkShowPhotoAdapter;
    protected HomeworkAddPhotoAdapter homeworkAddPhotoAdapter;
    //要删除的图片Url，如果重新上传图片则在服务器上删除之前的图片
    protected String delPhotoesUrl;
    //是否修改了要上传的图片
    public boolean ifChangePhotoes;
    //是否触发Item事件,因为使用的HorizantalListView不能屏蔽父控件事件，用这个变量控制是否触发事件
    public boolean ifAllowItemEvent = true;

    protected void initView() {
        homeworkShowPhotoList = (HorizontalListView) findViewById(R.id.list_homework_showphoto);
        homeworkAddPhotoList = (HorizontalListView) findViewById(R.id.list_homework_addphoto);
    }

    protected void initEvent() {
        homeworkShowPhotoAdapter = new HomeworkShowPhotoAdapter(this);
        homeworkShowPhotoList.setAdapter(homeworkShowPhotoAdapter);
        homeworkShowPhotoList.setOnItemClickListener(this);
        homeworkAddPhotoAdapter = new HomeworkAddPhotoAdapter(this, ImgUtil.maxImgCount);
        homeworkAddPhotoList.setAdapter(homeworkAddPhotoAdapter);
        homeworkAddPhotoList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!ifAllowItemEvent) {
            ifAllowItemEvent = true;
            return;
        }
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
        ifChangePhotoes = ImgUtil.setHomeworkAddList(homeworkAddPhotoAdapter, requestCode, resultCode, data);
    }
}
