package com.mredrock.cyxbs.summer.ui.mvvm.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.util.List;

public class InfoRepository {
    private static InfoRepository repository;
    private AVUser currentUesr;

    private InfoRepository(){
        currentUesr = AVUser.getCurrentUser();
    }

    public static InfoRepository getInstance(){
        if(repository==null){
            synchronized (InfoRepository.class){
                if(repository==null){
                    repository = new InfoRepository();
                }
            }
        }
        return repository;
    }

    /**
     * 查询用户的粉丝
     * @param avUser
     * @return
     */
    public LiveData<List<AVUser>> getFowllowerList(AVUser avUser){
        final MutableLiveData<List<AVUser>> data = new MutableLiveData<>();

        try {
            AVQuery<AVUser> query = avUser.followerQuery(AVUser.class);
            query.include("follower");
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if(e==null){
                        data.setValue(list);
                    }else{
                        Toasts.show("查询不到当前粉丝列表");
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取关注列表
     * @param avUser
     * @return
     */
    public LiveData<List<AVUser>> getFowlloweeList(AVUser avUser){
        final MutableLiveData<List<AVUser>> data = new MutableLiveData<>();

        AVQuery<AVUser> query = null;
        try {
            query = avUser.followeeQuery(AVUser.class);
            query.include("followee");
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if(e==null){
                        data.setValue(list);
                    }else{
                        Toasts.show("查询不到当前关注列表");
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }

        return data;
    }
}
