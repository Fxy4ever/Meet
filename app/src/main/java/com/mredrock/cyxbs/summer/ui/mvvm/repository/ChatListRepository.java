package com.mredrock.cyxbs.summer.ui.mvvm.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.mredrock.cyxbs.summer.bean.ChatUserBean;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.tencent.qc.stat.common.User;

import java.util.ArrayList;
import java.util.List;

public class ChatListRepository {
    private static ChatListRepository repository;
    private MutableLiveData<List<ChatUserBean>> data = new MutableLiveData<>();
    private List<ChatUserBean> beans = new ArrayList<>();

    private ChatListRepository() {
    }

    public static ChatListRepository getInstance(){
        if(repository==null){
            synchronized (ChatListRepository.class){
                if(repository==null){
                    repository = new ChatListRepository();
                }
            }
        }
        return repository;
    }

    public MutableLiveData<List<ChatUserBean>> getData(){
        beans.clear();
        Log.d("ChatListFragment", "getData: ");
        AVUser user = AVUser.getCurrentUser();
        AVQuery<AVObject> query = new AVQuery<>("conRelation");
        query.whereEqualTo("mine",user);
        query.include("you");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (int i = 0; i< list.size();i++) {
                    ChatUserBean bean = new ChatUserBean();
                    AVUser you = list.get(i).getAVUser("you");
                    bean.setAvUser(you);
                    bean.setConversationId(list.get(i).getString("conversationId"));
                    beans.add(bean);
                    AVQuery<AVObject> avQuery = new AVQuery<>("_Conversation");
                    avQuery.whereEqualTo("objectId",list.get(i).getString("conversationId"));
                    int finalI = i;
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if(e==null){
                                bean.setConversation(list.get(0));
                                if(finalI ==list.size()-1){
                                    data.setValue(beans);
                                }
                            }
                        }
                    });
                }

            }
        });
        return data;
    }


}
