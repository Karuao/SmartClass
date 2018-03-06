package team.qdu.smartclass.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.kevin.crop.UCrop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Class;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaClassDetailFragment;

/**
 * Created by asus on 2018/2/7.
 */

public class ModifyClassActivity extends SBaseActivity{

    EditText classnameEdt;
    EditText courseEdt;
    AppCompatSpinner universitySpin;
    AppCompatSpinner departmentSpin;
    EditText goalEdt;
    EditText examEdt;

    ImageView AvatarImg;
    PopupWindow selectphotoPopup;

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

    //对话框
    private AlertDialog.Builder alert;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_tab05_admin_compileclass);
        this.classAppAction.getClassInfor(getClassId(), new ActionCallbackListener<Class>() {
            @Override
            public void onSuccess(Class data, String message) {
                classnameEdt.setText(data.getName());
                courseEdt.setText(data.getCourse());
                goalEdt.setText(data.getDetail());
                examEdt.setText(data.getExam_shedule());
                //从服务器获取图片
                ModifyClassActivity.this.classAppAction.getBitmap(data.getAvatar(), new ActionCallbackListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap data, String message) {
                        AvatarImg.setImageBitmap(data);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                    }
                });
                setSpinnerItemSelectedByValue(universitySpin,data.getUniversity());
                setSpinnerItemSelectedByValue(departmentSpin,data.getDepartment());
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
        mDestinationUri = Uri.fromFile(new File(this.getCacheDir(), "cropImage.png"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
        initView();
    }

    //初始化View
    private void initView() {
        classnameEdt = (EditText) findViewById(R.id.modifyClassName);
        courseEdt = (EditText) findViewById(R.id.modifyCourse);
        AvatarImg = (ImageView) findViewById(R.id.modifyClassAvatar);
        universitySpin = (AppCompatSpinner)findViewById(R.id.modifyClassUniversity);
        departmentSpin = (AppCompatSpinner)findViewById(R.id.modifyClassDepartment);
        goalEdt = (EditText)findViewById(R.id.modifyClassGoal);
        examEdt = (EditText)findViewById(R.id.modifyClassExam);
        builder = new AlertDialog.Builder(this);
        alert = builder.setTitle("权限被禁用")
                .setMessage("需要读写手机存储权限才能正常工作")
                .setNeutralButton("确定", null);
    }

    public void toBack(View view) {
        finish();
    }

    //修改班课信息按钮点击事件
    public void finishModify(View view) throws URISyntaxException {
        File file = null;
        if (isDefaultAvatar) {
            //将mipmap中的默认头像转成File
            Resources r = this.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(r, R.mipmap.ic_classavatar_def);
            file = new File(Environment.getExternalStorageDirectory() + File.separator + "defClassAvatar.png");//将要保存图片的路径
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = new File(new URI(mDestinationUri.toString()));
        }
        String name = classnameEdt.getText().toString();
        String course = courseEdt.getText().toString();
        final String classId = getClassId();
        String university = universitySpin.getSelectedItem().toString();
        String department = departmentSpin.getSelectedItem().toString();
        String goal = goalEdt.getText().toString();
        String exam = examEdt.getText().toString();
        classAppAction.compileClass(classId,file, name, course, university,department,goal,exam, new ActionCallbackListener<String>() {
            @Override
            public void onSuccess(String data, String message) {
                TeaClassDetailFragment.refreshFlag= true;
                setClassId(classId);
                finish();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_LONG).show();
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

    //选择头像点击事件
    public void showSelectPopup(View view) {
        if (selectphotoPopup == null) {
            View contentView = LayoutInflater.from(ModifyClassActivity.this)
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
                bitmap = MediaStore.Images.Media.getBitmap(ModifyClassActivity.this.getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AvatarImg.setImageBitmap(bitmap);
        } else {
            Toast.makeText(ModifyClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ModifyClassActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ModifyClassActivity.this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
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

    public void setSpinnerItemSelectedByValue(AppCompatSpinner spinner,String value){
        SpinnerAdapter spinnerAdapter=spinner.getAdapter();
        int k=spinnerAdapter.getCount();
        if(value==null){
            spinner.setSelection(0,true);
        }else {
            for (int i = 0; i < k; i++) {
                if (value.equals(spinnerAdapter.getItem(i).toString())) {
                    spinner.setSelection(i, true);
                    break;
                }
            }
        }
    }
}
