package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.fragment.TeaClassHomeworkFragment;
import team.qdu.smartclass.fragment.TeaHomeworkFinishFragment;
import team.qdu.smartclass.fragment.TeaHomeworkUnderwayFragment;

/**
 * Created by 11602 on 2018/2/22.
 */

public class TeaHomeworkFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 2;
    private TeaHomeworkUnderwayFragment teaHomeworkUnderwayFragment;
    private TeaHomeworkFinishFragment teaHomeworkFinishFragment;

    public TeaHomeworkFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        teaHomeworkUnderwayFragment = new TeaHomeworkUnderwayFragment();
        teaHomeworkFinishFragment = new TeaHomeworkFinishFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case TeaClassHomeworkFragment.PAGE_ONE:
                fragment = teaHomeworkUnderwayFragment;
                break;
            case TeaClassHomeworkFragment.PAGE_TWO:
                fragment = teaHomeworkFinishFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
