package team.qdu.smartclass.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ButtonUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.view.CustomDatePicker;

/**
 * Created by 11602 on 2018/2/6.
 */

public class PublishHomeworkActivity extends SBaseActivity {

    private EditText homeworkTitleEdt;
    private TextView homeworkDeadlineTxt;
    private EditText homeworkDetailEdt;
    private ImageView photoImg;
    private CustomDatePicker customDatePicker;
    private PopupWindow selectphotoPopup;
    //权限
    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    //拍照临时图片
    private String mTempPhotoPath;
    //获得的照片Uri
    Uri photoUri;
    //是否上传图片
    boolean ifUploadPhoto;
    //相册选图标记
    private static final int GALLERY_REQUEST_CODE = 0;
    //相机拍照标记
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_publishhomework);
        ifUploadPhoto = false;
        initView();
        initDatePicker();
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
    }

    private void initView() {
        homeworkTitleEdt = (EditText) findViewById(R.id.edt_homework_title);
        homeworkDeadlineTxt = (TextView) findViewById(R.id.txt_homework_deadline);
        homeworkDetailEdt = (EditText) findViewById(R.id.edt_homework_detail);
        photoImg = (ImageView) findViewById(R.id.img_homework_photo);
    }

    //发布作业点击事件
    public void toPublish(View view) throws URISyntaxException {
        if (!ButtonUtil.isFastDoubleClick(view.getId())) {
            String title = homeworkTitleEdt.getText().toString();
            String deadline = homeworkDeadlineTxt.getText().toString();
            String detail = homeworkDetailEdt.getText().toString();
            File photo = null;
            if (ifUploadPhoto) {
                photo = new File(new URI(photoUri.toString()));
            }

            homeworkAppAction.pushHomework(title, deadline, detail, photo, getClassId(),
                    new ActionCallbackListener<Void>() {
                        @Override
                        public void onSuccess(Void data, String message) {
                            Toast.makeText(PublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                            TeaHomeworkUnderwayFragment.refreshFlag = true;
                            finish();
                        }

                        @Override
                        public void onFailure(String errorEvent, String message) {
                            Toast.makeText(PublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //截止日期点击事件
    public void toPickDate(View view) {
        customDatePicker.show(homeworkDeadlineTxt.getText().toString());
    }

    //添加图片点击事件
    public void toAddPhoto(View view) {
        if (selectphotoPopup == null) {
            View contentView = LayoutInflater.from(PublishHomeworkActivity.this)
                    .inflate(R.layout.popup_selectphoto, null);
            selectphotoPopup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            selectphotoPopup.setOutsideTouchable(true);
            selectphotoPopup.setBackgroundDrawable(new BitmapDrawable());
        }
        selectphotoPopup.showAtLocation(getWindow().getDecorView(),
                Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }

    //初始化DatePicker截止时间时间选择器
    private void initDatePicker() {
        Date current = new Date();
        Date afterOneMinute = new Date(current.getTime() + 8 * 60 * 60 * 1000 + 60 * 1000);
        Date afterOneDay = new Date(current.getTime() + 8 * 60 * 60 * 1000 + 24 * 60 * 60 * 1000);
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

    //拍照点击事件
    public void takePhoto(View view) {
        selectphotoPopup.dismiss();
        /**
         * 1)使用ContextCompat.chefkSelfPermission(),因为Context.permission
         * 只在棒棒糖系统中使用
         * 2）总是检查权限（即使权限被授予）因为用户可能会在设置中移除你的权限*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTempPhotoPath)));
            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
        }
    }

    //从相册中获取点击事件
    public void pickFromGallery(View view) {
        selectphotoPopup.dismiss();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    //取消点击事件
    public void cancel(View view) {
        selectphotoPopup.dismiss();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery(null);
                }
                break;
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto(null);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //startActivityForResult之后回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CreateClassActivity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    File temp = new File(mTempPhotoPath);
                    photoUri = Uri.fromFile(temp);
                    ifUploadPhoto = true;
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    photoUri = data.getData();
                    ifUploadPhoto = true;
                    break;
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        PublishHomeworkActivity.this.getContentResolver(), photoUri);
                photoImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //解决部分自动旋转问题
            photoImg.setRotation(ImgUtil.getBitmapDegree(photoUri.getPath()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toBack(View view) {
        finish();
    }
}
