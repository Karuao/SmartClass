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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.smartclass.R;
import team.qdu.smartclass.util.ImgUtil;

/**
 * Created by 11602 on 2018/2/26.
 */

public class DoHomeworkActivity extends SBaseActivity {

    private TextView homeworkTitleTxt;
    private TextView homeworkDetailTxt;
    private ImageView homeworkPhotoImg;
    private RelativeLayout homeworkPhotoRlayout;
    private EditText answerDetailEdt;
    private ImageView answerPhotoImg;
    private String homeworkAnswerId;
    private Bitmap homeworkPhoto;
    //弹出窗口
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
        setContentView(R.layout.activity_stu_dowork);
        initView();
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        setData();
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_title);
        homeworkDetailTxt = (TextView) findViewById(R.id.txt_homework_detail);
        homeworkPhotoImg = (ImageView) findViewById(R.id.img_homework_photo);
        homeworkPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_homework_photo);
        answerDetailEdt = (EditText) findViewById(R.id.edt_answer_detail);
        answerPhotoImg = (ImageView) findViewById(R.id.img_answer_photo);
    }

    //给控件设置数据
    private void setData() {
        homeworkAppAction.getStuHomeworkDetail(homeworkAnswerId, new ActionCallbackListener<HomeworkAnswerWithBLOBs>() {
            @Override
            public void onSuccess(HomeworkAnswerWithBLOBs data, String message) {
                homeworkTitleTxt.setText(data.getHomework().getName());
                homeworkDetailTxt.setText(data.getHomework().getDetail());
                answerDetailEdt.setText(data.getDetail());
                if (data.getHomework().getUrl() != null) {
                    setPhoto(homeworkPhotoImg, data.getHomework().getUrl(), true);
                } else {
                    homeworkPhotoRlayout.setVisibility(View.GONE);
                }
                if (data.getUrl() != null) {
                    setPhoto(answerPhotoImg, data.getUrl(), false);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(DoHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url, final boolean ifSetPhoto) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                if (ifSetPhoto) {
                    homeworkPhoto = data;
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(DoHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(DoHomeworkActivity.this, ShowPhotoActivity.class);
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

    //提交作业点击事件
    public void toSubmitHomework(View view) throws URISyntaxException {
        String answerDetail = answerDetailEdt.getText().toString();
        File answerPhoto = null;
        if (ifUploadPhoto) {
            answerPhoto = new File(new URI(photoUri.toString()));
        }
        homeworkAppAction.submitHomework(homeworkAnswerId, answerDetail, answerPhoto, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(DoHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(DoHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加图片点击事件
    public void toAddPhoto(View view) {
        if (selectphotoPopup == null) {
            View contentView = LayoutInflater.from(DoHomeworkActivity.this)
                    .inflate(R.layout.popup_selectphoto, null);
            selectphotoPopup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            selectphotoPopup.setOutsideTouchable(true);
            selectphotoPopup.setBackgroundDrawable(new BitmapDrawable());
        }
        selectphotoPopup.showAtLocation(getWindow().getDecorView(),
                Gravity.CENTER | Gravity.BOTTOM, 0, 0);
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
                        DoHomeworkActivity.this.getContentResolver(), photoUri);
                answerPhotoImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //图片按照ImageView大小缩放
            answerPhotoImg.setScaleType(ImageView.ScaleType.FIT_XY);
            //解决部分自动旋转问题
            answerPhotoImg.setRotation(ImgUtil.getBitmapDegree(photoUri.getPath()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toBack(View view) {
        finish();
    }
}