package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team.qdu.smartclass.R;

/**
 * Created by 11602 on 2017/10/30.
 */

public class MainFragment extends SBaseFragment {

    String content;

    public MainFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ((TextView) view.findViewById(R.id.txt_content)).setText(content);
        return view;
    }
}
