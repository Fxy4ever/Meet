package com.mredrock.cyxbs.summer.ui.mvp.presenter;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.ui.mvp.contract.AskDetailContract;
import com.mredrock.cyxbs.summer.ui.mvp.model.AskDetailModel;
import com.mredrock.cyxbs.summer.ui.view.activity.AskDetailActivity;
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
                int commentNum = AskDetailActivity.bean.getAskInfo().getInt("countNum");
                AskDetailActivity.bean.getAskInfo().put("countNum",commentNum+1);
                AskDetailActivity.bean.getAskInfo().saveInBackground();
            }

            @Override
            public void failed(String msg) {
                Toasts.show(msg);
            }
        });
    }
}
