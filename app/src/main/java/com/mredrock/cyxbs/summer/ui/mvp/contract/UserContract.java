package com.mredrock.cyxbs.summer.ui.mvp.contract;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.base.BaseContract;

import java.util.List;

public class UserContract {

    public interface IUserModel extends BaseContract.ISomethingModel{
        void LoadUserInfo(String objectId,CallBack callBack);
        void LoadUserRecent(AVUser user,LoadCallBack callBack);
    }

    public interface IUserView extends BaseContract.ISomethingView{
        void setUser(AVUser avUser,boolean isFav);
        void setData(List<AVObject> beans);
    }

    public interface CallBack{
        void succeed(Object o,boolean isFavorite);
        void failed(String msg);
    }
}
