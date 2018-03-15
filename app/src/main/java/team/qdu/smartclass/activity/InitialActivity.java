package team.qdu.smartclass.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import team.qdu.smartclass.R;

import static com.lzy.imagepicker.ui.ImageGridActivity.REQUEST_PERMISSION_CAMERA;

/**
 * 控制权限，不给权限就闪退
 * Created by 11602 on 2018/3/15.
 */

public class InitialActivity extends SBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_initial);
        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
        } else {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }

    //请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED
                && grantResults[1] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "相机和存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "相机权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
        } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(context, LoginActivity.class));
        }
        finish();
    }
}
