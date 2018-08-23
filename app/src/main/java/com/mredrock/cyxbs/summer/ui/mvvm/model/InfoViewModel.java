package com.mredrock.cyxbs.summer.ui.mvvm.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.ui.mvvm.repository.InfoRepository;

import java.util.List;

public class InfoViewModel extends AndroidViewModel {
    private LiveData<List<AVUser>> followerList;
    private LiveData<List<AVUser>> followeeList;

    private AVUser fenSi;
    private AVUser guanZhu;


    public InfoViewModel(@NonNull Application application) {
        super(application);
    }

    public void setFenSi(AVUser fenSi) {
        this.fenSi = fenSi;
        followerList = InfoRepository.getInstance().getFowllowerList(fenSi);
    }

    public void setGuanZhu(AVUser guanZhu) {
        this.guanZhu = guanZhu;
        followeeList = InfoRepository.getInstance().getFowlloweeList(guanZhu);
    }

    public LiveData<List<AVUser>> getFollowerList() {
        return followerList;
    }

    public LiveData<List<AVUser>> getFolloweeList() {
        return followeeList;
    }
}
