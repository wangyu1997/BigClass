package com.njtech.bigclass.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.njtech.bigclass.entity.DataBean;
import com.njtech.bigclass.entity.LoginEntity;

/**
 * Created by wangyu on 26/09/2016.
 */

public class UserInsertHelper {
    private String uid;
    private String username;
    private String aid;
    private String a_name;
    private String name;
    private String email;
    private String header;
    private String sex;
    private String createTime;

    public static final String UID = "id";
    public static final String USERNAME = "user_name";
    public static final String AID = "AID";
    public static final String ANAME = "aname";
    public static final String NAME = "signature";
    public static final String EMAIL = "face_img";
    public static final String HEADER = "school_id";
    public static final String SEX = "sex";
    public static final String CREATETIME = "createtime";
    public static final String ACADEMY_NAME = "academy_name";

    public static void insertUser(Context context, LoginEntity.DataBean infoBean) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putString(UID, infoBean.getUid());
        editor.putString(USERNAME, infoBean.getUsername());
        editor.putString(AID, infoBean.getAid());
        editor.putString(ANAME, infoBean.getA_name());
        editor.putString(NAME, infoBean.getName());
        editor.putString(EMAIL, infoBean.getEmail());
        editor.putString(SEX, infoBean.getSex());
        editor.putString(CREATETIME, infoBean.getCreateTime());
        editor.commit();
    }

    public static void insertAcademyName(Context context, String Academy_name) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putString(ACADEMY_NAME, Academy_name);
        editor.commit();
    }

    public static void updateUser(Context context, DataBean infoBean) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.putString(USERNAME, infoBean.getUsername());
        editor.putString(AID, infoBean.getAid());
        editor.putString(ANAME, infoBean.getA_name());
        editor.putString(EMAIL, infoBean.getEmail());
        editor.putString(SEX, infoBean.getSex());
        editor.putString(ACADEMY_NAME, infoBean.getAcademy_name());
        if (infoBean.getHeader() != null)
            editor.putString(HEADER, infoBean.getHeader());
        editor.commit();
    }

    public static DataBean getUserInfo(Context context) {
        SharedPreferences sharedPreferences = MyApplication.getSharedPreferences(context);
        if (!sharedPreferences.getString(UID, "").equals("")) {
            DataBean userInfoBean = new DataBean();
            userInfoBean.setUid(sharedPreferences.getString(UID, ""));
            userInfoBean.setUsername(sharedPreferences.getString(USERNAME, ""));
            userInfoBean.setSex(sharedPreferences.getString(SEX, ""));
            userInfoBean.setAid(sharedPreferences.getString(AID, ""));
            userInfoBean.setA_name(sharedPreferences.getString(ANAME, ""));
            userInfoBean.setHeader(sharedPreferences.getString(HEADER, ""));
            userInfoBean.setName(sharedPreferences.getString(NAME, ""));
            userInfoBean.setEmail(sharedPreferences.getString(EMAIL, ""));
            userInfoBean.setCreateTime(sharedPreferences.getString(CREATETIME, ""));
            userInfoBean.setAcademy_name(sharedPreferences.getString(ACADEMY_NAME, ""));
            return userInfoBean;
        }

        return null;
    }

    public static boolean isUserId(Context context, String id) {
        SharedPreferences sharedPreferences = MyApplication.getSharedPreferences(context);
        String uid = sharedPreferences.getString(UID, "");
        return id.equals(uid);
    }

    public static void removeUser(Context context) {
        SharedPreferences.Editor editor = MyApplication.getEditor(context);
        editor.remove(UID);
        editor.remove(USERNAME);
        editor.remove(SEX);
        editor.remove(AID);
        editor.remove(ANAME);
        editor.remove(HEADER);
        editor.remove(NAME);
        editor.remove(EMAIL);
        editor.remove(CREATETIME);
        editor.remove(ACADEMY_NAME);
        editor.commit();
    }


}
