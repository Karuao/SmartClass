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
import team.qdu.smartclass.activity.DoHomeworkActivity;
import team.qdu.smartclass.activity.ShowMyHomeworkActivity;
import team.qdu.smartclass.activity.StuClassMainActivity;
import team.qdu.smartclass.adapter.StuHomeworkUnderwayAdapter;
import team.qdu.smartclass.util.ButtonUtil;

/**
 * Created by 11602 on 2018/2/22.
 */

public class StuHomeworkUnderwayFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    private View currentPage;
    private ListView homeworkList;
    private StuClassMainActivity mContext;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03_underway, container, false);
        mContext = (StuClassMainActivity) getParentFragment().getActivity();
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
        homeworkList = (ListView) currentPage.findViewById(R.id.list_stu_underway);
    }

    private void initEvent() {
        homeworkList.setOnItemClickListener(this);
    }

    //homeworkList设置Adapter
    public void setHomeworkList() {
        mContext.homeworkAppAction.getHomeworkList(mContext.getClassId(), mContext.getUserId(),
                mContext.getUserTitle(), "进行中", new ActionCallbackListener<List>() {
                    @Override
                    public void onSuccess(List data, String message) {
                        homeworkList.setAdapter(new StuHomeworkUnderwayAdapter(mContext, data));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!ButtonUtil.isFastDoubleClick(view.getId())) {
            String homeworkAnswerId = ((TextView) view.findViewById(R.id.txt_homeworkanswer_underway_id)).getText().toString();
            String homeworkStatus = ((TextView) view.findViewById(R.id.txt_homework_underway_status)).getText().toString();
            Intent intent;
            if ("进行中".equals(homeworkStatus)) {
                intent = new Intent(getParentFragment().getActivity(), DoHomeworkActivity.class);

            } else {
                intent = new Intent(getParentFragment().getActivity(), ShowMyHomeworkActivity.class);
            }
            intent.putExtra("homeworkAnswerId", homeworkAnswerId);
            startActivity(intent);
        }
    }
}
