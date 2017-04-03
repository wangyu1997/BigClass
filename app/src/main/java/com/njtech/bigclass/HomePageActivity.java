package com.njtech.bigclass;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.njtech.bigclass.adapter.ListCourseItemAdapter;
import com.njtech.bigclass.entity.courseShowEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_first)
    LinearLayout layoutFirst;
    @BindView(R.id.layout_second)
    LinearLayout layoutSecond;
    @BindView(R.id.layout_third)
    LinearLayout layoutThird;
    @BindView(R.id.course_list)
    RecyclerView courseList;
    @BindView(R.id.id_swipe_ly)
    SwipeRefreshLayout idSwipeLy;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ListCourseItemAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private int aid;
    private String aname;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);
        }
        AppManager.getAppManager().addActivity(this);


        init();
    }

    private void init() {
        //TODO SET the first activity selected
        layoutFirst.setBackground(getResources().getDrawable(R.color.amber_50));
        manager = new LinearLayoutManager(this);
        courseList.setLayoutManager(manager);
        adapter = new ListCourseItemAdapter(HomePageActivity.this, new ArrayList<courseShowEntity.DataBean>());
        courseList.setAdapter(adapter);
        data = getIntent();
        aid = Integer.parseInt(data.getStringExtra("aid"));
        aname = data.getStringExtra("aname");
        tvTitle.setText(aname);

        idSwipeLy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        idSwipeLy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                getCourse(aid);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        idSwipeLy.setRefreshing(true);
        getCourse(aid);
    }

    @OnClick({R.id.layout_second, R.id.layout_third})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_second:
                startActivity(new Intent(this, InfoActivity.class));
                break;
            case R.id.layout_third:
                startActivity(new Intent(this, PersonActivity.class));
                break;
        }
    }

    public void getCourse(int aid) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.getCourses(aid)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<courseShowEntity>() {
                    @Override
                    public void onCompleted() {
                        idSwipeLy.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(HomePageActivity.this, "获取课程信息失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        idSwipeLy.setRefreshing(false);
                    }

                    @Override
                    public void onNext(courseShowEntity courseShowEntity) {
                        if (courseShowEntity.isError()) {
                            Toast.makeText(HomePageActivity.this, "获取课程信息失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        } else {
                            List<com.njtech.bigclass.entity.courseShowEntity.DataBean> datas = courseShowEntity.getData();
                            if (datas == null || datas.size() == 0)
                                datas = new ArrayList<>();
                            adapter.setData(datas);
                        }
                    }
                });
    }


    @OnClick(R.id.id_swipe_ly)
    public void onClick() {
    }

    @OnClick(R.id.tv_title)
    public void onViewClicked() {
    }
}
