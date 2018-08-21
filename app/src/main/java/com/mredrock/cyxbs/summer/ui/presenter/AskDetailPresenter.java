package com.mredrock.cyxbs.summer.ui.presenter;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.ui.contract.AskDetailContract;
import com.mredrock.cyxbs.summer.ui.model.AskDetailModel;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.util.List;

public class AskDetailPresenter extends BasePresenter<AskDetailContract.IAskDetailView> {
    private AskDetailModel model;

    public AskDetailPresenter(AskDetailModel model) {
        this.model = model;
    }

    public void start(String objectId){
        model.load(objectId, new BaseContract.ISomethingModel.LoadCallBack() {
            @Override
            public void succeed(Object o) {
                if(o!=null){
                    List<AVObject> data = (List<AVObject>) o;
                    getView().setData(data);
                }
            }

            @Override
            public void failed(String msg) {
                Toasts.show(msg);
            }
        });
    }

    public void comment(CommentBean bean){
        getView().showLoad();
        model.comment(bean, new BaseContract.ISomethingModel.LoadCallBack() {
            @Override
            public void succeed(Object o) {
                getView().hideLoad();

            }

            @Override
            public void failed(String msg) {
                Toasts.show(msg);
            }
        });
    }
}
