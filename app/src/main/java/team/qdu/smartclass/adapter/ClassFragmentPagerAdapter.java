package team.qdu.smartclass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import team.qdu.smartclass.activity.ClassMainActivity;
import team.qdu.smartclass.fragment.ClassDetailFragment;
import team.qdu.smartclass.fragment.ClassHomeworkFragment;
import team.qdu.smartclass.fragment.ClassInformFragment;
import team.qdu.smartclass.fragment.ClassMaterialFragment;
import team.qdu.smartclass.fragment.ClassMemberFragment;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassFragmentPagerAdapter extends FragmentPagerAdapter {

    //页面数量
    private final int PAGER_COUNT = 5;
    private ClassMaterialFragment classMaterialFragment;
    private ClassMemberFragment classMemberFragment;
    private ClassHomeworkFragment classHomeworkFragment;
    private ClassInformFragment classInformFragment;
    private ClassDetailFragment classDetailFragment;

    public ClassFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        classMaterialFragment = new ClassMaterialFragment();
        classMemberFragment = new ClassMemberFragment();
        classHomeworkFragment = new ClassHomeworkFragment();
        classInformFragment = new ClassInformFragment();
        classDetailFragment = new ClassDetailFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ClassMainActivity.PAGE_ONE:
                fragment = classMaterialFragment;
                break;
            case ClassMainActivity.PAGE_TWO:
                fragment = classMemberFragment;
                break;
            case ClassMainActivity.PAGE_THREE:
                fragment = classHomeworkFragment;
                break;
            case ClassMainActivity.PAGE_FOUR:
                fragment = classInformFragment;
                break;
            case ClassMainActivity.PAGE_FIVE:
                fragment = classDetailFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
