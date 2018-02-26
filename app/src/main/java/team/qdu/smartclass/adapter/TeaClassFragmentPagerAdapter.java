package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.fragment.TeaClassDetailFragment;
import team.qdu.smartclass.fragment.TeaClassHomeworkFragment;
import team.qdu.smartclass.fragment.TeaClassInformFragment;
import team.qdu.smartclass.fragment.TeaClassMaterialFragment;
import team.qdu.smartclass.fragment.TeaClassMemberFragment;

/**
 * Created by 11602 on 2018/1/24.
 */

public class TeaClassFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 5;
    private TeaClassMaterialFragment teaClassMaterialFragment;
    private TeaClassMemberFragment teaClassMemberFragment;
    private TeaClassHomeworkFragment teaClassHomeworkFragment;
    private TeaClassInformFragment teaClassInformFragment;
    private TeaClassDetailFragment teaClassDetailFragment;

    public TeaClassFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        teaClassMaterialFragment = new TeaClassMaterialFragment();
        teaClassMemberFragment = new TeaClassMemberFragment();
        teaClassHomeworkFragment = new TeaClassHomeworkFragment();
        teaClassInformFragment = new TeaClassInformFragment();
        teaClassDetailFragment = new TeaClassDetailFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TeaClassMainActivity.PAGE_ONE:
                fragment = teaClassMaterialFragment;
                break;
            case TeaClassMainActivity.PAGE_TWO:
                fragment = teaClassMemberFragment;
                break;
            case TeaClassMainActivity.PAGE_THREE:
                fragment = teaClassHomeworkFragment;
                break;
            case TeaClassMainActivity.PAGE_FOUR:
                fragment = teaClassInformFragment;
                break;
            case TeaClassMainActivity.PAGE_FIVE:
                fragment = teaClassDetailFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    public TeaClassHomeworkFragment getTeaClassHomeworkFragment() {
        return teaClassHomeworkFragment;
    }
}
