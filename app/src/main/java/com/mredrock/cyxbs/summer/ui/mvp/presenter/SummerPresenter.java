package com.mredrock.cyxbs.summer.ui.mvp.presenter;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.ui.mvp.contract.SummerContract;

import java.util.List;

public class SummerPresenter extends BasePresenter<SummerContract.ISummerView> {
    private SummerContract.ISummerModel model;
    private static int skip = 10;

    public SummerPresenter(SummerContract.ISummerModel model) {
        this.model = model;
    }

    public void start(){
        skip=10;
        model.loadData(0,new BaseContract.ISomethingModel.LoadCallBack() {
            @Override
            public void succeed(Object o) {
                if(o != null){
                    List<AVObject> data = (List<AVObject>) o;
                    getView().setData(data);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

    public void loadMore(){
        model.loadData(skip, new BaseContract.ISomethingModel.LoadCallBack() {
            @Override
            public void succeed(Object o) {
                if(o!=null){
                    skip+=10;
                    List<AVObject> data = (List<AVObject>) o;
                    getView().setMoreData(data);
                }
            }

            @Override
            public void failed(String msg) {

            }
        });
    }

}
