package com.mredrock.cyxbs.summer.ui.mvp.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mredrock.cyxbs.summer.ui.mvp.contract.UserContract;

import java.util.List;

public class UserModel implements UserContract.IUserModel {
    @Override
    public void LoadUserInfo(String objectId,UserContract.CallBack callBack) {
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        userQuery.whereEqualTo("objectId",objectId);
        userQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if(e==null){
                    //查询关注者
                    AVQuery<AVUser> followeeQuery = null;
                    try {
                        followeeQuery = AVUser.getCurrentUser().followeeQuery(AVUser.class);
                        followeeQuery.findInBackground(new FindCallback<AVUser>() {
                            @Override
                            public void done(List<AVUser> avObjects, AVException avException) {
                                if(avObjects.size()==0) callBack.succeed(list.get(0),false);

                                for (int i = 0; i < avObjects.size(); i++) {
                                    if(avObjects.get(i).getObjectId().equals(objectId)) callBack.succeed(list.get(0),true);
                                    else callBack.succeed(list.get(0),false);
                                }
                            }
                        });
                    } catch (AVException e1) {
                        e1.printStackTrace();
                    }
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
                    callBack.succeed(list);
                }else{
                    callBack.failed(e.getMessage());
                }
            }
        });
    }
}
