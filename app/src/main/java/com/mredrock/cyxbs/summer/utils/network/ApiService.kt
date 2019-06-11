package com.mredrock.cyxbs.summer.utils.network


import com.mredrock.cyxbs.summer.bean.LoginBean
import com.mredrock.cyxbs.summer.bean.NetBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/userSystem/register")
    fun register(@Query("userName") userName:String,
                 @Query("password") password:String,
                 @Query("phone") phone:String,
                 @Query("age") age:Int):Observable<NetBean>

    @POST("/userSystem/login")
    fun login(@Query("userName") userName:String,
                 @Query("password") password:String):Observable<LoginBean>
}