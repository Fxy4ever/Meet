package com.mredrock.cyxbs.summer.ui.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.mredrock.cyxbs.summer.base.BaseContract;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.ui.contract.AskFriednsContract;

public class AskFriendsModel implements AskFriednsContract.IAskFriendsModel {


    @Override
    public void Ask(AskBean bean, LoadCallBack callBack) {

            AVObject info = new AVObject("askInfo");
            info.put("askAvatar",bean.getAskAvatar());
            info.put("askName",bean.getAskName());
            info.put("askContent",bean.getAskContent());
            Log.d("fxy", "Ask: "+bean.getAuthorName());
            info.put("authorName",bean.getAuthorName());
            if(bean.getVoice()!=null){
                info.put("voice",bean.getVoice());
            }
            if(bean.getPhotos()!=null){
                info.put("photos",bean.getPhotos());
            }

            info.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        callBack.succeed("");//成功就回调
                    }else{
                        callBack.failed(e.getMessage());
                    }
                }
            });
    }
}
