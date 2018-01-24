package team.qdu.smartclass.fragment;

/**
 * 个人信息界面
 * Created by rjmgc on 2017/12/11.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.qdu.smartclass.R;

public class MainUserFragment extends SBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_user, container, false);
    }
}