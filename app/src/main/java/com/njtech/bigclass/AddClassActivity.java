package com.njtech.bigclass;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.njtech.bigclass.PopUpWindow.CourseNum_PopUp;
import com.njtech.bigclass.PopUpWindow.Day_PopUp;
import com.njtech.bigclass.PopUpWindow.Place_PopUp;
import com.njtech.bigclass.PopUpWindow.Week_PopUp;
import com.njtech.bigclass.entity.AddEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

public class AddClassActivity extends AppCompatActivity {
    @BindView(R.id.tv_academy)
    TextView tvAcademy;
    @BindView(R.id.tv_coursename)
    TextView tvCoursename;
    @BindView(R.id.tv_gpa)
    TextView tvGpa;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_building)
    TextView tvBuilding;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.teacher_name)
    TextView teacherName;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    public static final int Academy_res = 605;
    private static final int Academy_req = 635;
    public static final int Course_res = 601;
    private static final int Course_req = 632;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_room)
    EditText tvRoom;
    private String Academy_name;
    private int Academy_id;
    private int course_id;
    private String course_name;
    private String gpa;
    private WifiManager wifi;
    private String ssid;
    private String bssid;
    private Week_PopUp week_popUp;
    private Day_PopUp day_popUp;
    private String week_info;
    private String day_info;
    private CourseNum_PopUp courseNum_popUp1, courseNum_popUp2;
    private Place_PopUp place_popUp;
    private String coursenum1, coursenum2;
    private int course_1, course_2;
    private String building, room;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.course_create);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        Academy_id = -1;
        Academy_name = "";
        course_name = "";
        gpa = "";
        course_id = -1;
        ssid = "";
        bssid = "";
        week_info = "第1周";
        day_info = "周一";
        coursenum1 = "第1节";
        coursenum2 = "第2节";
        course_1 = 1;
        course_2 = 2;
        room = "";
        building = "";
    }

    @OnClick({R.id.tv_academy, R.id.tv_coursename, R.id.tv_gpa, R.id.tv_wifi, R.id.tv_week, R.id.tv_day, R.id.tv_num, R.id.tv_building, R.id.tv_room, R.id.tv_content, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_academy:
                Intent intent = new Intent(this, AcademySelectActivity.class);
                intent.putExtra("flag", 3);
                startActivityForResult(intent, Academy_req);
                break;
            case R.id.tv_coursename:
                if (Academy_id == -1) {
                    Toast.makeText(this, "请先选择学院", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(this, CourseSelectActivity.class);
                    intent1.putExtra("aid", Academy_id);
                    startActivityForResult(intent1, Course_req);
                }
                break;
            case R.id.tv_gpa:
                break;
            case R.id.tv_wifi:
                getWifi();
                break;
            case R.id.tv_week:
                week_popUp = new Week_PopUp(this, new WeekClick(), new weekListener());
                week_info = Week_PopUp.data[Week_PopUp.DEFAULT_VALUE];
                week_popUp.showAtLocation(this.findViewById(R.id.class_add), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_day:
                day_popUp = new Day_PopUp(this, new DayClick(), new dayListener());
                day_info = Day_PopUp.data[Day_PopUp.DEFAULT_VALUE];
                day_popUp.showAtLocation(this.findViewById(R.id.class_add), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_num:
                courseNum_popUp1 = new CourseNum_PopUp(true, this, new course1Click(), new course1Listener());
                coursenum1 = CourseNum_PopUp.data[CourseNum_PopUp.DEFAULT_VALUE];
                courseNum_popUp1.showAtLocation(this.findViewById(R.id.class_add), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_building:
                place_popUp = new Place_PopUp(this, new PlaceClick(), new placeListener());
                building = Place_PopUp.data[Place_PopUp.DEFAULT_VALUE];
                place_popUp.showAtLocation(this.findViewById(R.id.class_add), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_content:
                break;
            case R.id.btn_create:
                create();
                break;
        }
    }


    public void create() {
        if (Academy_id == -1 || Academy_name.equals("")) {
            Toast.makeText(this, "请选择学院", Toast.LENGTH_SHORT).show();
            return;
        }
        if (course_name.equals("") || course_id == -1) {
            Toast.makeText(this, "请选择课程", Toast.LENGTH_SHORT).show();
            return;
        }
        if (building.equals("教学楼")) {
            Toast.makeText(this, "请选择教学楼", Toast.LENGTH_SHORT).show();
            return;
        }
        String room = tvRoom.getText().toString().trim();
        if (room.equals("")) {
            Toast.makeText(this, "请输入教室", Toast.LENGTH_SHORT).show();
            return;
        }
        if (room.length() > 4) {
            Toast.makeText(this, "教室格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        if (ssid.isEmpty() && bssid.isEmpty()) {
            map.put("wifi", ssid);
            map.put("bssid", bssid);
        }
        map.put("cid", course_id);
        String time = week_info + " " + day_info + " " + course_1 + "/" + course_2;
        map.put("time", time);
        map.put("content", "这是测试课程");
        map.put("place", building + room);
        addCourse(map);
    }

    public void addCourse(Map<String, Object> map) {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "正在添加课程，请稍后...", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.addCourse(map)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddEntity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddClassActivity.this, "添加课程失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(AddEntity addEntity) {
                        if (addEntity.isError()) {
                            Toast.makeText(AddClassActivity.this, "添加课程失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddClassActivity.this, "添加课程成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    public void getWifi() {
        Toast.makeText(this, "正在搜寻wifi,请稍后", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_DISABLING) {
            Toast.makeText(this, "请打开wifi并连接后再点击", Toast.LENGTH_SHORT).show();
        } else {
            if (info.getNetworkId() == -1) {
                Toast.makeText(this, "请连接wifi后再点击", Toast.LENGTH_SHORT).show();
            } else {
                ssid = info.getSSID();
                bssid = info.getBSSID();
                ssid = ssid.substring(1, ssid.length() - 1);
                tvWifi.setText(ssid);
            }
        }
        progressBar.setVisibility(View.GONE);
    }

    public class weekListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            week_info = Week_PopUp.data[picker.getValue()];
        }
    }

    public class WeekClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    week_popUp.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (week_info != null) {
                        week_popUp.dismiss();
                        tvWeek.setText(week_info);
                    }
                    break;

            }
        }
    }


    public class dayListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            day_info = Day_PopUp.data[picker.getValue()];
        }
    }

    public class DayClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    day_popUp.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (day_info != null) {
                        day_popUp.dismiss();
                        tvDay.setText(day_info);
                    }
                    break;

            }
        }
    }

    public class placeListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            building = Place_PopUp.data[picker.getValue()];
        }
    }

    public class PlaceClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    place_popUp.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (day_info != null) {
                        place_popUp.dismiss();
                        tvBuilding.setText(building);
                    }
                    break;

            }
        }
    }


    public class course1Listener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            coursenum1 = CourseNum_PopUp.data[picker.getValue()];
            course_1 = Integer.parseInt(coursenum1.substring(1, coursenum1.length() - 1));
        }
    }

    public class course1Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    courseNum_popUp1.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (day_info != null) {
                        courseNum_popUp1.dismiss();
                        courseNum_popUp2 = new CourseNum_PopUp(false, AddClassActivity.this, new course2Click(), new course2Listener());
                        coursenum2 = CourseNum_PopUp.data[CourseNum_PopUp.DEFAULT_VALUE];
                        courseNum_popUp2.showAtLocation(AddClassActivity.this.findViewById(R.id.class_add), Gravity.BOTTOM, 0, 0);
                    }
                    break;

            }
        }
    }

    public class course2Listener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            coursenum2 = CourseNum_PopUp.data[picker.getValue()];
            course_2 = Integer.parseInt(coursenum2.substring(1, coursenum2.length() - 1));
        }
    }

    public class course2Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    coursenum1 = "第1节";
                    course_1 = 1;
                    coursenum2 = "第2节";
                    course_2 = 2;
                    courseNum_popUp2.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (day_info != null) {
                        if (course_2 < course_1) {
                            Toast.makeText(AddClassActivity.this, "结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
                        } else {
                            courseNum_popUp2.dismiss();
                            tvNum.setText(coursenum1 + "到" + coursenum2);
                        }
                    }
                    break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Academy_req && resultCode == Academy_res) {
            Academy_name = data.getStringExtra("academy_name");
            Academy_id = data.getIntExtra("academy_id", -1);
            if (Academy_id == -1) {
                Academy_name = "";
            } else {
                tvAcademy.setText(Academy_name);
            }
            course_id = -1;
            course_name = "";
            gpa = "";
            tvCoursename.setText("");
            tvGpa.setText("");
        }
        if (requestCode == Course_req && resultCode == Course_res) {
            course_name = data.getStringExtra("course_name");
            course_id = data.getIntExtra("course_id", -1);
            gpa = data.getStringExtra("course_gpa");
            if (course_id == -1) {
                course_name = "";
                gpa = "";
            } else {
                tvCoursename.setText(course_name);
                tvGpa.setText(gpa);
            }
        }
    }
}

