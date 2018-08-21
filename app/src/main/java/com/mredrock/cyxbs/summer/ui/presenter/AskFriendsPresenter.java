package com.mredrock.cyxbs.summer.ui.presenter;

import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.ui.contract.AskFriednsContract;
import com.mredrock.cyxbs.summer.utils.ActivityManager;
import com.mredrock.cyxbs.summer.utils.Toasts;

public class AskFriendsPresenter extends BasePresenter<AskFriednsContract.IAskFriendsView> {
    private AskFriednsContract.IAskFriendsModel model;

    public AskFriendsPresenter(AskFriednsContract.IAskFriendsModel model) {
        this.model = model;
    }

    public void ask(AskBean askBean){
        getView().showLoad();
        model.Ask(askBean, new BaseContract.ISomethingModel.LoadCallBack() {
            @Override
            public void succeed(Object o) {
                getView().hideLoad();
                Toasts.show("发布成功");
                ActivityManager.getInstance().getCurrentActivity().finish();
            }

            @Override
            public void failed(String msg) {
                Toasts.show(msg);
            }
        });
    }
}
