package com.mredrock.cyxbs.summer.utils;

import android.util.Log;

import com.mredrock.cyxbs.summer.bean.ChickenBean;
import com.mredrock.cyxbs.summer.bean.InfoBean;
import com.mredrock.cyxbs.summer.bean.MeetBackBean;
import com.mredrock.cyxbs.summer.bean.MeetQuestionBean;
import com.mredrock.cyxbs.summer.bean.TokenBean;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private static String baseUrl = "http://120.79.143.238:8765/";
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
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            //打印retrofit日志
            Log.e("RetrofitLog","retrofitBack = "+message);
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
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

    public Observable<InfoBean> setQuestion(
            String token,
            String user_id,
            String question_note,
            String question_name1, String question_answer1,
            String question_name2, String question_answer2,
            String question_name3, String question_answer3
    ) {
        return service.setQuestion(token, user_id, question_note, question_name1, question_answer1,
                question_name2, question_answer2, question_name3, question_answer3);
    }

    public Observable<ChickenBean> getChicken() {
        return service.getChicken(token);
    }

    public Observable<MeetQuestionBean> meet(String user_id, String token) {
        return service.meet(user_id,token);
    }

    public Observable<MeetBackBean> answer(String token,
                                           String user_id,
                                           String question_id,
                                           String question_note,
                                           String question_name1, String question_answer1,
                                           String question_name2, String question_answer2,
                                           String question_name3, String question_answer3){
        return service.answerQuestion(token,user_id,question_id,question_note,
                question_name1,question_answer1,
                question_name2,question_answer2,
                question_name3,question_answer3);
    }



    public interface APIService {
        //注册用户进数据库
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
                                         @Field("question_note") String question_note,
                                         @Field("question_name1") String question_name1,
                                         @Field("question_answer1") String question_answer1,
                                         @Field("question_name2") String question_name2,
                                         @Field("question_answer2") String question_answer2,
                                         @Field("question_name3") String question_name3,
                                         @Field("question_answer3") String question_answer3);


        //匹配问题
        @POST("meet/question/get")
        @FormUrlEncoded
        Observable<MeetQuestionBean> meet(@Field("user_id") String user_id, @Field("token") String token);

        //回答问题
        @POST("meet/question/mate")
        @FormUrlEncoded
        Observable<MeetBackBean> answerQuestion(@Field("token") String token,
                                                @Field("user_id") String user_id,
                                                @Field("question_id") String question_id,
                                                @Field("question_note") String question_note,
                                                @Field("question_name1") String question_name1,
                                                @Field("question_answer1") String question_answer1,
                                                @Field("question_name2") String question_name2,
                                                @Field("question_answer2") String question_answer2,
                                                @Field("question_name3") String question_name3,
                                                @Field("question_answer3") String question_answer3);

        @POST("meet/article/getAll")
        @FormUrlEncoded
        Observable<ChickenBean> getChicken(@Field("p") String p);
    }

}
