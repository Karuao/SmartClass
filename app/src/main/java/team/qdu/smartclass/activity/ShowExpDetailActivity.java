package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import team.qdu.core.ActionCallbackListener;
import team.qdu.model.ClassUserExp;
import team.qdu.smartclass.R;
import team.qdu.smartclass.adapter.ExpDetailAdapter;
import team.qdu.smartclass.util.LoadingDialogUtil;

/**
 * Created by asus on 2018/4/5.
 */

public class ShowExpDetailActivity extends SBaseActivity{

    private ExpDetailAdapter expDetailAdapter;
    private ListView expDetail;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_member_expdetail_student);
        initView();
        initEvent();
        getExpDetailList();
    }

    public void initView(){
        expDetail = (ListView)findViewById(R.id.list_student_expDetail);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_student_exp);
    }

    private void initEvent() {
        expDetailAdapter = new ExpDetailAdapter(context);
        expDetail.setAdapter(expDetailAdapter);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorSecondary);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefreshLayout.setRefreshing(true);
                // TODO 获取数据
                getExpDetailList();
            }
        });
    }

    public void getExpDetailList(){
        Intent intent = getIntent();
        String classId = intent.getStringExtra("classId");
        String userId = intent.getStringExtra("userId");
        this.memberAppAction.getExpDetail(classId, userId, this,new ActionCallbackListener<List<ClassUserExp>>() {
            @Override
            public void onSuccess(List<ClassUserExp> data, String message) {
                expDetailAdapter.setItems(data);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
