package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.qdu.smartclass.R;

/**
 * Created by rjmgc on 2018/1/17.
 */

public class TeaClassMaterialFragment extends SBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Teacher Material");
        return inflater.inflate(R.layout.class_tab01_admin, container, false);
    }
}