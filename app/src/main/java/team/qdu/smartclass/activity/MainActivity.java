package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.MainFragmentPagerAdapter;

/**
 * 主页
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class MainActivity extends SBaseActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    PopupWindow popupWindow;
    private ViewPager mainVpager;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    //tab
    private LinearLayout tabClass;
    private LinearLayout tabUser;
    private ImageButton imgClass;
    private ImageButton imgUser;
    private TextView userName;
    private TextView userNumber;
    private TextView userGender;
    private TextView userUniversity;
    private TextView userDepartment;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        initView();
        JPushInterface.setAlias(context,1,getUserId());
        initEvents();
    }

    //初始化View
    private void initView() {
        //页面切换
        mainVpager = (ViewPager) findViewById(R.id.vpager_main);
        tabClass = (LinearLayout) findViewById(R.id.tab01);
        tabUser = (LinearLayout) findViewById(R.id.tab02);
        imgClass = (ImageButton) findViewById(R.id.class_img);
        imgUser = (ImageButton) findViewById(R.id.user_img);
        //设置tab颜色为班课tab为选中状态
        imgUser.setImageResource(R.drawable.user_notselect);
    }

    //点击效果
    private void initEvents() {
        mainVpager.setAdapter(mainFragmentPagerAdapter);
        mainVpager.addOnPageChangeListener(this);
        tabClass.setOnClickListener(this);
        tabUser.setOnClickListener(this);
    }

    //切换图片颜色
    private void resetImg() {
        imgClass.setImageResource(R.drawable.main_class_notselect);
        imgUser.setImageResource(R.drawable.user_notselect);
    }

    //修改个人信息点击事件
    public void toChangeInfo(View view) {
        startActivity(new Intent(MainActivity.this, ChangeInfoActivity.class));
    }

    //设置点击事件
    public void toSet(View view) {
        application.addActivity(MainActivity.this);
        startActivity(new Intent(MainActivity.this, SetActivity.class));
    }

    //点击右上角加号显示或隐藏创建加入班课按钮
    public void toCreateList(View view) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_addclass, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(findViewById(R.id.llayout_addibtn));
    }

    //创建班课点击事件
    public void toCreateClass(View view) {
            Intent intent;
            if (ifNeedFillProfile()) {
                intent = new Intent(MainActivity.this, PrepareClassActivity.class);
                intent.putExtra("do", "create");
                Toast.makeText(context, "创建班课前，请完善个人信息", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(MainActivity.this, CreateClassActivity.class);
            }
            startActivity(intent);
    }

    //加入班课点击事件
    public void toJoinClass(View view) {
            Intent intent;
            if (ifNeedFillProfile()) {
                intent = new Intent(MainActivity.this, PrepareClassActivity.class);
                intent.putExtra("do", "join");
                Toast.makeText(context, "创建班课前，请完善个人信息", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(MainActivity.this, JoinClassActivity.class);
            }
            startActivity(intent);
    }

    //是否需要完善个人信息
    private boolean ifNeedFillProfile() {
        popupWindow.dismiss();
        userName = (TextView) findViewById(R.id.userName);
        userNumber = (TextView) findViewById(R.id.userNumber);
        userGender = (TextView) findViewById(R.id.userGender);
        userUniversity = (TextView) findViewById(R.id.userUniversity);
        userDepartment = (TextView) findViewById(R.id.userDepartment);
        if (TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(userNumber.getText())
                || TextUtils.isEmpty(userGender.getText()) || TextUtils.isEmpty(userGender.getText())
                || TextUtils.isEmpty(userUniversity.toString())) {
            return true;
        }
        return false;
    }

    //底部tab键点击事件
    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.tab01:
                imgClass.setImageResource(R.drawable.main_class_select);
                mainVpager.setCurrentItem(0);
                break;
            case R.id.tab02:
                imgUser.setImageResource(R.drawable.user_select);
                mainVpager.setCurrentItem(1);
                break;
        }
    }

    //ViewPager重写的方法
    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        int currentItem = mainVpager.getCurrentItem();
        resetImg();
        switch (currentItem) {
            case 0:
                imgClass.setImageResource(R.drawable.main_class_select);
                break;
            case 1:
                imgUser.setImageResource(R.drawable.user_select);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}

