package com.mredrock.cyxbs.summer.base;

public interface IBasePresenter<V> {
    void attachView(V view);//绑定View

    void detachView();//解绑View
}
