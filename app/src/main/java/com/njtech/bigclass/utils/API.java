package com.njtech.bigclass.utils;


import com.njtech.bigclass.entity.AcademysEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by FJS0420 on 2016/8/31.
 */

public interface API {
    String BASE_URL = "http://119.29.97.151/BigClass/";

    //注册 请求短信验证码接口
    @FormUrlEncoded
    @POST("token/getToken.php")
    Call<String> getToken(@Field("token") String token);

    @POST("public/academys.php")
    Observable<AcademysEntity> getAcademys();




}
