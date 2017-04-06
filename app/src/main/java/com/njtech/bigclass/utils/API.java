package com.njtech.bigclass.utils;


import com.njtech.bigclass.entity.AcademysEntity;
import com.njtech.bigclass.entity.AddEntity;
import com.njtech.bigclass.entity.ArrayEntity;
import com.njtech.bigclass.entity.CourseEntity;
import com.njtech.bigclass.entity.Info_entity;
import com.njtech.bigclass.entity.ObjEntity;
import com.njtech.bigclass.entity.StringEntity;
import com.njtech.bigclass.entity.LoginEntity;
import com.njtech.bigclass.entity.RegistEntity;
import com.njtech.bigclass.entity.courseShowEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface API {
    String BASE_URL = "http://119.29.97.151/BigClass1/";

    //七牛 token
    @FormUrlEncoded
    @POST("token/getToken.php")
    Call<String> getToken(@Field("token") String token);

    @POST("public/academys.php")
    Observable<AcademysEntity> getAcademys();

    @FormUrlEncoded
    @POST("public/courses.php")
    Observable<CourseEntity> getCoursesInfo(@Field("id") int id);

    //注册 请求验证码接口
    @FormUrlEncoded
    @POST("public/sendMail.php")
    Observable<StringEntity> getcode(@Field("action") String acton, @Field("email") String email);

    //注册 请求验证码接口
    @FormUrlEncoded
    @POST("public/sendMail.php")
    Observable<StringEntity> resetPass(@Field("action") String acton, @Field("email") String email, @Field("flag") String flag);


    //注册
    @FormUrlEncoded
    @POST("teacher/regist.php")
    Observable<RegistEntity> regist(@Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("aid") int aid, @Field("name") String name, @Field("sex") int sex);

    //登录
    @FormUrlEncoded
    @POST("teacher/login.php")
    Observable<LoginEntity> login(@Field("username") String username, @Field("password") String password);

    //上传头像
    @FormUrlEncoded
    @POST("teacher/upHeader.php")
    Observable<ArrayEntity> upHeader(@Field("head") String headUrl);

    //忘记密码
    @FormUrlEncoded
    @POST("teacher/forgetPass.php")
    Observable<ArrayEntity> forgetPass(@Field("email") String email,@Field("password") String password);

    //首页课程
    @FormUrlEncoded
    @POST("public/allcourses.php")
    Observable<courseShowEntity> getCourses(@Field("aid") int aid);

    //检查课程是否自己开设
    @FormUrlEncoded
    @POST("teacher/check.php")
    Observable<ObjEntity> checkCourses(@Field("cid") int cid);

    //发起结束签到
    @FormUrlEncoded
    @POST("teacher/sign.php")
    Observable<ObjEntity> sign(@Field("cid") int cid,@Field("action") String action);

    //获取课程详情
    @FormUrlEncoded
    @POST("public/Info.php")
    Observable<Info_entity> courseInfo(@Field("id") int id);

    //添加课程
    @FormUrlEncoded
    @POST("teacher/add.php")
    Observable<AddEntity> addCourse(@FieldMap Map<String,Object> map);




}
