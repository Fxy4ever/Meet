package com.mredrock.cyxbs.summer.ui.mvvm.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.bean.ChatBean;
import com.mredrock.cyxbs.summer.event.AudioEvent;
import com.mredrock.cyxbs.summer.event.ImageEvent;
import com.mredrock.cyxbs.summer.event.TextEvent;
import com.mredrock.cyxbs.summer.ui.mvvm.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private LiveData<List<ChatBean>> chatList;
    private LiveData<AVUser> user;
    private final AVUser mine;
    private final AVUser you;
    public static final String TAG = "ChatViewModel";


    private ChatViewModel(@NonNull Application application, AVUser mine, AVUser you) {
        super(application);
        this.mine = mine;
        this.you = you;
        ChatRepository.getInstance().setUser(you, mine);
        chatList = ChatRepository.getInstance().createChat();
    }

    public void sendText(String str){
        ChatRepository.getInstance().sendMessage(str);
    }

    public void sendAudio(String name,String path){
        ChatRepository.getInstance().sendAudio(name,path);
    }

    public void sendImage(String name,String path){
        ChatRepository.getInstance().sendImage(name,path);
    }

    public void getText(TextEvent event){
        ChatRepository.getInstance().changeData(event.getMessage());
    }
    public void getImage(ImageEvent event){
        ChatRepository.getInstance().changeData(event.getMessage());
    }
    public void getAudio(AudioEvent event){
        ChatRepository.getInstance().changeData(event.getMessage());
    }

    public LiveData<List<ChatBean>> getChatList(){
        if(chatList.getValue()!=null){
        }
        return chatList;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;

        private final AVUser mine;
        private final AVUser you;

        public Factory(Application application,AVUser mine, AVUser you) {
            this.application = application;
            this.mine = mine;
            this.you = you;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ChatViewModel(application, mine, you);
        }
    }
}
