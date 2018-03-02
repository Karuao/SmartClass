package team.qdu.smartclass.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.adapter.StuHomeworkFinishAdapter;

/**
 * Created by 11602 on 2018/2/22.
 */

public class StuHomeworkFinishFragment extends SBaseFragment {

    private View currentPage;
    private ListView homeworkList;
    private StuClassMainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03_finish, container, false);
        mContext = (StuClassMainActivity) getParentFragment().getActivity();
        initView();
        setHomeworkList();
        return currentPage;
    }

    private void initView() {
        homeworkList = (ListView) currentPage.findViewById(R.id.list_stu_finish);
    }

    //homeworkList设置Adapter
    public void setHomeworkList() {
        mContext.homeworkAppAction.getHomeworkList(mContext.getClassId(), mContext.getUserId(),
                mContext.getUserTitle(), "已结束", new ActionCallbackListener<List>() {
                    @Override
                    public void onSuccess(List data, String message) {
                        homeworkList.setAdapter(new StuHomeworkFinishAdapter(mContext, data));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
