package com.mredrock.cyxbs.summer.ui.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.ui.contract.UserContract;

import java.util.List;

public class UserModel implements UserContract.IUserModel {
    @Override
    public void LoadUserInfo(String objectId, LoadCallBack callBack) {
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereEqualTo("objectId",objectId);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if(e==null){
                    callBack.succeed(list.get(0));
                }else{
                    callBack.failed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void LoadUserRecent(AVUser user, LoadCallBack callBack) {
        AVQuery<AVObject> query = new AVQuery<>("askInfo");
        query.whereEqualTo("author",user);
        query.orderByDescending("updatedAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    Log.d("fxy", "done: "+list.size());
                    callBack.succeed(list);
                }else{
                    callBack.failed(e.getMessage());
                }
            }
        });
    }
}
