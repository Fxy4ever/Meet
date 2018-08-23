package com.mredrock.cyxbs.summer.ui.mvp.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.ui.mvp.contract.AskDetailContract;

import java.util.List;

public class AskDetailModel implements AskDetailContract.IAskDetailModel {
    @Override
    public void comment(CommentBean bean, LoadCallBack callBack) {
        AVObject comment = new AVObject("comment");
        comment.put("user",bean.getUser());
        comment.put("askInfo",bean.getAskInfo());
        if(bean.getVoice()!=null){
            comment.put("voice",bean.getVoice());
        }
        if(!bean.getContent().equals("")){
            comment.put("content",bean.getContent());
        }
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    callBack.succeed("");
                }else{
                    callBack.failed(e.getMessage());
                }
            }
        });

    }

    @Override
    public void load(String objectId, LoadCallBack callBack) {
        AVObject askInfo = AVObject.createWithoutData("askInfo",objectId);
        AVQuery<AVObject> query = new AVQuery<>("comment");
        query.whereEqualTo("askInfo",askInfo);
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
