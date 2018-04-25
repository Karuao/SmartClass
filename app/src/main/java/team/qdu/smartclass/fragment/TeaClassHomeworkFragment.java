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
import team.qdu.smartclass.adapter.TeaHomeworkFragmentPagerAdapter;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassHomeworkFragment extends SBaseFragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private boolean isPrepared;
    //该页面
    View currentPage;

    private ViewPager teaHomeworkVpager;
    private TeaHomeworkFragmentPagerAdapter teaHomeworkFragmentPagerAdapter;

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
        currentPage = inflater.inflate(R.layout.class_tab03_admin, container, false);
        initView();
        initEvents();
        isPrepared = true;
        lazyLoad();
        return currentPage;
    }

    @Override
    public void onDestroyView() {
        teaHomeworkVpager.setAdapter(null);
        super.onDestroyView();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }

    //写这里的时候出现了一个机制，该Fragment因为滑动被销毁的时候，他里面的ViewPager控制的Fragment还会自动加载，留待以后理解
    //若每次都重设Adapter，从资源一个个过来会请求两遍数据，但设置失败，从资源直接跳过来，会请求三遍数据，设置成功
    //调到资源假销毁，调到成员伪加载，再到作业重设Adapter会失败，onDestoryView中setAdapter(null)替换掉修复成功
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //只在移出后重加载
        if (teaHomeworkVpager.getAdapter() == null) {
            teaHomeworkFragmentPagerAdapter = new TeaHomeworkFragmentPagerAdapter(getChildFragmentManager());
            teaHomeworkVpager.setAdapter(teaHomeworkFragmentPagerAdapter);
        }
//        teaHomeworkVpager.setAdapter(null);
//        teaHomeworkFragmentPagerAdapter.getTeaHomeworkUnderwayFragment();
//        if (teaHomeworkFragmentPagerAdapter.getTeaHomeworkUnderwayFragment().isVisible == true) {
//            teaHomeworkFragmentPagerAdapter.getTeaHomeworkUnderwayFragment().setHomeworkList();
//        }
//        teaHomeworkFragmentPagerAdapter = new TeaHomeworkFragmentPagerAdapter(getChildFragmentManager());
//        teaHomeworkVpager.setAdapter(teaHomeworkFragmentPagerAdapter);
//        teaHomeworkFragmentPagerAdapter.getTeaHomeworkUnderwayFragment().setHomeworkList();
//        teaHomeworkFragmentPagerAdapter.getTeaHomeworkFinishFragment().setHomeworkList();
    }

    //初始化View
    private void initView() {
        titleBarClassNameTxt = (TextView) currentPage.findViewById(R.id.txt_titlebar_classname);
        titleBarClassNameTxt.setText(((SBaseActivity) getActivity()).getCourse());
        teaHomeworkVpager = (ViewPager) currentPage.findViewById(R.id.class_tab03_admin_viewpager);
        tabUnderway = (LinearLayout) currentPage.findViewById(R.id.ll_class_homework_underway);
        tabFinish = (LinearLayout) currentPage.findViewById(R.id.ll_class_homework_finish);
        tvUnderway = (TextView) currentPage.findViewById(R.id.tv_class_homework_underway);
        tvFinish = (TextView) currentPage.findViewById(R.id.tv_class_homework_finish);
//        teaHomeworkFragmentPagerAdapter = new TeaHomeworkFragmentPagerAdapter(getChildFragmentManager());
        //设置tab颜色为进行中作业tab为选中状态
        tvFinish.setTextColor(getResources().getColor(R.color.hinter));
    }

    private void initEvents() {
//        teaHomeworkVpager.setAdapter(teaHomeworkFragmentPagerAdapter);
        teaHomeworkVpager.addOnPageChangeListener(this);
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
                teaHomeworkVpager.setCurrentItem(0);
                break;
            case R.id.ll_class_homework_finish:
                tvFinish.setTextColor(getResources().getColor(R.color.classbottom));
                teaHomeworkVpager.setCurrentItem(1);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int currentItem = teaHomeworkVpager.getCurrentItem();
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

    public TeaHomeworkFragmentPagerAdapter getTeaHomeworkFragmentPagerAdapter() {
        return teaHomeworkFragmentPagerAdapter;
    }
}
