package com.mredrock.cyxbs.summer.utils;

import com.mredrock.cyxbs.summer.bean.InfoBean;
import com.mredrock.cyxbs.summer.bean.TokenBean;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * create by:Fxymine4ever
 * time: 2018/11/13
 */
public class HttpUtilManager {
    private static String baseUrl = "http://ry24mj.natappfree.cc/";
    private static String token = "pi6mNVUp";
    private APIService service;
    private static HttpUtilManager httpUtilManager;


    public static HttpUtilManager getInstance() {
        if (httpUtilManager == null) {
            synchronized (HttpUtilManager.class) {
                if (httpUtilManager == null) {
                    httpUtilManager = new HttpUtilManager();
                }
            }
        }
        return httpUtilManager;
    }


    private HttpUtilManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
    }

    public Observable<InfoBean> register(String user_id) {
        return service.register(user_id, token);
    }

    public Observable<TokenBean> getToken(String user_id) {
        return service.getToken(user_id, token);
    }


    public interface APIService {
        //注册用户进lxc的数据库
        @POST("meet/user/register")
        @FormUrlEncoded
        Observable<InfoBean> register(@Field("user_id") String user_id, @Field("p") String p);

        //刷新token
        @POST("meet/user/t0ken_refresh")
        @FormUrlEncoded
        Observable<TokenBean> getToken(@Field("user_id") String user_id, @Field("p") String p);

        //设置问题
        @POST("meet/question/create")
        @FormUrlEncoded
        Observable<InfoBean> setQuestion(@Field("token") String token,
                                         @Field("user_id") String user_id,
                                         @Field("question_node") String question_note,
                                         @Field("question_name1") String question_name1,
                                         @Field("question_answer1") String question_answer1,
                                         @Field("question_name2") String question_name2,
                                         @Field("question_answer2") String question_answer2,
                                         @Field("question_name3") String question_name3,
                                         @Field("question_answer3") String question_answer3);


        // TODO: 2018/11/13 等接口修好 改 Bean
        //匹配问题
        @POST("meet/question/get")
        @FormUrlEncoded
        Observable<InfoBean> meet(@Field("user_id") String user_id, @Field("token") String token);

        //回答问题
        @POST("meet/question/mate")
        @FormUrlEncoded
        Observable<InfoBean> answerQuestion(@Field("token") String token,
                                            @Field("question_id") String question_id,
                                            @Field("user_id") String user_id,
                                            @Field("question_node") String question_note,
                                            @Field("question_name1") String question_name1,
                                            @Field("question_answer1") String question_answer1,
                                            @Field("question_name2") String question_name2,
                                            @Field("question_answer2") String question_answer2,
                                            @Field("question_name3") String question_name3,
                                            @Field("question_answer3") String question_answer3);


    }

}
