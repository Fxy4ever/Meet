package com.mredrock.cyxbs.summer.ui.mvp.presenter;

import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.ui.mvp.contract.AskFriednsContract;
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
                Toasts.show("发布成功 财富值+5");
                int curMoney = AVUser.getCurrentUser().getInt("money");
                AVUser.getCurrentUser().put("money",curMoney+5);
                AVUser.getCurrentUser().saveInBackground();
                ActivityManager.getInstance().getCurrentActivity().finish();
            }

            @Override
            public void failed(String msg) {
                Toasts.show(msg);
            }
        });
    }
}
