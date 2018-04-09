package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2018/2/3.
 */

public class ShowInviteCodeActivity extends SBaseActivity {

    ImageView avatarImg;
    TextView invitecodeTxt;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_showinvitecode);
        initView();
    }

    //初始化控件
    private void initView() {
        avatarImg = (ImageView) findViewById(R.id.img_avatar);
        invitecodeTxt = (TextView) findViewById(R.id.txt_invitecode);
//        Intent intent = getIntent();
//        Bitmap avatar = null;
//        //未处理如果未设置头像
//        try {
//            avatar = MediaStore.Images.Media.getBitmap(
//                    this.getContentResolver(), (Uri) intent.getExtras().get("avatarUri"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        avatarImg.setImageBitmap(avatar);
        String avatarUrl = getIntent().getStringExtra("avatarUrl");
        if (!TextUtils.isEmpty(avatarUrl)) {
            fileAppAction.cacheImg(avatarUrl, new ActionCallbackListener<File>() {
                @Override
                public void onSuccess(File data, String message) {
                    Glide.with(context).load(data.getPath()).into(avatarImg);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        invitecodeTxt.setText(getClassId());
    }

    public void accessClass(View view) {
        Intent intent = new Intent(ShowInviteCodeActivity.this, TeaClassMainActivity.class);
        finish();
        startActivity(intent);
    }
}
