package team.qdu.smartclass.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.smartclass.R;
import team.qdu.smartclass.activity.ShowFinishHomeworkActivity;
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.adapter.TeaHomeworkFinishAdapter;

/**
 * Created by 11602 on 2018/2/22.
 */

public class TeaHomeworkFinishFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    private View currentPage;
    private ListView homeworkList;
    private TeaClassMainActivity mContext;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03_admin_finish, container, false);
        mContext = (TeaClassMainActivity) getParentFragment().getActivity();
        initView();
        setHomeworkList();
        initEvent();
        return currentPage;
    }

    //页面从后台返回到前台运行
    @Override
    public void onResume() {
        super.onResume();
        if (refreshFlag) {
            setHomeworkList();
            refreshFlag = false;
        }
    }

    private void initView() {
        homeworkList = (ListView) currentPage.findViewById(R.id.list_tea_finish);
    }

    private void initEvent() {
        homeworkList.setOnItemClickListener(this);
    }

    //homeworkList设置Adapter
    public void setHomeworkList() {
        mContext.homeworkAppAction.getHomeworkList(mContext.getClassId(), mContext.getUserId(),
                mContext.getUserTitle(), "已结束", new ActionCallbackListener<List>() {
                    @Override
                    public void onSuccess(List data, String message) {
                        homeworkList.setAdapter(new TeaHomeworkFinishAdapter(mContext, data));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String homeworkId = ((TextView) view.findViewById(R.id.txt_homework_finish_id)).getText().toString();
        Intent intent = new Intent(getParentFragment().getActivity(), ShowFinishHomeworkActivity.class);
        intent.putExtra("homeworkId", homeworkId);
        startActivity(intent);
    }
}
