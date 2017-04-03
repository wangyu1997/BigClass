package com.njtech.bigclass;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.njtech.bigclass.entity.GeneralEntity;
import com.njtech.bigclass.entity.LoginEntity;
import com.njtech.bigclass.entity.RegistEntity;
import com.njtech.bigclass.entity.RegistVerEntity;
import com.njtech.bigclass.utils.API;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.HttpControl;
import com.njtech.bigclass.utils.MyApplication;
import com.njtech.bigclass.utils.UserInsertHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static rx.schedulers.Schedulers.io;

/**
 * Created by wangyu on 02/04/2017.
 */

public class Regist2Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_passwdconfirm)
    EditText editPasswdconfirm;
    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.edit_verifycode)
    EditText editVerifycode;
    @BindView(R.id.btn_resend)
    Button btnResend;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    private RegistVerEntity registVerEntity;
    private Intent data;
    private String rawcode;
    private String code;
    private int i = 60;
    private String academy_name;

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
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        data = getIntent();
        academy_name = data.getStringExtra("academy_name");
        registVerEntity = (RegistVerEntity) data.getBundleExtra("bundle").getSerializable("regist");
    }

    @OnClick(R.id.btn_resend)
    public void resend() {
        send();
    }

    public void regist() {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(Regist2Activity.this, "正在注册，请稍后", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.regist(registVerEntity.getUsername(), registVerEntity.getEmail(),
                registVerEntity.getPassword(), registVerEntity.getAid(),
                registVerEntity.getName(), registVerEntity.getSex())
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegistEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Regist2Activity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(RegistEntity registEntity) {
                        if (registEntity.isError()) {
                            Toast.makeText(Regist2Activity.this, "注册失败:" + registEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(Regist2Activity.this, "注册成功，正在登录，请稍后", Toast.LENGTH_SHORT).show();
                            login();
                        }
                    }
                });
    }

    public void send() {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(Regist2Activity.this, "正在发送验证码，请稍后", Toast.LENGTH_SHORT).show();
        String email = editEmail.getText().toString();
        if (!checkEmaile(email)) {
            Toast.makeText(MyApplication.getGlobalContext(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.getcode("send", email)
                .subscribeOn(io())
                .unsubscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GeneralEntity>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                        btnResend.setClickable(false);
                        btnResend.setText(i + "");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (; i > 0; i--) {
                                    handler.sendEmptyMessage(-9);
                                    if (i <= 0) {
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(-8);
                            }
                        }).start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Regist2Activity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(GeneralEntity generalEntity) {
                        if (generalEntity.isError()) {
                            Toast.makeText(Regist2Activity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                        } else {
                            rawcode = generalEntity.getData();
                            Toast.makeText(Regist2Activity.this, "验证码发送成功，请回邮箱查看", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void login() {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        Toast.makeText(this, registVerEntity.getUsername() + "  " + registVerEntity.getPassword(), Toast.LENGTH_SHORT).show();
        api.login(registVerEntity.getUsername(), registVerEntity.getPassword())
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
                        Toast.makeText(Regist2Activity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        if (loginEntity.isError()) {
                            Toast.makeText(Regist2Activity.this, "登录失败:" + loginEntity.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Regist2Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            UserInsertHelper.insertUser(Regist2Activity.this, loginEntity.getData());
                            UserInsertHelper.insertAcademyName(Regist2Activity.this, academy_name);
                            startActivity(new Intent(Regist2Activity.this, upHeaderActivity.class));
                        }
                    }
                });
    }


    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btnResend.setText(i + "");
            } else if (msg.what == -8) {
                btnResend.setText("发送验证码");
                btnResend.setClickable(true);
                i = 60;
            }
        }

    };

    /**
     * 正则表达式校验邮箱
     *
     * @param emaile 待匹配的邮箱
     * @return 匹配成功返回true 否则返回false;
     */
    private static boolean checkEmaile(String emaile) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }

    @OnClick(R.id.btn_regist)
    public void onClick() {
        code = editVerifycode.getText().toString();
        if (!code.equals(rawcode)) {
            Toast.makeText(MyApplication.getGlobalContext(), "验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        registVerEntity.setEmail(editEmail.getText().toString());
        registVerEntity.setPassword(editPasswd.getText().toString());
        registVerEntity.setPasswordCom(editPasswdconfirm.getText().toString());
        if (registVerEntity.isLeagel2()) {
            regist();
        }
    }
}
