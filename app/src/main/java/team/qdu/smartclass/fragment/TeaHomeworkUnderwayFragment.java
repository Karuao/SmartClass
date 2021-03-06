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
import team.qdu.smartclass.activity.TeaClassMainActivity;
import team.qdu.smartclass.activity.TeaShowEvaluatedHomeworkListActivity;
import team.qdu.smartclass.activity.TeaShowUnderwayHomeworkListActivity;
import team.qdu.smartclass.adapter.TeaHomeworkUnderwayAdapter;

/**
 * Created by 11602 on 2018/2/22.
 */

public class TeaHomeworkUnderwayFragment extends SBaseFragment implements AdapterView.OnItemClickListener {

    private boolean isPrepared;
    private View currentPage;
    private ListView homeworkList;
    private TeaClassMainActivity mContext;
    //刷新标志
    public static boolean refreshFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = inflater.inflate(R.layout.class_tab03_admin_underway, container, false);
        mContext = (TeaClassMainActivity) getParentFragment().getActivity();
        initView();
        initEvent();
//        setHomeworkList();
        isPrepared = true;
        lazyLoad();
        return currentPage;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }
//    }

    @Override
    protected void onVisible() {
    }

    //第一次加载之后该页面一直处于isVisible状态
//    成员 	false false
//    成员到作业	true  true 无
//    资源到作业	true  true 有
//    资源到成员	false false
//    成员到作业	false false无
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !((TeaClassHomeworkFragment)getParentFragment()).isVisible) {
            return;
        }
        setHomeworkList();
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
        homeworkList = (ListView) currentPage.findViewById(R.id.list_tea_underway);
    }

    private void initEvent() {
        homeworkList.setOnItemClickListener(this);
    }

    //homeworkList设置Adapter
    public void setHomeworkList() {
        mContext.homeworkAppAction.getHomeworkList(mContext.getClassId(), mContext.getUserId(),
                mContext.getUserTitle(), "进行中", this, new ActionCallbackListener<List>() {
                    @Override
                    public void onSuccess(List data, String message) {
                        homeworkList.setAdapter(new TeaHomeworkUnderwayAdapter(mContext, data));
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String homeworkId = ((TextView) view.findViewById(R.id.txt_homework_underway_id)).getText().toString();
        String homeworkStatus = ((TextView) view.findViewById(R.id.txt_homework_underway_status)).getText().toString();
        Intent intent;
        if ("进行中".equals(homeworkStatus)) {
            intent = new Intent(getParentFragment().getActivity(), TeaShowUnderwayHomeworkListActivity.class);
        } else {
            intent = new Intent(getParentFragment().getActivity(), TeaShowEvaluatedHomeworkListActivity.class);
        }
        intent.putExtra("homeworkId", homeworkId);
        startActivity(intent);
    }
}