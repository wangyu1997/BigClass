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

    @BindView(R.id.edit_user)
    EditText editUser;
    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgetpd)
    TextView tvForgetpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);

    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forgetpd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_forgetpd:
                break;
        }
    }
}
