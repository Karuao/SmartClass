package team.qdu.smartclass.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kevin.crop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.MainUserFragment;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * 创建加入班课之前的工作，保证个人信息完整
 * Created by 11602 on 2018/2/1.
 */

public class PrepareClassActivity extends SBaseActivity {

    private TextView modifyUserAccount;
    private TextView modifyUserName;
    private TextView modifyUserNumber;
    private AppCompatSpinner modifyUserGender;
    private AppCompatSpinner modifyUserUniversity;
    private AppCompatSpinner modifyUserDepartment;
    private TextView modifyUserMotto;
    private File userAvatar;
    Button btn;

    private ImageView AvatarImg;
    private PopupWindow selectphotoPopup;

    //权限
    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    //拍照临时图片
    private String mTempPhotoPath;
    //剪切后图像文件
    private Uri mDestinationUri;
    //判断是否使用原头像
    private boolean isDefaultAvatar = true;

    //相册选图标记
    private static final int GALLERY_REQUEST_CODE = 0;
    //相机拍照标记
    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_change);
        mDestinationUri = Uri.fromFile(new File(this.getCacheDir(), "cropImage.png"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
        initView();
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", null);
        this.userAppAction.getUserInforByAccount(account, this,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                modifyUserAccount.setText(user.getAccount());
                modifyUserName.setText(user.getName());
                modifyUserMotto.setText(user.getStatus_message());
                modifyUserNumber.setText(user.getSno());
                //从服务器获取图片
                if(user.getAvatar()!=null) {
                    PrepareClassActivity.this.fileAppAction.cacheImg(user.getAvatar(), PrepareClassActivity.this, new ActionCallbackListener<File>() {
                        @Override
                        public void onSuccess(File data, String message) {
                            Glide.with(context).load(data.getPath()).into(AvatarImg);
                        }

                        @Override
                        public void onFailure(String errorEvent, String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                setSpinnerItemSelectedByValue(modifyUserGender,user.getGender());
                setSpinnerItemSelectedByValue(modifyUserUniversity,user.getUniversity());
                setSpinnerItemSelectedByValue(modifyUserDepartment,user.getDepartment());
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView() {
        modifyUserAccount = (TextView) findViewById(R.id.modifyUserAccount);
        modifyUserName = (TextView) findViewById(R.id.modifyUserName);
        modifyUserGender = (AppCompatSpinner) findViewById(R.id.modifyUserGender);
        modifyUserNumber = (TextView) findViewById(R.id.modifyUserNumber);
        modifyUserUniversity = (AppCompatSpinner) findViewById(R.id.modifyUserUniversity);
        modifyUserDepartment = (AppCompatSpinner) findViewById(R.id.modifyUserDepartment);
        modifyUserMotto = (TextView) findViewById(R.id.modifyUserMotto);
        AvatarImg = (ImageView) findViewById(R.id.circleImageView);
        btn = (Button) findViewById(R.id.btn_personal_change);
    }

    public void confirmClick(View view) throws URISyntaxException {
        if (!isDefaultAvatar) {
            userAvatar = new File(new URI(mDestinationUri.toString()));
        }else {
            userAvatar = null;
        }
        if(userAvatar!=null) {
            userAvatar = ImgUtil.compressAvatarPhoto(this, userAvatar);
        }
        final
        String userAccount = modifyUserAccount.getText().toString();
        String userName = modifyUserName.getText().toString();
        String userGender = modifyUserGender.getSelectedItem().toString();
        String userNumber = modifyUserNumber.getText().toString();
        String userUniversity = modifyUserUniversity.getSelectedItem().toString();
        String userDepartment = modifyUserDepartment.getSelectedItem().toString();
        String userMotto = modifyUserMotto.getText().toString();
        LoadingDialogUtil.createLoadingDialog(this,"加载中...");
        this.userAppAction.modifyUserInformation(userAvatar, userAccount, userName, userGender, userNumber, userUniversity, userDepartment
                , userMotto, this,new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        //MainUserFragment的刷新标志设为true，下次进入该页面将刷新
                        MainUserFragment.refreshFlag = true;
                        finish();
                        if ("create".equals(getIntent().getStringExtra("do"))) {
                            finish();
                            startActivity(new Intent(PrepareClassActivity.this, CreateClassActivity.class));
                        } else {
                            finish();
                            startActivity(new Intent(PrepareClassActivity.this, JoinClassActivity.class));
                        }
                        LoadingDialogUtil.closeDialog();//关闭加载中动画
                    }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                if(userAvatar!=null) {
                    userAvatar.delete();
                }
                LoadingDialogUtil.closeDialog();
            }
        });
    }

    public void setSpinnerItemSelectedByValue(AppCompatSpinner spinner, String value) {
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        int k = spinnerAdapter.getCount();
        if (value == null) {
            spinner.setSelection(0, true);
        } else {
            for (int i = 0; i < k; i++) {
                if (value.equals(spinnerAdapter.getItem(i).toString())) {
                    spinner.setSelection(i, true);
                    break;
                }
            }
        }
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
            View contentView = LayoutInflater.from(PrepareClassActivity.this)
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
                bitmap = MediaStore.Images.Media.getBitmap(PrepareClassActivity.this.getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AvatarImg.setImageBitmap(bitmap);
        } else {
            Toast.makeText(PrepareClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PrepareClassActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(PrepareClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
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
