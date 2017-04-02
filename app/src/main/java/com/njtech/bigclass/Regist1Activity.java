package com.njtech.bigclass;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.njtech.bigclass.PopUpWindow.Class_PopUp;
import com.njtech.bigclass.PopUpWindow.Sex_PopUp;
import com.njtech.bigclass.utils.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyu on 02/04/2017.
 */

public class Regist1Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_sex)
    EditText editSex;
    @BindView(R.id.edit_class)
    EditText editClass;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edit_academy)
    EditText editAcademy;
    private Class_PopUp class_popUp;
    private Sex_PopUp sex_popUp;
    public String classinfo;
    public String sexStr;
    public int sex;//男1 女2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
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
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        editClass.setKeyListener(null);
        editSex.setKeyListener(null);
    }

    @OnClick({R.id.edit_sex, R.id.edit_class, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_sex:
                sex_popUp = new Sex_PopUp(this, new PopClick());
                sex_popUp.showAtLocation(findViewById(R.id.regist1), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.edit_major:
                break;
            case R.id.edit_class:
                class_popUp = new Class_PopUp(this, new PopClick(), new valueChangeListener());
                classinfo = Class_PopUp.data[Class_PopUp.DEFAULT_VALUE];
                class_popUp.showAtLocation(this.findViewById(R.id.regist1), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_next:
                break;
        }
    }

    @OnClick(R.id.edit_academy)
    public void onClick() {
    }

    public class valueChangeListener implements NumberPicker.OnValueChangeListener {

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            classinfo = Class_PopUp.data[picker.getValue()];
        }
    }

    public class PopClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_cancel:
                    class_popUp.dismiss();
                    break;
                case R.id.pop_confirm:
                    if (classinfo != null) {
                        class_popUp.dismiss();
                        editClass.setText(classinfo);
                    }
                    break;
                case R.id.man:
                    sexStr = "男";
                    sex = 1;
                    editSex.setText(sexStr);
                    sex_popUp.dismiss();
                    break;
                case R.id.woman:
                    sexStr = "女";
                    sex = 2;
                    editSex.setText(sexStr);
                    sex_popUp.dismiss();
                    break;
            }
        }
    }
}


