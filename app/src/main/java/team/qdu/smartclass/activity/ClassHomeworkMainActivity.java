package team.qdu.smartclass.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.qdu.smartclass.R;

/**
 * 作业主页面的activity
 * <p>
 * Created by Rock on 2017/4/23.
 */

public class ClassHomeworkMainActivity extends SBaseActivity implements View.OnClickListener {


    private ViewPager viewPager;

    private PagerAdapter mAdapter;
    private List<View> views = new ArrayList<>();
    //tab
    private LinearLayout tabUnderway;
    private LinearLayout tabFinish;

    private TextView tvUnderway;
    private TextView tvFinish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_tab03);
        initView();

        initEvents();

    }

    private void initEvents() {
        //点击效果
        tabUnderway.setOnClickListener(this);
        tabFinish.setOnClickListener(this);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                resetImg();
                switch (currentItem) {
                    case 0:
                        tvUnderway.setTextColor(getColor(R.color.classbottom));
                        break;
                    case 1:
                        tvFinish.setTextColor(getColor(R.color.classbottom));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化View
    private void initView() {


        //页面切换
        viewPager = (ViewPager) findViewById(R.id.class_tab03_viewpager);

        tabUnderway= (LinearLayout) findViewById(R.id.ll_class_homework_underway);
        tabFinish = (LinearLayout) findViewById(R.id.ll_class_homework_finish);


        tvUnderway = (TextView) findViewById(R.id.tv_class_homework_underway);
        tvFinish = (TextView) findViewById(R.id.tv_class_homework_finish);


        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.class_tab03_underway, null);
        View tab02 = mInflater.inflate(R.layout.class_tab03_finish, null);

        views.add(tab01);
        views.add(tab02);


        mAdapter = new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));

            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        viewPager.setAdapter(mAdapter);

        //
    }


    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_class_homework_underway:
                tvUnderway.setTextColor(getColor(R.color.classbottom));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_class_homework_finish:
                tvFinish.setTextColor(getColor(R.color.classbottom));
                viewPager.setCurrentItem(1);
                break;

        }
    }

    //切换字体颜色
    private void resetImg() {

        tvUnderway.setTextColor(getColor(R.color.hinter));
        tvFinish.setTextColor(getColor(R.color.hinter));

    }



}

