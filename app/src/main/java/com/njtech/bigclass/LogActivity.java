package com.njtech.bigclass;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.njtech.bigclass.entity.DataBean;
import com.njtech.bigclass.entity.LoginEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;
import com.njtech.bigclass.utils.UserInsertHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

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
    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        init();

    }

    public void init() {
        if (UserInsertHelper.getUserInfo(LogActivity.this) != null) {
            DataBean loginEntity = UserInsertHelper.getUserInfo(this);
            Intent intent = new Intent(LogActivity.this, HomePageActivity.class);
            intent.putExtra("aid",loginEntity.getAid());
            intent.putExtra("aname",loginEntity.getA_name());
            startActivity(intent);
            finish();
        }
    }


    @OnClick({R.id.tv_forget, R.id.btn_regist, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPassActivity.class));
                break;
            case R.id.btn_regist:
                startActivity(new Intent(this, Regist1Activity.class));
                break;
            case R.id.btn_login:
                if (editUser.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editPasswd.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
                break;
        }
    }


    public void login() {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "正在登录，请稍后", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.login(editUser.getText().toString(), editPasswd.getText().toString())
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEntity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LogActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        if (loginEntity.isError()) {
                            Toast.makeText(LogActivity.this, "登录失败:" + loginEntity.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            UserInsertHelper.removeUser(LogActivity.this);
                            UserInsertHelper.insertUser(LogActivity.this, loginEntity.getData());
                            Intent intent = new Intent(LogActivity.this, HomePageActivity.class);
                            intent.putExtra("aid",loginEntity.getData().getAid());
                            intent.putExtra("aname",loginEntity.getData().getA_name());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
