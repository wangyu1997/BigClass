package com.njtech.bigclass;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.njtech.bigclass.adapter.ListCourseGroupItemAdapter;
import com.njtech.bigclass.entity.CourseEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

/**
 * Created by wangyu on 9/2/16.
 */

public class CourseSelectActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.course_group_list)
    ListView courseGroupList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private AppCompatActivity context;
    private List<CourseEntity.DataBean> dataList;
    private ListCourseGroupItemAdapter adapter;
    private int aid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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
        aid = getIntent().getIntExtra("aid", -1);
        setContentView(R.layout.activity_course_select);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataList = new ArrayList<>();
        Toast.makeText(context, "正在加载中...", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        getCourseList(aid);
    }


    public void getCourseList(int aid) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.getCoursesInfo(aid)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CourseEntity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CourseSelectActivity.this, "课程信息加载失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(CourseEntity courseEntity) {
                        if (!courseEntity.isError()) {
                            initCourseList(courseEntity.getData());
                        } else {
                            Toast.makeText(CourseSelectActivity.this, "课程信息加载失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void initCourseList(List<CourseEntity.DataBean> data) {
        adapter = new ListCourseGroupItemAdapter(this, data);
        courseGroupList.setAdapter(adapter);
    }
}
