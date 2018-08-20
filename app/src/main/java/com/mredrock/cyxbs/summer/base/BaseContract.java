package com.mredrock.cyxbs.summer.base;

import android.content.Context;

/**
 * 用Contract类来管理接口
 **/
public class BaseContract {

    //公用View接口
    public interface ISomethingView {
        Context getContext();
    }

    //公用Model接口
    public interface ISomethingModel {

        interface LoadCallBack {
            void succeed(Object o);//这里改成你想要回调的数据类型

            void failed(String msg);
        }
    }

}
