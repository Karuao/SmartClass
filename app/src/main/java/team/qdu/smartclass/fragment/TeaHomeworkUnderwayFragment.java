package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.Homework;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.TeaHomeworkAdapter;

/**
 * Created by 11602 on 2018/2/22.
 */

public class TeaHomeworkUnderwayFragment extends SBaseFragment {

    private View currentPage;
    private ListView homeworkList;
    private TeaClassMainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03_admin_underway, container, false);
        mContext = (TeaClassMainActivity) getParentFragment().getActivity();
        initView();
        setHomeworkList();
        return currentPage;
    }

    private void initView() {
        homeworkList = (ListView) currentPage.findViewById(R.id.list_tea_underway);
    }

    //homeworkList设置Adapter
    public void setHomeworkList() {
        mContext.homeworkAppAction.getHomeworkList(mContext.getClassId(), mContext.getUserId(),
                mContext.getUserTitle(), "进行中", new ActionCallbackListener<List<Homework>>() {
                    @Override
                    public void onSuccess(List<Homework> data, String message) {
                        homeworkList.setAdapter(new TeaHomeworkAdapter(mContext, data));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
