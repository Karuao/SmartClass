package team.qdu.smartclass.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/3/1.
 */

public class ShowPhotoActivity extends SBaseActivity {

    ImageView showPhotoImg;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_showphoto);
        initView();
    }

    private void initView() {
        showPhotoImg = (ImageView) findViewById(R.id.img_showphoto);
        //显示上一页面点击的图片
        File filePath = new File(Environment.getExternalStorageDirectory() + File.separator + "showPhoto.png");
        Drawable drawable = Drawable.createFromPath(filePath.toString());
        showPhotoImg.setImageDrawable(drawable);
        filePath.delete();
    }
}
