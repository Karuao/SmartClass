package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.fragment.StuClassDetailFragment;
import team.qdu.smartclass.fragment.StuClassHomeworkFragment;
import team.qdu.smartclass.fragment.StuClassInformFragment;
import team.qdu.smartclass.fragment.StuClassMaterialFragment;
import team.qdu.smartclass.fragment.StuClassMemberFragment;

/**
 * Created by 11602 on 2018/1/24.
 */

public class StuClassFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 5;
    private StuClassMaterialFragment stuClassMaterialFragment;
    private StuClassMemberFragment stuClassMemberFragment;
    private StuClassHomeworkFragment stuClassHomeworkFragment;
    private StuClassInformFragment stuClassInformFragment;
    private StuClassDetailFragment stuClassDetailFragment;

    public StuClassFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        stuClassMaterialFragment = new StuClassMaterialFragment();
        stuClassMemberFragment = new StuClassMemberFragment();
        stuClassHomeworkFragment = new StuClassHomeworkFragment();
        stuClassInformFragment = new StuClassInformFragment();
        stuClassDetailFragment = new StuClassDetailFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case StuClassMainActivity.PAGE_ONE:
                fragment = stuClassMaterialFragment;
                break;
            case StuClassMainActivity.PAGE_TWO:
                fragment = stuClassMemberFragment;
                break;
            case StuClassMainActivity.PAGE_THREE:
                fragment = stuClassHomeworkFragment;
                break;
            case StuClassMainActivity.PAGE_FOUR:
                fragment = stuClassInformFragment;
                break;
            case StuClassMainActivity.PAGE_FIVE:
                fragment = stuClassDetailFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
