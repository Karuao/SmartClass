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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.kevin.crop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUser;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.MainClassFragment;
import team.qdu.smartclass.util.ConstantUtil;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;


/**
 * 创建班课
 * <p>
 * Created by Rock on 2017/4/23.
 */
public class CreateClassActivity extends SBaseActivity {

    private EditText classnameEdt;
    private EditText courseEdt;
    private ImageView AvatarImg;
    private File classAvatar;
    //弹出窗口
    private PopupWindow selectphotoPopup;

    //权限
    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    //拍照临时图片
    private String mTempPhotoPath;
    //剪切后图像文件
    private Uri mDestinationUri;
    //判断是否使用默认头像
    private boolean isDefaultAvatar = true;

    //相册选图标记
    private static final int GALLERY_REQUEST_CODE = 0;
    //相机拍照标记
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclass);
        mDestinationUri = Uri.fromFile(new File(CreateClassActivity.this.getCacheDir(), "cropImage.png"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
        initView();
    }

    //初始化View
    private void initView() {
        classnameEdt = (EditText) findViewById(R.id.edt_classname);
        courseEdt = (EditText) findViewById(R.id.edt_course);
        AvatarImg = (ImageView) findViewById(R.id.img_avatar);
    }

    //创建班课按钮点击事件
    public void finishCreate(View view) throws URISyntaxException {
        LoadingDialogUtil.createLoadingDialog(this, "上传中...");//加载中动画，用来防止用户重复点击
        if (!isDefaultAvatar) {
            classAvatar = new File(new URI(mDestinationUri.toString()));
            classAvatar = ImgUtil.compressAvatarPhoto(this, classAvatar);
        }
        String name = classnameEdt.getText().toString();
        final String course = courseEdt.getText().toString();
        String userId = getUserId();
        classAppAction.createClass(classAvatar, name, course, userId, this, new ActionCallbackListener<ClassUser>() {
            @Override
            public void onSuccess(ClassUser data, String message) {
                MainClassFragment.refreshFlag = true;
                setClassId(data.getClass_id().toString());
                setClassUserId(data.getClass_user_id().toString());
                setUserTitle(ConstantUtil.TEACHER_USER_TITLE);
                setCourse(course);
                Intent intent = new Intent(CreateClassActivity.this, ShowInviteCodeActivity.class);
                intent.putExtra("avatarUrl", data.getMy_class().getAvatar());
                finish();
                startActivity(intent);
                LoadingDialogUtil.closeDialog();//关闭加载中动画
                if (!isDefaultAvatar) {
                    classAvatar.delete();//删除缓存的压缩图片
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog();//关闭加载中动画
                if (!isDefaultAvatar) {
                    classAvatar.delete();//删除缓存的压缩图片
                }
            }
        });

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
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    //取消点击事件
    public void cancel(View view) {
        selectphotoPopup.dismiss();
    }

    //选择头像点击事件
    public void showSelectPopup(View view) {
        if (selectphotoPopup == null) {
            View contentView = LayoutInflater.from(CreateClassActivity.this)
                    .inflate(R.layout.popup_selectphoto, null);
            selectphotoPopup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            selectphotoPopup.setOutsideTouchable(true);
            selectphotoPopup.setBackgroundDrawable(new BitmapDrawable());
        }
        selectphotoPopup.showAtLocation(getWindow().getDecorView(),
                Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .withTargetActivity(CropActivity.class)
                .start(this);
    }

    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(CreateClassActivity.this.getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AvatarImg.setImageBitmap(bitmap);
        } else {
            Toast.makeText(CreateClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e("CreateClassActivity", "handleCropError: ", cropError);
            Toast.makeText(CreateClassActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CreateClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
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
                    startCropActivity(Uri.fromFile(temp));
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    startCropActivity(data.getData());
                    break;
                case UCrop.REQUEST_CROP:    // 裁剪图片结果
                    handleCropResult(data);
                    isDefaultAvatar = false;
                    break;
                case UCrop.RESULT_ERROR:    // 裁剪图片错误
                    handleCropError(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}