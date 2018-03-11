package team.qdu.smartclass.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.User;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.MainUserFragment;

/**
 * 创建加入班课之前的工作，保证个人信息完整
 * Created by 11602 on 2018/2/1.
 */

public class PrepareClassActivity extends SBaseActivity {

    TextView modifyUserAccount;
    TextView modifyUserName;
    AppCompatSpinner modifyUserGender;
    AppCompatSpinner modifyUserUniversity;
    AppCompatSpinner modifyUserDepartment;
    TextView modifyUserMotto;
    TextView modifyUserNumber;
    Button btn;

    ImageView AvatarImg;


    //拍照临时图片
    private String mTempPhotoPath;
    //剪切后图像文件
    private Uri mDestinationUri;
    //判断是否使用默认头像
    private boolean isDefaultAvatar = false;


    //对话框
    private AlertDialog.Builder alert;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_change);
        mDestinationUri = Uri.fromFile(new File(this.getCacheDir(), "cropImage.png"));
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.png";
        initView();
        SharedPreferences sharedPreferences=this.getSharedPreferences("user", Activity.MODE_PRIVATE);
        String account=sharedPreferences.getString("account",null);
        this.userAppAction.getUserInforByAccount(account,new ActionCallbackListener<User>() {
            @Override
            public void onSuccess(User user, String message) {
                modifyUserAccount.setText(user.getAccount());
                modifyUserName.setText(user.getName());
                modifyUserMotto.setText(user.getStatus_message());
                modifyUserNumber.setText(user.getSno());
                //从服务器获取图片
                PrepareClassActivity.this.classAppAction.getBitmap(user.getAvatar(), new ActionCallbackListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap data, String message) {
                        AvatarImg.setImageBitmap(data);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                    }
                });
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

    public void initView(){
        modifyUserAccount=(TextView) findViewById(R.id.modifyUserAccount);
        modifyUserName=(TextView)findViewById(R.id.modifyUserName);
        modifyUserGender=(AppCompatSpinner) findViewById(R.id.modifyUserGender);
        modifyUserNumber=(TextView)findViewById(R.id.modifyUserNumber);
        modifyUserUniversity=(AppCompatSpinner) findViewById(R.id.modifyUserUniversity);
        modifyUserDepartment=(AppCompatSpinner)findViewById(R.id.modifyUserDepartment);
        modifyUserMotto=(TextView) findViewById(R.id.modifyUserMotto);
        AvatarImg = (ImageView) findViewById(R.id.circleImageView);
        btn=(Button)findViewById(R.id.btn_personal_change);
        builder = new AlertDialog.Builder(this);
        alert = builder.setTitle("权限被禁用")
                .setMessage("需要读写手机存储权限才能正常工作")
                .setNeutralButton("确定", null);
    }

    public void confirmClick(View view) throws URISyntaxException {
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
        String userAccount=modifyUserAccount.getText().toString();
        String userName=modifyUserName.getText().toString();
        String userGender=modifyUserGender.getSelectedItem().toString();
        String userNumber=modifyUserNumber.getText().toString();
        String userUniversity=modifyUserUniversity.getSelectedItem().toString();
        String userDepartment=modifyUserDepartment.getSelectedItem().toString();
        String userMotto=modifyUserMotto.getText().toString();
        this.userAppAction.modifyUserInformation(file,userAccount,userName,userGender,userNumber,userUniversity,userDepartment
                ,userMotto,new ActionCallbackListener<Void>() {
                    @Override
                    public void onSuccess(Void data, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        //MainUserFragment的刷新标志设为true，下次进入该页面将刷新
                        MainUserFragment.refreshFlag = true;
                        finish();
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
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
