package com.mredrock.cyxbs.summer.ui.mvvm.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mredrock.cyxbs.summer.bean.ChatUserBean;
import com.mredrock.cyxbs.summer.ui.mvvm.repository.ChatListRepository;

import java.util.List;

public class ChatListViewModel extends AndroidViewModel {
    private LiveData<List<ChatUserBean>> list;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadData(){
        list = ChatListRepository.getInstance().getData();
    }

    public LiveData<List<ChatUserBean>> getList() {
        return list;
    }
}
