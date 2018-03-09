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
 * Created by 11602 on 2018/3/3.
 */

public class EvaluateHomworkActivity extends SBaseActivity {

    private TextView homeworkTitleTxt;
    private TextView answerDetailTxt;
    private ImageView answerPhotoImg;
    private RelativeLayout answerPhotoRlayout;
    private EditText answerExpEdt;
    private EditText evaluateRemarkEdt;
    private ImageView evaluatePhotoImg;
    private String homeworkAnswerId;
    private Bitmap homeworkAnswerPhoto;
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

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_homework_admin_evaluate_details);
        homeworkAnswerId = getIntent().getStringExtra("homeworkAnswerId");
        initView();
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
    }

    private void initView() {
        homeworkTitleTxt = (TextView) findViewById(R.id.txt_homework_name);
        answerDetailTxt = (TextView) findViewById(R.id.txt_answer_detail);
        answerPhotoImg = (ImageView) findViewById(R.id.img_answer_photo);
        answerPhotoRlayout = (RelativeLayout) findViewById(R.id.rlayout_answer_photo);
        answerExpEdt = (EditText) findViewById(R.id.edt_answer_exp);
        evaluateRemarkEdt = (EditText) findViewById(R.id.edt_evaluate_remark);
        evaluatePhotoImg = (ImageView) findViewById(R.id.img_evaluate_photo);
        answerExpEdt.requestFocus();
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
                    answerExpEdt.setText(data.getExp().toString());
                }
                if (data.getRemark() != null) {
                    evaluateRemarkEdt.setText(data.getRemark());
                }
                if (data.getRemark_url() != null) {
                    setPhoto(evaluatePhotoImg, data.getRemark_url(), false);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(EvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //经验LinerLayout点击事件,聚焦经验EditText
    public void toFocusExp(View view) {
        answerExpEdt.requestFocus();
    }

    //ImageView设置图片
    private void setPhoto(final ImageView imageView, String url, final boolean ifSetPhoto) {
        classAppAction.getBitmap(url, new ActionCallbackListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap data, String message) {
                imageView.setImageBitmap(data);
                if (ifSetPhoto) {
                    homeworkAnswerPhoto = data;
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(EvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击图片展示图片点击事件
    public void toShowPhoto(View view) {
        Intent intent = new Intent(EvaluateHomworkActivity.this, ShowPhotoActivity.class);
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

    //提交评价点击事件
    public void toSubmitEvaluation(View view) throws URISyntaxException {
        String  answerExp = answerExpEdt.getText().toString();
        String evaluateRemark = evaluateRemarkEdt.getText().toString();
        File evaluatePhoto = null;
        if (ifUploadPhoto) {
            evaluatePhoto = new File(new URI(photoUri.toString()));
        }
        homeworkAppAction.commitHomeworkEvaluation(homeworkAnswerId, answerExp, evaluateRemark, evaluatePhoto, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data, String message) {
                Toast.makeText(EvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
                ShowEvaluateHomeworkActivity.refreshFlag = true;
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(EvaluateHomworkActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加图片点击事件
    public void toAddPhoto(View view) {
        if (selectphotoPopup == null) {
            View contentView = LayoutInflater.from(EvaluateHomworkActivity.this)
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
                } else {
                    Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto(null);
                } else {
                    Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
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
                        EvaluateHomworkActivity.this.getContentResolver(), photoUri);
                evaluatePhotoImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //图片按照ImageView大小缩放
            evaluatePhotoImg.setScaleType(ImageView.ScaleType.FIT_XY);
            //解决部分自动旋转问题
            evaluatePhotoImg.setRotation(ImgUtil.getBitmapDegree(photoUri.getPath()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toBack(View view) {
        finish();
    }
}
