package com.mredrock.cyxbs.summer.ui.mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.util.ArrayList;
import java.util.List;

public class InfoRepository {
    private static InfoRepository repository;
    private final MutableLiveData<List<AVUser>> data1;
    private final MutableLiveData<List<AVUser>> data2;
    private List<AVUser> newList1 = new ArrayList<>();
    private List<AVUser> newList2 = new ArrayList<>();

    private InfoRepository(){
        data1 = new MutableLiveData<>();
        data2 = new MutableLiveData<>();
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
        newList1.clear();
        try {
            AVQuery<AVUser> query = avUser.followerQuery(AVUser.class);
            query.include("follower");
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if(e==null){
                        newList1.addAll(list);
                        data1.setValue(newList1);
                    }else{
                        Toasts.show("查询不到当前粉丝列表");
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
        return data1;
    }

    /**
     * 获取关注列表
     * @param avUser
     * @return
     */
    public LiveData<List<AVUser>> getFowlloweeList(AVUser avUser){
        newList2.clear();
        try {
            AVQuery<AVUser> query = null;
            query = avUser.followeeQuery(AVUser.class);
            query.include("followee");
            query.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if(e==null){
                        newList2.addAll(list);
                        data2.setValue(newList2);
                    }else{
                        Toasts.show("查询不到当前关注列表");
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }

        return data2;
    }
}
