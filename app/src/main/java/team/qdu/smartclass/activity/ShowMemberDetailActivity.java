package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.fragment.TeaClassMemberFragment;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by asus on 2018/3/12.
 */

public class ShowMemberDetailActivity extends SBaseActivity {
    private ImageView memImg;
    private TextView memName;
    private TextView memSno;
    private TextView memExp;
    private TextView memberImgTop;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.class_member_admin_memberdetails);
        initView();
        Intent intent = getIntent();
        String memberName = intent.getStringExtra("memberName");
        String memberSno = intent.getStringExtra("memberSno");
        String memberExp = intent.getStringExtra("memberExp");
        String memberAvatar = intent.getStringExtra("memberAvatar");
        memName.setText(memberName);
        memSno.setText(memberSno);
        memExp.setText(memberExp);
        if(memberAvatar.equals("")) {
            memImg.setImageResource(R.mipmap.ic_useravatar_def);
        }else {
            this.fileAppAction.cacheImg(memberAvatar, this, new ActionCallbackListener<File>() {
                @Override
                public void onSuccess(File data, String message) {
                    Glide.with(context).load(data.getPath()).into(memImg);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        memberImgTop.setText(memberName);
    }

    public void initView(){
        memImg = (ImageView)findViewById(R.id.iv_class_memberimg);
        memName = (TextView)findViewById(R.id.tv_class_membername);
        memSno = (TextView)findViewById(R.id.tv_class_membernum);
        memExp = (TextView)findViewById(R.id.tv_class_memberexp);
        memberImgTop = (TextView)findViewById(R.id.class_memberDetails_student);
    }

    public void shiftClass(View view){
        Intent intent = getIntent();
        final String classUserId = intent.getStringExtra("classUserId");
        new AlertDialog.Builder(ShowMemberDetailActivity.this)
                .setTitle("提示")
                .setMessage("确定将此学生移出班课？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(ShowMemberDetailActivity.this,"加载中");
                        ShowMemberDetailActivity.this.memberAppAction.shiftClass(classUserId, ShowMemberDetailActivity.this,new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                TeaClassMemberFragment.refreshFlag = true;
                                finish();
                                LoadingDialogUtil.closeDialog();
                            }

                            @Override
                            public void onFailure(String errorEvent, String message) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                LoadingDialogUtil.closeDialog();
                            }
                        });
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    public void checkStudentExpDetail(View view){
        Intent intent2 = getIntent();
        String memberId = intent2.getStringExtra("memberId");
        Intent intent = new Intent(ShowMemberDetailActivity.this,ShowExpDetailActivity.class);
        intent.putExtra("classId",getClassId());
        intent.putExtra("userId",memberId);
        startActivity(intent);
    }

}
