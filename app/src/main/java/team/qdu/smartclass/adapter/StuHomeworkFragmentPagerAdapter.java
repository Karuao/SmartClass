package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.fragment.TeaClassHomeworkFragment;
import team.qdu.smartclass.fragment.StuHomeworkFinishFragment;
import team.qdu.smartclass.fragment.StuHomeworkUnderwayFragment;

/**
 * Created by 11602 on 2018/2/22.
 */

public class StuHomeworkFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 2;
    private StuHomeworkUnderwayFragment stuHomeworkUnderwayFragment;
    private StuHomeworkFinishFragment stuHomeworkFinishFragment;

    public StuHomeworkFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        stuHomeworkUnderwayFragment = new StuHomeworkUnderwayFragment();
        stuHomeworkFinishFragment = new StuHomeworkFinishFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TeaClassHomeworkFragment.PAGE_ONE:
                fragment = stuHomeworkUnderwayFragment;
                break;
            case TeaClassHomeworkFragment.PAGE_TWO:
                fragment = stuHomeworkFinishFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
