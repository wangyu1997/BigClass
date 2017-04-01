package com.njtech.bigclass.utils;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FJS0420 on 2016/8/31.
 */

public interface API {
    String BASE_URL = "http://119.29.97.151/BigClass/";

//    //注册 请求短信验证码接口
//    @FormUrlEncoded
//    @Headers("X-Requested-With:XMLHttpRequest")
//    @POST("Sms/Send")
//    Observable<RegisterEntity> smsSend(@Field("mobile") String mobile, @Field("rn") String rn, @Field("sign") String sign);
//


}
