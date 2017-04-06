package com.njtech.bigclass.utils;


import com.njtech.bigclass.entity.AcademysEntity;
import com.njtech.bigclass.entity.ArrayEntity;
import com.njtech.bigclass.entity.StringEntity;
import com.njtech.bigclass.entity.LoginEntity;
import com.njtech.bigclass.entity.RegistEntity;
import com.njtech.bigclass.entity.courseShowEntity;

import retrofit2.Call;
import retrofit2.http.Field;
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

}
