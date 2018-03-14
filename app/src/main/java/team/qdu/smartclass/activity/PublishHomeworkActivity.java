package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.HomeworkAddPhotoAdapter;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;
import team.qdu.smartclass.util.ButtonUtil;
import team.qdu.smartclass.util.GlideImageLoader;
import team.qdu.smartclass.util.ImgUtil;
import team.qdu.smartclass.view.CustomDatePicker;
import team.qdu.smartclass.view.HorizontalListView;
import team.qdu.smartclass.view.SelectDialog;

/**
 * Created by 11602 on 2018/2/6.
 */

public class PublishHomeworkActivity extends SBaseActivity implements AdapterView.OnItemClickListener {

    private EditText homeworkTitleEdt;
    private TextView homeworkDeadlineTxt;
    private EditText homeworkDetailEdt;
    private ImageView photoImg;
    private HorizontalListView homeworkPhotoList;
    private CustomDatePicker customDatePicker;
    private PopupWindow selectphotoPopup;

    private HomeworkAddPhotoAdapter homeworkAddPhotoAdapter;
    private int maxImgCount = 6;               //允许选择图片最大数
    ArrayList<ImageItem> images = null;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    //权限
    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
//    //拍照临时图片
//    private String mTempPhotoPath;
//    //获得的照片Uri
//    String photoUri;
//    //是否上传图片
//    boolean ifUploadPhoto;
//    //相册选图标记
//    private static final int GALLERY_REQUEST_CODE = 0;
//    //相机拍照标记
//    private static final int CAMERA_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tea_publishhomework);
//        ifUploadPhoto = false;
        initView();
        initEvent();
        initDatePicker();
//        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
    }

    private void initView() {
        homeworkTitleEdt = (EditText) findViewById(R.id.edt_homework_title);
        homeworkDeadlineTxt = (TextView) findViewById(R.id.txt_homework_deadline);
        homeworkDetailEdt = (EditText) findViewById(R.id.edt_homework_detail);
        photoImg = (ImageView) findViewById(R.id.img_homework_photo);
        homeworkPhotoList = (HorizontalListView) findViewById(R.id.list_homework_photo);
        initImagePicker();
    }

    private void initEvent() {
        homeworkAddPhotoAdapter = new HomeworkAddPhotoAdapter(context, maxImgCount);
        homeworkPhotoList.setAdapter(homeworkAddPhotoAdapter);
        homeworkPhotoList.setOnItemClickListener(this);
    }

    //初始化ImagePicker图片选择库
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
    }

    //发布作业点击事件
    public void toPublish(View view) throws URISyntaxException {
        if (!ButtonUtil.isFastDoubleClick(view.getId())) {
            CompressHelper compressHelper = ImgUtil.getCompressHelper(context);
            String title = homeworkTitleEdt.getText().toString();
            String deadline = homeworkDeadlineTxt.getText().toString();
            String detail = homeworkDetailEdt.getText().toString();
            List<File> photoList = new ArrayList<>();
            for (int i = 0; i < homeworkAddPhotoAdapter.getImagesSize(); i++) {
                photoList.add(compressHelper.compressToFile(new File(homeworkAddPhotoAdapter.getImages().get(i).path)));
            }

            homeworkAppAction.pushHomework(title, deadline, detail, photoList, getClassId(),
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
//            File photo = null;
//            if (ifUploadPhoto) {
////                photo = new File(new URI(photoUri.toString()));
//                photo = new File(photoUri);
//            }
//
//            homeworkAppAction.pushHomework(title, deadline, detail, photo, getClassId(),
//                    new ActionCallbackListener<Void>() {
//                        @Override
//                        public void onSuccess(Void data, String message) {
//                            Toast.makeText(PublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
//                            TeaHomeworkUnderwayFragment.refreshFlag = true;
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure(String errorEvent, String message) {
//                            Toast.makeText(PublishHomeworkActivity.this, message, Toast.LENGTH_SHORT).show();
//                        }
//                    });
        }
    }

    //截止日期点击事件
    public void toPickDate(View view) {
        customDatePicker.show(homeworkDeadlineTxt.getText().toString());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == parent.getChildCount() - 1 && position != maxImgCount - 1) {
            List<String> names = new ArrayList<>();
            names.add("拍照");
            names.add("相册");
            super.showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 直接调起相机
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - homeworkAddPhotoAdapter.getImagesSize());
                            Intent intent = new Intent(context, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                            break;
                        case 1:
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - homeworkAddPhotoAdapter.getImagesSize());
                            Intent intent1 = new Intent(context, ImageGridActivity.class);
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                            startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            break;
                        default:
                            break;
                    }

                }
            }, names);
        } else {
            //打开预览
            Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) homeworkAddPhotoAdapter.getImages());
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    homeworkAddPhotoAdapter.addItems(images);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    homeworkAddPhotoAdapter.setItems(images);
                }
            }
        }
    }

    //添加图片点击事件
//    public void toAddPhoto(View view) {
//        if (selectphotoPopup == null) {
//            View contentView = LayoutInflater.from(PublishHomeworkActivity.this)
//                    .inflate(R.layout.popup_selectphoto, null);
//            selectphotoPopup = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
//            selectphotoPopup.setOutsideTouchable(true);
//            selectphotoPopup.setBackgroundDrawable(new BitmapDrawable());
//        }
//        selectphotoPopup.showAtLocation(getWindow().getDecorView(),
//                Gravity.CENTER | Gravity.BOTTOM, 0, 0);
//    }

    //拍照点击事件
//    public void takePhoto(View view) {
//        selectphotoPopup.dismiss();
//        /**
//         * 1)使用ContextCompat.chefkSelfPermission(),因为Context.permission
//         * 只在棒棒糖系统中使用
//         * 2）总是检查权限（即使权限被授予）因为用户可能会在设置中移除你的权限*/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//        } else {
//            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            //下面这句指定调用相机拍照后的照片存储的路径
//            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTempPhotoPath)));
//            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
//        }
//    }

    //从相册中获取点击事件
//    public void pickFromGallery(View view) {
//        selectphotoPopup.dismiss();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//        } else {
//            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
//            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
//            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
//        }
//    }

    //取消点击事件
//    public void cancel(View view) {
//        selectphotoPopup.dismiss();
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                } else {
//                    Toast.makeText(this, "读写手机存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    //startActivityForResult之后回调
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == CreateClassActivity.RESULT_OK) {
//            switch (requestCode) {
//                case CAMERA_REQUEST_CODE:   // 调用相机拍照
//                    File temp = new File(mTempPhotoPath);
//                    photoUri = temp.getPath();
//                    ifUploadPhoto = true;
//                    break;
//                case GALLERY_REQUEST_CODE:  // 直接从相册获取
//                    photoUri = ImgUtil.getPath(context, data.getData());
//                    ifUploadPhoto = true;
//                    break;
//            }
//            Bitmap bitmap = BitmapFactory.decodeFile(photoUri);
//            photoImg.setImageBitmap(bitmap);
//            //解决部分自动旋转问题
//            photoImg.setRotation(ImgUtil.getBitmapDegree(photoUri));
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
