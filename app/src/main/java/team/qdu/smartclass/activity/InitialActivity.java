package team.qdu.smartclass.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.util.Tools;
import team.qdu.smartclass.view.CommonProgressDialog;

import static com.lzy.imagepicker.ui.ImageGridActivity.REQUEST_PERMISSION_CAMERA;

/**
 * 控制权限，不给权限就闪退
 * Created by 11602 on 2018/3/15.
 * 自动更新软件
 * Modified by Rock on 2018/4/4
 */

public class InitialActivity extends SBaseActivity {

    private CommonProgressDialog progressDialog;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_initial);
        checkPermissions();
    }

    //获取版本号检测是否需要更新
    private void checkUpdate() {
        fileAppAction.getVersionInfo(new ActionCallbackListener<Map>() {
            @Override
            public void onSuccess(Map data, String message) {
                int currentVersion = Tools.getVersion(InitialActivity.this);
                int latestVersion = Integer.parseInt((String) data.get("version"));
                String content = (String) data.get("content");
                String url = (String) data.get("url");
                if (latestVersion > currentVersion) {
                    showDialog(content, url);
                } else {
                    startAndFinishActivity();
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                startAndFinishActivity();
            }
        });
    }

    //检查权限
    private void checkPermissions() {
        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))) {
            //如果有权限未获取则获取权限
            ActivityCompat.requestPermissions(InitialActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CAMERA);
        } else {
            checkUpdate();
        }
    }

    //显示更新进度Dialog
    private void showDialog(final String content, final String url) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog = new CommonProgressDialog(InitialActivity.this);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setCustomTitle(LayoutInflater.from(InitialActivity.this).inflate(
                                R.layout.title_dialog, null));
                        progressDialog.setMessage("正在下载");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        fileAppAction.downloadApp(url, InitialActivity.this, new ActionCallbackListener<Object>() {
                            @Override
                            public void onSuccess(Object data, String message) {
                                if ("update".equals(message)) {
                                    progressDialog.setIndeterminate(false);
                                    progressDialog.setMax(100);
                                    progressDialog.setProgress((Integer) data);
                                } else {
                                    progressDialog.dismiss();
                                    //安装应用
                                    startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile((File) data),
                                            "application/vnd.android.package-archive"));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                progressDialog.dismiss();
                                //更新失败进入软件
                                Toast.makeText(InitialActivity.this, message, Toast.LENGTH_SHORT).show();
                                startAndFinishActivity();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startAndFinishActivity();
                    }
                })
                .show();
    }

    //start LoginActivity, finish this
    private void startAndFinishActivity() {
        sprfMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sprfMain.edit();
        if (getUserId() != null) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
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
            finish();
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "相机权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
            finish();
        } else if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "存储权限未开启，请到权限管理中开启权限", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            checkUpdate();
        }
    }
}
