package com.mredrock.cyxbs.summer.ui.presenter;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.ui.contract.SummerContract;

import java.util.List;

public class SummerPresenter extends BasePresenter<SummerContract.ISummerView> {
    private SummerContract.ISummerModel model;

    public SummerPresenter(SummerContract.ISummerModel model) {
        this.model = model;
    }

    public void start(){
        model.loadData(new BaseContract.ISomethingModel.LoadCallBack() {
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
}
