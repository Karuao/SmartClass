package team.qdu.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import team.qdu.smartclass.R;

/**
 * 主页
 *
 * Created by Rock on 2017/4/23.
 */

public class MainActivity extends SBaseActivity implements View.OnClickListener {


    private ViewPager viewPager;

    private PagerAdapter mAdapter;
    private List<View> Views=new ArrayList<>();
    //tab
    private LinearLayout tabClass;
    private LinearLayout tabUser;
    private LinearLayout personal_top;
    private ImageButton imgClass;
    private ImageButton imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        initView();

        initEvents();
    }

    private void initEvents() {
        //点击效果
        tabClass.setOnClickListener(this);
        tabUser.setOnClickListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                resetImg();
                switch (currentItem){
                    case 0:
                        imgClass.setImageResource(R.drawable.class_select);
                        break;
                    case 1:
                        imgUser.setImageResource(R.drawable.user_select);
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
        viewPager =(ViewPager) findViewById(R.id.viewpager);
        tabClass =(LinearLayout) findViewById(R.id.tab01);
        tabUser =(LinearLayout) findViewById(R.id.tab02);

        imgClass =(ImageButton) findViewById(R.id.class_img);
        imgUser =(ImageButton) findViewById(R.id.user_img);

        LayoutInflater mInflater=LayoutInflater.from(this);
        View tab01=mInflater.inflate(R.layout.main_tab_01,null);
        View tab02=mInflater.inflate(R.layout.main_tab_02,null);

        Views.add(tab01);
        Views.add(tab02);

        mAdapter=new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=Views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(Views.get(position));

            }

            @Override
            public int getCount() {
                return Views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        };

        viewPager.setAdapter(mAdapter);

        //
    }



    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()){
            case R.id.tab01:
                imgClass.setImageResource(R.drawable.class_select);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab02:
                imgUser.setImageResource(R.drawable.user_select);
                viewPager.setCurrentItem(1);
                break;
        }
    }
    //切换图片颜色
    private void resetImg() {

        imgClass.setImageResource(R.drawable.class_notselect);
        imgUser.setImageResource(R.drawable.user_notselect);
    }

    public void toChangeInfo(View view) {
        startActivity(new Intent(MainActivity.this, ChangeInfoActivity.class));
    }

    public void toSet(View view) {
        startActivity(new Intent(MainActivity.this, SetActivity.class));
    }
}
