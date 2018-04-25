package team.qdu.core.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理工具类
 * Created by 11602 on 2018/2/8.
 */

public class ImgUtil {

    public static List<File> compressPhotoes(List<String> imgPathList, Context context) {
        List<File> photoList = new ArrayList<>();
        for (int i = 0; i < imgPathList.size(); i++) {
            photoList.add(new CompressHelper.Builder(context)
                    .setMaxWidth(1920)
                    .setMaxHeight(1080)
                    .setQuality(80)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(context.getExternalCacheDir().getAbsolutePath())
                    .build()
                    .compressToFile(
                            new File(imgPathList.get(i))));
        }
        return photoList;
    }
}
