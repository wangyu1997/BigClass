package com.njtech.bigclass;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.njtech.bigclass.entity.RegistVerEntity;
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
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edit_academy)
    EditText editAcademy;
    @BindView(R.id.im_male)
    ImageButton imMale;
    @BindView(R.id.im_female)
    ImageButton imFemale;
    private RegistVerEntity registVerEntity;
    private static final int academy_req = 581;
    public static final int academy_res = 100;
    public String academy_name;
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
        registVerEntity = new RegistVerEntity();
        selectSex(1);
        editAcademy.setKeyListener(null);
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                registVerEntity.setUsername(editUser.getText().toString());
                registVerEntity.setName(editName.getText().toString());
                if (registVerEntity.isLeagel1()) {
                    Intent intent = new Intent(Regist1Activity.this, Regist2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("regist", registVerEntity);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
                }
                break;
        }
    }

    @OnClick(R.id.edit_academy)
    public void onClick() {
        Intent intent = new Intent(this, AcademySelectActivity.class);
        startActivityForResult(intent, academy_req);
    }

    @OnClick({R.id.im_male, R.id.im_female})
    public void selectSex(View view) {
        switch (view.getId()) {
            case R.id.im_male:
                selectSex(1);
                break;
            case R.id.im_female:
                selectSex(2);
                break;
        }
    }


    public void selectSex(int flag) {
        imFemale.setImageDrawable(getResources().getDrawable(R.mipmap.femalehide));
        imMale.setImageDrawable(getResources().getDrawable(R.mipmap.malehide));
        if (flag == 1) {
            registVerEntity.setSex(1);
            imMale.setImageDrawable(getResources().getDrawable(R.mipmap.maleshow));
        }
        if (flag == 2) {
            registVerEntity.setSex(2);
            imFemale.setImageDrawable(getResources().getDrawable(R.mipmap.femaleshow));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == academy_req) && (resultCode == academy_res)) {
            academy_name = data.getStringExtra("academy_name");
            int academy_id = data.getIntExtra("academy_id", -1);
            editAcademy.setText(academy_name);
            registVerEntity.setAid(academy_id);
        }
    }
}


