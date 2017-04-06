package com.njtech.bigclass;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.njtech.bigclass.entity.Info_entity;
import com.njtech.bigclass.entity.ObjEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

/**
 * Created by wangyu on 06/04/2017.
 */

public class CourseInfoActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.teacher_head)
    SimpleDraweeView teacherHead;
    @BindView(R.id.teacher_name)
    TextView teacherName;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_create)
    TextView tvCreate;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.stu_head1)
    SimpleDraweeView stuHead1;
    @BindView(R.id.stu_head2)
    SimpleDraweeView stuHead2;
    @BindView(R.id.stu_head3)
    SimpleDraweeView stuHead3;
    @BindView(R.id.headers)
    LinearLayout headers;
    @BindView(R.id.tv_gpa)
    TextView tvGpa;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.sign_layout)
    LinearLayout signLayout;
    @BindView(R.id.course_content)
    TextView courseContent;
    @BindView(R.id.join_layout)
    LinearLayout joinLayout;
    @BindView(R.id.sign_btn)
    Button signBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.tv_academy)
    TextView tvAcademy;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private boolean isMine;
    private Info_entity.DataBean info_Data;
    private int cid = -1;
    private static final int courseInfoMsg = 555;
    private int number = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.course_info);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        isMine = false;
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        progressBar.setVisibility(View.VISIBLE);
        cid = Integer.parseInt(getIntent().getStringExtra("cid"));
        refresh.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                Toast.makeText(CourseInfoActivity.this, "正在加载中，请稍后...", Toast.LENGTH_SHORT).show();
                check(cid);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh.setRefreshing(true);
        check(cid);
        Toast.makeText(this, "正在加载中，请稍后...", Toast.LENGTH_SHORT).show();
    }

    public void check(final int cid) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.checkCourses(cid)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ObjEntity>() {
                    @Override
                    public void onCompleted() {
                        getInfo(cid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CourseInfoActivity.this, "获取权限失败,正在加载课程详情", Toast.LENGTH_SHORT).show();
                        getInfo(cid);
                    }

                    @Override
                    public void onNext(ObjEntity objEntity) {
                        if (objEntity.isError()) {
                            Toast.makeText(CourseInfoActivity.this, "获取权限失败:" + objEntity.getMsg() + ",正在加载课程详情", Toast.LENGTH_SHORT).show();
                        } else {
                            if (objEntity.getMsg().equals("yes"))
                                isMine = true;
                            else
                                isMine = false;
                        }
                    }
                });
    }

    public void getInfo(int cid) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.courseInfo(cid)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Info_entity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CourseInfoActivity.this, "获取课程详情失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(Info_entity info_entity) {
                        if (info_entity.isError()) {
                            Toast.makeText(CourseInfoActivity.this, "获取课程详情失败:" + info_entity.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Message msg = handler.obtainMessage();
                            msg.what = courseInfoMsg;
                            msg.obj = info_entity.getData();
                            msg.sendToTarget();
                        }
                    }
                });
    }

    public void sign(int cid) {
        String action;
        if (signBtn.getText().toString().equals("发起签到")) {
            action = "start";
        } else {
            action = "end";
        }
        progressBar.setVisibility(View.VISIBLE);
        signBtn.setEnabled(false);
        Toast.makeText(this, "正在" + signBtn.getText().toString() + ",请稍后...", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.sign(cid, action)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ObjEntity>() {
                    @Override
                    public void onCompleted() {
                        signBtn.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CourseInfoActivity.this, signBtn.getText().toString() + "失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        signBtn.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ObjEntity objEntity) {
                        if (objEntity.isError()) {
                            Toast.makeText(CourseInfoActivity.this, signBtn.getText().toString() + "失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CourseInfoActivity.this, signBtn.getText().toString() + "成功", Toast.LENGTH_SHORT).show();
                            if (signBtn.getText().toString().equals("发起签到"))
                                signBtn.setText("结束签到");
                            else
                                signBtn.setText("发起签到");
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_action) {
            Toast.makeText(this, "功能键", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case courseInfoMsg:
                    info_Data = (Info_entity.DataBean) msg.obj;
                    initInterface(info_Data);
                    break;
            }
        }
    };

    public void initInterface(Info_entity.DataBean dataBean) {
        if (isMine)
            signLayout.setVisibility(View.VISIBLE);
        else
            signLayout.setVisibility(View.GONE);
        courseName.setText(dataBean.getC_name());
        String tHead = dataBean.getHeader();
        if (tHead != null) {
            Uri tUri = Uri.parse(tHead);
            teacherHead.setImageURI(tUri);
        }
        teacherName.setText(dataBean.getTeacher());
        tvWifi.setText(dataBean.getWifi());
        tvTime.setText(dataBean.getTime());
        tvCreate.setText(dataBean.getCreateTime().split(" ")[0]);
        tvNum.setText(dataBean.getNumber());
        String stuheads = dataBean.getHeader_con();
        if (stuheads == null || stuheads.isEmpty()) {
            headers.setVisibility(View.GONE);
        } else {
            String[] heads = stuheads.split(";");
            if (heads.length == 1) {
                stuHead1.setImageURI(Uri.parse(heads[0]));
                stuHead2.setVisibility(View.GONE);
                stuHead3.setVisibility(View.GONE);
            } else if (heads.length == 2) {
                stuHead1.setImageURI(Uri.parse(heads[0]));
                stuHead2.setImageURI(Uri.parse(heads[1]));
                stuHead3.setVisibility(View.GONE);
            } else {
                stuHead1.setImageURI(Uri.parse(heads[0]));
                stuHead2.setImageURI(Uri.parse(heads[1]));
                stuHead3.setImageURI(Uri.parse(heads[2]));
            }
        }
        tvGpa.setText(dataBean.getGpa());
        tvPlace.setText(dataBean.getPlace());
        int state = Integer.parseInt(dataBean.getState());
        number = Integer.parseInt(dataBean.getNumber());
        String course_state = "";
        switch (state) {
            case 0:
                course_state = "未开始";
                break;
            case 1:
                course_state = "上课中";
                break;
            case 2:
                course_state = "已结束";
                break;
        }
        tvState.setText(course_state);
        tvAcademy.setText(dataBean.getA_name().substring(0, dataBean.getA_name().indexOf("学院")));
        courseContent.setText(dataBean.getContent());
        int flag = Integer.parseInt(dataBean.getSignFlag());
        if (flag == 0 || flag == 2) {
            signBtn.setText("发起签到");
        } else if (flag == 1) {
            signBtn.setText("结束签到");
        }
    }

    @OnClick({R.id.join_layout, R.id.sign_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.join_layout:
                if (number>0)
                    Toast.makeText(this, "签到记录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sign_btn:
                sign(cid);
                break;
        }
    }
}
