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
import team.qdu.smartclass.adapter.TeaHomeworkFragmentPagerAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class StuClassHomeworkFragment extends SBaseFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

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
        return currentPage;
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
        tvFinish.setTextColor(getActivity().getColor(R.color.hinter));
    }

    private void initEvents() {
        stuHomeworkVpager.setAdapter(stuHomeworkFragmentPagerAdapter);
        stuHomeworkVpager.addOnPageChangeListener(this);
        tabUnderway.setOnClickListener(this);
        tabFinish.setOnClickListener(this);
    }

    //切换字体颜色
    private void resetImg() {
        tvUnderway.setTextColor(getActivity().getColor(R.color.hinter));
        tvFinish.setTextColor(getActivity().getColor(R.color.hinter));
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_class_homework_underway:
                tvUnderway.setTextColor(getActivity().getColor(R.color.classbottom));
                stuHomeworkVpager.setCurrentItem(0);
                break;
            case R.id.ll_class_homework_finish:
                tvFinish.setTextColor(getActivity().getColor(R.color.classbottom));
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
                tvUnderway.setTextColor(getActivity().getColor(R.color.classbottom));
                break;
            case 1:
                tvFinish.setTextColor(getActivity().getColor(R.color.classbottom));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
