package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.activity.MainActivity;
import team.qdu.smartclass.fragment.MainClassFragment;
import team.qdu.smartclass.fragment.MainUserFragment;

/**
 * Created by Rock on 2018/1/24.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 2;
    private MainClassFragment mainClassFragment;
    private MainUserFragment mainUserFragment;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mainClassFragment = new MainClassFragment();
        mainUserFragment = new MainUserFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = mainClassFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = mainUserFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
