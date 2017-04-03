package com.njtech.bigclass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.njtech.bigclass.entity.DataBean;
import com.njtech.bigclass.utils.AppManager;
import com.njtech.bigclass.utils.UserInsertHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyu on 03/04/2017.
 */

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.textView3)
    TextView textView3;
    private DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        dataBean = UserInsertHelper.getUserInfo(this);
        String uid = "uid:" + dataBean.getUid();
        String username = "username:" + dataBean.getUsername();
        String aid = "aid:" + dataBean.getAid();
        String a_name = "a_name:" + dataBean.getA_name();
        String name = "name:" + dataBean.getName();
        String email = "email:" + dataBean.getEmail();
        String header = "header:" + dataBean.getHeader();
        String sex = "sex:" + dataBean.getSex();
        String createTime = "createTime:" + dataBean.getCreateTime();
        textView3.setText(uid + "\n" + username + "\n"
                + aid + "\n" + a_name + "\n" + name + "\n" + email + "\n" +
                header + "\n" + sex + "\n" + createTime + "\n" );
    }
}
