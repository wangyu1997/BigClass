package com.njtech.bigclass;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.njtech.bigclass.PopUpWindow.Clear_PopUp;
import com.njtech.bigclass.PopUpWindow.CourseNum_PopUp;
import com.njtech.bigclass.PopUpWindow.Day_PopUp;
import com.njtech.bigclass.PopUpWindow.Place_PopUp;
import com.njtech.bigclass.PopUpWindow.State_PopUp;
import com.njtech.bigclass.PopUpWindow.Week_PopUp;
import com.njtech.bigclass.entity.ArrayEntity;
import com.njtech.bigclass.entity.Info_entity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;

import java.util.HashMap;
import java.util.Map;

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

public class UpdateClassActivity extends AppCompatActivity {

    public static final int Academy_res = 605;
    private static final int Academy_req = 635;
    public static final int Course_res = 601;
    private static final int Course_req = 632;
    @BindView(R.id.tv_state)
    TextView tvState;
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
    @BindView(R.id.tv_room)
    EditText tvRoom;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String course_name;
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
    private Info_entity.DataBean dataBean;
    private String state;
    private String time, place;
    private int cid;
    private String rawstate, raw_wifi, raw_time, raw_place, raw_content;
    private State_PopUp state_popUp;
    private String content;
    private Clear_PopUp clear_popUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.course_update);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        hideInput(this, tvRoom);
        dataBean = (Info_entity.DataBean) getIntent().getBundleExtra("bundle").getSerializable("data");
        cid = Integer.parseInt(dataBean.getId());
        course_name = dataBean.getC_name();
        raw_wifi = dataBean.getWifi();
        raw_time = dataBean.getTime();
        raw_place = dataBean.getPlace();
        raw_content = dataBean.getContent();
        String[] info = dataBean.getTime().split(" ");
        String week_info = info[0];
        String day_info = info[1];
        int course_1 = Integer.parseInt(info[2].split("/")[0]);
        int course_2 = Integer.parseInt(info[2].split("/")[1]);
        String coursenum1 = "第" + course_1 + "节";
        String coursenum2 = "第" + course_2 + "节";
        String[] placeInfo = dataBean.getPlace().split(" ");
        String building = placeInfo[0];
        String room = dataBean.getPlace().substring(dataBean.getPlace().indexOf(building) + building.length()+1);
        courseName.setText(course_name);
        rawstate = dataBean.getState();
        if (dataBean.getState().equals("0")) {
            tvState.setText("未开始");
        } else if (dataBean.getState().equals("1")) {
            tvState.setText("开始上课");
        } else if (dataBean.getState().equals("2")) {
            tvState.setText("结束课程");
        }
        raw_content = dataBean.getContent();
        tvWeek.setText(week_info);
        tvDay.setText(day_info);
        tvNum.setText(coursenum1 + "到" + coursenum2);
        tvBuilding.setText(building);
        tvRoom.setText(room);
        tvWifi.setText(raw_wifi);
        tvContent.setText(raw_content);
        this.course_name = "";
        state = "";
        ssid = "暂未指定";
        bssid = "-1";
        this.week_info = "";
        this.day_info = "";
        this.coursenum1 = "";
        this.coursenum2 = "";
        this.course_1 = -1;
        this.course_2 = -1;
        this.room = "";
        this.building = "";
        this.time = "";
        this.place = "";
        content = "";
        tvWifi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clear_popUp = new Clear_PopUp(UpdateClassActivity.this, new UpdateClassActivity.ClearClick());
                clear_popUp.showAtLocation(UpdateClassActivity.this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
                return false;
            }
        });
    }

    public class ClearClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comfirm_btn:
                    ssid = "";
                    bssid = "";
                    tvWifi.setText("");
                    break;
            }
            clear_popUp.dismiss();
        }
    }

    @OnClick({R.id.tv_wifi, R.id.tv_week, R.id.tv_day, R.id.tv_num, R.id.tv_building, R.id.tv_room, R.id.tv_content, R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wifi:
                getWifi();
                break;
            case R.id.tv_week:
                week_popUp = new Week_PopUp(this, new WeekClick(), new weekListener());
                week_info = Week_PopUp.data[Week_PopUp.DEFAULT_VALUE];
                week_popUp.showAtLocation(this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_day:
                day_popUp = new Day_PopUp(this, new DayClick(), new dayListener());
                day_info = Day_PopUp.data[Day_PopUp.DEFAULT_VALUE];
                day_popUp.showAtLocation(this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_num:
                courseNum_popUp1 = new CourseNum_PopUp(true, this, new course1Click(), new course1Listener());
                coursenum1 = CourseNum_PopUp.data[CourseNum_PopUp.DEFAULT_VALUE];
                courseNum_popUp1.showAtLocation(this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_building:
                place_popUp = new Place_PopUp(this, new PlaceClick(), new placeListener());
                building = Place_PopUp.data[Place_PopUp.DEFAULT_VALUE];
                place_popUp.showAtLocation(this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_content:
                break;
            case R.id.btn_create:
                create();
                break;
        }
    }


    public void create() {
        room = tvRoom.getText().toString().trim();
        if (room.equals("")) {
            Toast.makeText(this, "请输入教室", Toast.LENGTH_SHORT).show();
            return;
        }
        if (room.length() > 4 || room.length() < 1) {
            Toast.makeText(this, "教室格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isChange()) {
            Toast.makeText(this, "没有改动信息,请直接返回上一页", Toast.LENGTH_SHORT).show();
            return;
        } else {
            update(getMap());
        }
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

    @OnClick(R.id.tv_state)
    public void onViewClicked() {
        String state = tvState.getText().toString();
        state_popUp = new State_PopUp(UpdateClassActivity.this, new Pop_onclick());
        if (state.equals("未开始"))
            state_popUp.disableReset();
        if (state.equals("开始上课"))
            state_popUp.disableStart();
        if (state.equals("结束课程"))
            state_popUp.disableEnd();
        state_popUp.showAtLocation(findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);

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


    public class Pop_onclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reset_btn:
                    state = "0";
                    tvState.setText("未开始");
                    break;
                case R.id.start_btn:
                    state = "1";
                    tvState.setText("开始上课");
                    break;
                case R.id.end_btn:
                    state = "2";
                    tvState.setText("结束课程");
                    break;
            }
            state_popUp.dismiss();
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
                        courseNum_popUp2 = new CourseNum_PopUp(false, UpdateClassActivity.this, new course2Click(), new course2Listener());
                        coursenum2 = CourseNum_PopUp.data[CourseNum_PopUp.DEFAULT_VALUE];
                        courseNum_popUp2.showAtLocation(UpdateClassActivity.this.findViewById(R.id.class_update), Gravity.BOTTOM, 0, 0);
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
                            Toast.makeText(UpdateClassActivity.this, "结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
                        } else {
                            courseNum_popUp2.dismiss();
                            tvNum.setText(coursenum1 + "到" + coursenum2);
                        }
                    }
                    break;

            }
        }
    }


    public void update(Map<String, Object> map) {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "正在修改,请稍后...", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.updateCourse(map)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayEntity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UpdateClassActivity.this, "修改失败请稍后再试...", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }

                    @Override
                    public void onNext(ArrayEntity arrayEntity) {
                        if (arrayEntity.isError()) {
                            Toast.makeText(UpdateClassActivity.this, "修改失败请稍后再试...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateClassActivity.this, "修改成功，正在刷新信息", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 强制隐藏输入法键盘
     */
    private void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isChange() {
        if (week_info.isEmpty() || day_info.isEmpty() || course_1 == -1 || course_2 == -1)
            time = "";
        else
            time = week_info + " " + day_info + " " + course_1 + "/" + course_2;
        if (building.isEmpty() || room.isEmpty()) {
            place = "";
        } else
            place = building + " " + room;
        if ((state.isEmpty() || state.equals(rawstate)) && (ssid.equals(raw_wifi)) && (time.isEmpty() || time.equals(raw_time)) && (place.isEmpty() || place.equals(raw_place)) && (content.isEmpty() || content.equals(raw_content)))
            return false;
        return true;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", cid);
        if (!(state.isEmpty() || state.equals(rawstate)))
            map.put("state", state);
        if (!(ssid.equals(raw_wifi))) {
            if (ssid.isEmpty() || bssid.isEmpty()) {
                map.put("wifi", "暂未指定");
                map.put("bssid", "-1");
            } else {
                map.put("wifi", ssid);
                map.put("bssid", bssid);
            }
        }
        if (!(time.isEmpty() || time.equals(raw_time)))
            map.put("time", time);
        if (!(place.isEmpty() || place.equals(raw_place)))
            map.put("place", place);
        if (!(content.isEmpty() || content.equals(raw_content)))
            map.put("content", content);
        return map;
    }

}

