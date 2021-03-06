package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.SBaseActivity;
import team.qdu.smartclass.adapter.StuHomeworkFragmentPagerAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassHomeworkFragment extends SBaseFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private boolean isPrepared;
    //该页面
    private View currentPage;

    private ViewPager stuHomeworkVpager;
    private StuHomeworkFragmentPagerAdapter stuHomeworkFragmentPagerAdapter;

    private LinearLayout tabUnderway;
    private LinearLayout tabFinish;

    //标题栏班课名
    private TextView titleBarClassNameTxt;
    private TextView tvUnderway;
    private TextView tvFinish;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03, container, false);
        initView();
        initEvents();
        isPrepared = true;
        lazyLoad();
        return currentPage;
    }

    @Override
    public void onDestroyView() {
        stuHomeworkVpager.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
//        if (stuHomeworkVpager.getAdapter() == null) {
////            stuHomeworkFragmentPagerAdapter = new StuHomeworkFragmentPagerAdapter(getChildFragmentManager());
//            stuHomeworkVpager.setAdapter(stuHomeworkFragmentPagerAdapter);
//        } else {
////            stuHomeworkVpager.setAdapter(stuHomeworkFragmentPagerAdapter);
//            //调用的时候会出现mContext null
////            stuHomeworkFragmentPagerAdapter.getStuHomeworkUnderwayFragment().setHomeworkList();
////            stuHomeworkFragmentPagerAdapter.getStuHomeworkFinishFragment().setHomeworkList();
//        }
        stuHomeworkVpager.setAdapter(stuHomeworkFragmentPagerAdapter);
        if (stuHomeworkVpager.getAdapter() != null) {
            if (tvFinish.getCurrentTextColor() == getResources().getColor(R.color.classbottom)) {
                tabFinish.callOnClick();
            }
        }
    }

    //初始化View
    private void initView() {
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity)getActivity()).getCourse());
        stuHomeworkVpager = (ViewPager) currentPage.findViewById(R.id.class_tab03_viewpager);
        tabUnderway = (LinearLayout) currentPage.findViewById(R.id.ll_class_homework_underway);
        tabFinish = (LinearLayout) currentPage.findViewById(R.id.ll_class_homework_finish);
        tvUnderway = (TextView) currentPage.findViewById(R.id.tv_class_homework_underway);
        tvFinish = (TextView) currentPage.findViewById(R.id.tv_class_homework_finish);
        stuHomeworkFragmentPagerAdapter = new StuHomeworkFragmentPagerAdapter(getChildFragmentManager());
        //设置tab颜色为进行中作业tab为选中状态
        tvFinish.setTextColor(getResources().getColor(R.color.hinter));
    }

    private void initEvents() {
//        stuHomeworkVpager.setAdapter(stuHomeworkFragmentPagerAdapter);
        stuHomeworkVpager.addOnPageChangeListener(this);
        tabUnderway.setOnClickListener(this);
        tabFinish.setOnClickListener(this);
    }

    //切换字体颜色
    private void resetImg() {
        tvUnderway.setTextColor(getResources().getColor(R.color.hinter));
        tvFinish.setTextColor(getResources().getColor(R.color.hinter));
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_class_homework_underway:
                tvUnderway.setTextColor(getResources().getColor(R.color.classbottom));
                stuHomeworkVpager.setCurrentItem(0);
                break;
            case R.id.ll_class_homework_finish:
                tvFinish.setTextColor(getResources().getColor(R.color.classbottom));
                stuHomeworkVpager.setCurrentItem(1);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int currentItem = stuHomeworkVpager.getCurrentItem();
        resetImg();
        switch (currentItem) {
            case 0:
                tvUnderway.setTextColor(getResources().getColor(R.color.classbottom));
                break;
            case 1:
                tvFinish.setTextColor(getResources().getColor(R.color.classbottom));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
