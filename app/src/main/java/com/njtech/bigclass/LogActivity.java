package com.njtech.bigclass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.njtech.bigclass.utils.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogActivity extends AppCompatActivity {


    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);

    }


    @OnClick({R.id.tv_forget, R.id.btn_regist, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                break;
            case R.id.btn_regist:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
