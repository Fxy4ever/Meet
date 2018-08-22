package com.mredrock.cyxbs.summer.ui.contract;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.bean.AskBean;

import java.util.List;

public class UserContract {

    public interface IUserModel extends BaseContract.ISomethingModel{
        void LoadUserInfo(String objectId,LoadCallBack callBack);
        void LoadUserRecent(AVUser user,LoadCallBack callBack);
    }

    public interface IUserView extends BaseContract.ISomethingView{
        void setUser(AVUser avUser);
        void setData(List<AVObject> beans);
    }
}
