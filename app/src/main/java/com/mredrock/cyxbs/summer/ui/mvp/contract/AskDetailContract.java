package com.mredrock.cyxbs.summer.ui.mvp.contract;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.bean.CommentBean;

import java.util.List;

public class AskDetailContract {

    public interface IAskDetailModel extends BaseContract.ISomethingModel{
        void comment(CommentBean bean,LoadCallBack callBack);
        void load(String objectId,LoadCallBack callBack);
    }

    public interface IAskDetailView extends BaseContract.ISomethingView{
        void showLoad();
        void hideLoad();
        void setData(List<AVObject> data);
    }
}