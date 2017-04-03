package com.njtech.bigclass.utils;


import com.njtech.bigclass.entity.AcademysEntity;
import com.njtech.bigclass.entity.GeneralEntity;
import com.njtech.bigclass.entity.LoginEntity;
import com.njtech.bigclass.entity.RegistEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface API {
    String BASE_URL = "http://119.29.97.151/BigClass/";

    //七牛 token
    @FormUrlEncoded
    @POST("token/getToken.php")
    Call<String> getToken(@Field("token") String token);

    @POST("public/academys.php")
    Observable<AcademysEntity> getAcademys();

    //注册 请求验证码接口
    @FormUrlEncoded
    @POST("public/sendMail.php")
    Observable<GeneralEntity> getcode(@Field("action") String acton, @Field("email") String email);

    //注册
    @FormUrlEncoded
    @POST("teacher/regist.php")
    Observable<RegistEntity> regist(@Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("aid") int aid, @Field("name") String name, @Field("sex") int sex);

    //登录
    @FormUrlEncoded
    @POST("teacher/login.php")
    Observable<LoginEntity> login(@Field("username") String username, @Field("password") String password);


}
