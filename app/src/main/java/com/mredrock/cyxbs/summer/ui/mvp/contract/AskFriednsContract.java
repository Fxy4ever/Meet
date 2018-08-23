package com.mredrock.cyxbs.summer.ui.mvp.contract;

import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.bean.AskBean;

public class AskFriednsContract {

    public interface IAskFriendsModel extends BaseContract.ISomethingModel{
        void Ask(AskBean bean,LoadCallBack callBack);
    }

    public interface IAskFriendsView extends BaseContract.ISomethingView{
        void showLoad();
        void hideLoad();
    }
}
