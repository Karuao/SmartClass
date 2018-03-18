package team.qdu.smartclass.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.ui.ImageViewActivity;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.adapter.HomeworkAddPhotoAdapter;
import team.qdu.smartclass.adapter.HomeworkShowPhotoAdapter;
import team.qdu.smartclass.adapter.SBaseAdapter;
import team.qdu.smartclass.view.SelectDialog;

/**
 * 图片处理工具类
 * Created by 11602 on 2018/2/8.
 */

public class ImgUtil {
    public static final int maxImgCount = 6;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    //Android中选取文件后在onActivityResult中将intent中的Uri转换成文件的路径
    public static String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    //获取图片压缩工具
//    public static CompressHelper getCompressHelper(Context context) {
//        return new CompressHelper.Builder(context)
//                .setMaxWidth(1920)  // 默认最大宽度为720
//                .setMaxHeight(1080) // 默认最大高度为960
//                .setQuality(80)    // 默认压缩质量为80
//                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
//                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                .build();
//    }

    //初始化作业list
    public static void initHomeworkPhotoList(final Context context, final SBaseAdapter homeworkPhotoAdapter, String dirUrl, int photoNum) {
        for (int i = 0; i < photoNum; i++) {
            String urlTail = dirUrl + File.separator + i + ".jpeg";
            File photo = new File(Environment.getExternalStorageDirectory() + File.separator + urlTail);
            if (photo.exists()) {
                //图片存在从本地获取
                List<ImageItem> photoList = new ArrayList<>();
                ImageItem imageItem = new ImageItem();
                imageItem.path = photo.getPath();
                photoList.add(imageItem);
                homeworkPhotoAdapter.setItems(photoList);
            } else {
                //图片不存在从服务器获取
                ((SBaseActivity) context).imgAppAction.cacheImg(urlTail, new ActionCallbackListener<File>() {
                    @Override
                    public void onSuccess(File data, String message) {
                        List<ImageItem> photoList = new ArrayList<>();
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = data.getPath();
                        photoList.add(imageItem);
                        homeworkPhotoAdapter.addItems(photoList);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //点击HomeworkAddList选完图片后回调设置图片
    public static void setHomeworkAddList(HomeworkAddPhotoAdapter homeworkAddPhotoAdapter, int requestCode, int resultCode, Intent data) {
        ArrayList<ImageItem> images;
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

    //响应点击HomeworkAddPhotoListItem
    public static void responseClickHomeworkAddPhotoListItem(final Context context, final HomeworkAddPhotoAdapter homeworkAddPhotoAdapter, AdapterView<?> parent, int position) {
        if (position == parent.getChildCount() - 1 && position != maxImgCount - 1) {
            List<String> names = new ArrayList<>();
            names.add("拍照");
            names.add("相册");
            ((SBaseActivity) context).showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 直接调起相机
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - homeworkAddPhotoAdapter.getImagesSize());
                            Intent intent = new Intent(context, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            ((SBaseActivity) context).startActivityForResult(intent, REQUEST_CODE_SELECT);
                            break;
                        case 1:
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - homeworkAddPhotoAdapter.getImagesSize());
                            Intent intent1 = new Intent(context, ImageGridActivity.class);
                            ((SBaseActivity) context).startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            break;
                        default:
                            break;
                    }

                }
            }, names);
        } else {
            //打开预览
            Intent intentPreview = new Intent(context, ImagePreviewDelActivity.class);
            intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) homeworkAddPhotoAdapter.getImages());
            intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
            intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
            ((SBaseActivity) context).startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
        }
    }

    //响应点击HomeworkShowPhotoListItem
    public static void responseClickHomeworkShowPhotoListItem(Context context, HomeworkShowPhotoAdapter homeworkShowPhotoAdapter, int position) {
        Intent intentView = new Intent(context, ImageViewActivity.class);
        intentView.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) homeworkShowPhotoAdapter.getImages());
        intentView.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentView.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        context.startActivity(intentView);
    }

    //压缩图片
    public static void compressPhotoes(List photoList, HomeworkAddPhotoAdapter homeworkAddPhotoAdapter, Context context) {
        for (int i = 0; i < homeworkAddPhotoAdapter.getImagesSize(); i++) {
            photoList.add(new CompressHelper.Builder(context)
                    .setMaxWidth(1920)
                    .setMaxHeight(1080)
                    .setQuality(80)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build()
                    .compressToFile(
                            new File(homeworkAddPhotoAdapter.getImages().get(i).path)));
        }
    }
}
