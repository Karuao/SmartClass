package team.qdu.smartclass.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jauker.widget.BadgeView;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.StuClassFragmentPagerAdapter;
import team.qdu.smartclass.fragment.MainClassFragment;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * 班课主页
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class StuClassMainActivity extends SBaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private ViewPager classVpager;
    private StuClassFragmentPagerAdapter stuClassFragmentPagerAdapter;
    //tab
    private LinearLayout tabResource;
    private LinearLayout tabMember;
    private LinearLayout tabHomework;
    private LinearLayout tabInform;
    private LinearLayout tabClassinfo;

    private ImageView imgResource;
    private ImageView imgMember;
    private ImageView imgHomework;
    private ImageView imgInform;
    private ImageView imgClassinfo;
    //红点BadgeView
    private BadgeView materailBadgeView;
    private BadgeView homeworkBadgeView;
    private BadgeView informBadgeView;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;

    //设置考试安排和学习目标是否单行显示
    public boolean singleLine1=true;
    public boolean singleLine2=true;

    TextView stuClassDetail;
    TextView stuClassExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_mainpage);
        stuClassFragmentPagerAdapter = new StuClassFragmentPagerAdapter(getSupportFragmentManager());
        initView();
        initEvents();
    }

    //初始化View
    private void initView() {
        //页面切换
        classVpager = (ViewPager) findViewById(R.id.class_viewpager);
        tabResource = (LinearLayout) findViewById(R.id.ll_class_resource);
        tabMember = (LinearLayout) findViewById(R.id.ll_class_member);
        tabHomework = (LinearLayout) findViewById(R.id.ll_class_homework);
        tabInform = (LinearLayout) findViewById(R.id.ll_class_inform);
        tabClassinfo = (LinearLayout) findViewById(R.id.ll_class_classinfo);
        imgResource = (ImageView) findViewById(R.id.iv_class_resource);
        imgMember = (ImageView) findViewById(R.id.iv_class_member);
        imgHomework = (ImageView) findViewById(R.id.iv_class_homework);
        imgInform = (ImageView) findViewById(R.id.iv_class_inform);
        imgClassinfo = (ImageView) findViewById(R.id.iv_class_classinfo);
        //设置红点
        if ("是".equals(getIntent().getStringExtra("ifNewMaterial"))) {
            materailBadgeView = new BadgeView(context);
            materailBadgeView.setMaxHeight(40);
            materailBadgeView.setMaxWidth(40);
            materailBadgeView.setTextColor(Color.parseColor("#CCFF0000"));
            materailBadgeView.setBadgeMargin(0, 0, 24, 0);
            materailBadgeView.setText("1");
            materailBadgeView.setTargetView(tabResource);
        }
        if ("是".equals(getIntent().getStringExtra("ifNewHomework"))) {
            homeworkBadgeView = new BadgeView(context);
            homeworkBadgeView.setMaxHeight(40);
            homeworkBadgeView.setMaxWidth(40);
            homeworkBadgeView.setTextColor(Color.parseColor("#CCFF0000"));
            homeworkBadgeView.setBadgeMargin(0, 0, 24, 0);
            homeworkBadgeView.setText("1");
            homeworkBadgeView.setTargetView(tabHomework);
        }
        if (getIntent().getSerializableExtra("unreadInformationNum") != null
                && ((int) getIntent().getSerializableExtra("unreadInformationNum")) > 0) {
            informBadgeView = new BadgeView(context);
            informBadgeView.setBadgeMargin(0, 0, 24, 0);
            informBadgeView.setText(getIntent().getSerializableExtra("unreadInformationNum").toString());
            informBadgeView.setTargetView(tabInform);
        }
    }

    public void initView2(){
        stuClassDetail = (TextView)findViewById(R.id.tv_class_goal_details);
        stuClassExam = (TextView)findViewById(R.id.tv_class_exam_details);
    }

    private void initEvents() {
        //点击效果
        tabResource.setOnClickListener(this);
        tabMember.setOnClickListener(this);
        tabHomework.setOnClickListener(this);
        tabInform.setOnClickListener(this);
        tabClassinfo.setOnClickListener(this);
        tabHomework.callOnClick();
        classVpager.setAdapter(stuClassFragmentPagerAdapter);
        classVpager.setOffscreenPageLimit(4);
        classVpager.setCurrentItem(1);
        classVpager.addOnPageChangeListener(this);
        //初始化tab按钮颜色，作业为选中
        resetImg();
        imgMember.setImageResource(R.drawable.class_member_select);
    }

    //切换图片颜色
    private void resetImg() {
        imgResource.setImageResource(R.drawable.class_resource);
        imgMember.setImageResource(R.drawable.class_member);
        imgHomework.setImageResource(R.drawable.class_homework);
        imgInform.setImageResource(R.drawable.class_inform);
        imgClassinfo.setImageResource(R.drawable.class_classinfo);
    }

    public void toSignInforStudent(View view) {
        startActivity(new Intent(StuClassMainActivity.this, StuMemberSigninActivity.class));
    }

    public void checkExpDetail(View view){
        Intent intent = new Intent(new Intent(StuClassMainActivity.this,ShowExpDetailActivity.class));
        intent.putExtra("classId",getClassId());
        intent.putExtra("userId",getUserId());
        startActivity(intent);
    }

    //若某个页面有新推送，查看页面后进行的操作
    private void readNew(String whichPage) {
        classAppAction.readNew(getClassUserId(), whichPage);
    }

    //通知tab上的小红标数字减1
    public void decrementInformBadge() {
        if (informBadgeView != null) {
            informBadgeView.decrementBadgeCount(1);
        }
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_class_resource:
                imgResource.setImageResource(R.drawable.class_resource_select);
                classVpager.setCurrentItem(0);
                if ("是".equals(getIntent().getStringExtra("ifNewMaterial"))) {
                    getIntent().putExtra("ifNewMaterial", "否");
                    ((ViewGroup) materailBadgeView.getParent()).removeView(materailBadgeView);
//                    materailBadgeView.decrementBadgeCount(1);
                    readNew("material");
                }
                break;
            case R.id.ll_class_member:
                imgMember.setImageResource(R.drawable.class_member_select);
                classVpager.setCurrentItem(1);
                break;
            case R.id.ll_class_homework:
                imgHomework.setImageResource(R.drawable.class_homework_select);
                classVpager.setCurrentItem(2);
                if ("是".equals(getIntent().getStringExtra("ifNewHomework"))) {
                    getIntent().putExtra("ifNewHomework", "否");
                    ((ViewGroup) homeworkBadgeView.getParent()).removeView(homeworkBadgeView);
//                    homeworkBadgeView.decrementBadgeCount(1);
                    readNew("homework");
                }
                break;
            case R.id.ll_class_inform:
                imgInform.setImageResource(R.drawable.class_inform_select);
                classVpager.setCurrentItem(3);
                break;
            case R.id.ll_class_classinfo:
                imgClassinfo.setImageResource(R.drawable.class_classinfo_select);
                classVpager.setCurrentItem(4);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        int currentItem = classVpager.getCurrentItem();
        resetImg();
        switch (currentItem) {
            case 0:
                imgResource.setImageResource(R.drawable.class_resource_select);
                if ("是".equals(getIntent().getStringExtra("ifNewMaterial"))) {
                    getIntent().putExtra("ifNewMaterial", "否");
                    ((ViewGroup) materailBadgeView.getParent()).removeView(materailBadgeView);
//                    materailBadgeView.decrementBadgeCount(1);
                    readNew("material");
                }
                break;
            case 1:
                imgMember.setImageResource(R.drawable.class_member_select);
                break;
            case 2:
                imgHomework.setImageResource(R.drawable.class_homework_select);
                if ("是".equals(getIntent().getStringExtra("ifNewHomework"))) {
                    getIntent().putExtra("ifNewHomework", "否");
                    ((ViewGroup) homeworkBadgeView.getParent()).removeView(homeworkBadgeView);
//                    homeworkBadgeView.decrementBadgeCount(1);
                    readNew("homework");
                }
                break;
            case 3:
                imgInform.setImageResource(R.drawable.class_inform_select);
                break;
            case 4:
                imgClassinfo.setImageResource(R.drawable.class_classinfo_select);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    public void quitClass(View view) {
        final String classId = getClassId();
        final String userId = getUserId();
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要退出此班课？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogUtil.createLoadingDialog(StuClassMainActivity.this,"加载中...");
                        StuClassMainActivity.this.classAppAction.quitClass(classId, userId, new ActionCallbackListener<Void>() {
                            @Override
                            public void onSuccess(Void data, String message) {
                                finish();
                                MainClassFragment.refreshFlag = true;
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                .setNegativeButton("取消", null)
                .show();
    }

    public void seeMoreDetailForStu(View view){
        initView2();
        if(singleLine1==true){
            stuClassDetail.setSingleLine(false);
            singleLine1 = false;
        }else {
            stuClassDetail.setSingleLine(true);
            singleLine1 = true;
        }
    }

    public void seeMoreExamForStu(View view){
        initView2();
        if(singleLine2==true){
            stuClassExam.setSingleLine(false);
            singleLine2 = false;
        }else {
            stuClassExam.setSingleLine(true);
            singleLine2 = true;
        }
    }
}

