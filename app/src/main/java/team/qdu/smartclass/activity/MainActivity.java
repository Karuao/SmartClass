package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
    private TextView userGender;
    private TextView userUniversity;
    private TextView userDepartment;

    private boolean isClick = true;
    private LinearLayout create;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        initView();
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
        mainVpager.setAdapter(mainFragmentPagerAdapter);
        mainVpager.addOnPageChangeListener(this);
    }

    //点击效果
    private void initEvents() {
        tabClass.setOnClickListener(this);
        tabUser.setOnClickListener(this);
    }

    //切换图片颜色
    private void resetImg() {
        imgClass.setImageResource(R.drawable.main_class_notselect);
        imgUser.setImageResource(R.drawable.user_notselect);
    }

    public void toChangeInfo(View view) {
        startActivity(new Intent(MainActivity.this, ChangeInfoActivity.class));
    }

    public void toSet(View view) {
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
        userGender = (TextView) findViewById(R.id.userGender);
        userUniversity = (TextView) findViewById(R.id.userUniversity);
        userDepartment = (TextView) findViewById(R.id.userDepartment);
        popupWindow.dismiss();
        if (userGender.getText().toString().isEmpty() || userDepartment.getText().toString().isEmpty()
                || userUniversity.toString().isEmpty()) {
            startActivity(new Intent(MainActivity.this, PrepareClassActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, CreateClassActivity.class));
        }
    }

    //加入班课点击事件
    public void toJoinClass(View view) {
        userGender = (TextView) findViewById(R.id.userGender);
        userUniversity = (TextView) findViewById(R.id.userUniversity);
        userDepartment = (TextView) findViewById(R.id.userDepartment);
        popupWindow.dismiss();
        if (userGender.getText().toString().isEmpty() || userDepartment.getText().toString().isEmpty()
                || userUniversity.toString().isEmpty()) {
            startActivity(new Intent(MainActivity.this, PrepareClassActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, JoinClassActivity.class));
        }
        startActivity(new Intent(MainActivity.this, JoinClassActivity.class));
    }

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

