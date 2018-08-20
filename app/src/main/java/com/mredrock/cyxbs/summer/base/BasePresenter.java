package com.mredrock.cyxbs.summer.base;


public class BasePresenter<V extends BaseContract.ISomethingView> implements IBasePresenter<V> {
    private V view;

    //关联View
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    //解除View关联
    @Override
    public void detachView() {
        view = null;
    }

    public V getView() {
        return view;
    }


    private boolean isViewAttach() {
        return view != null;
    }

}
