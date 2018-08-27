package com.mredrock.cyxbs.summer.ui.mvvm.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.mredrock.cyxbs.summer.bean.ChatUserBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatListRepository {
    private static ChatListRepository repository;
    private MutableLiveData<List<ChatUserBean>> data = new MutableLiveData<>();
    private List<ChatUserBean> beans = new ArrayList<>();
    private AVIMClient client;

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

    /**
     * 这里建表不是很合理，所以查询代码稍多了一些 后面优化
     * @return
     */
    public MutableLiveData<List<ChatUserBean>> getData(){
        beans.clear();
        AVUser user = AVUser.getCurrentUser();

        AVQuery<AVObject> query = new AVQuery<>("conRelation");
        query.whereEqualTo("mine",user);

        AVQuery<AVObject> query1 = new AVQuery<>("conRelation");
        query1.whereEqualTo("you",user);

        AVQuery<AVObject> query_all = AVQuery.or(Arrays.asList(query,query1));
        query_all.include("mine");
        query_all.include("you");
        query_all.orderByDescending("updatedAt");
        query_all.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(list!=null&&list.size()>0){
                    //为了和下面list命名上不冲突
                    List<AVObject> lists = new ArrayList<>(list);
                    for (int i = 0; i < list.size(); i++) {
                        ChatUserBean bean = new ChatUserBean();
                        if(user.getObjectId().equals(list.get(i).getAVUser("you").getObjectId())){
                            //如果当前用户是you 对面的就是mine
                            bean.setAvUser(list.get(i).getAVUser("mine"));
                            bean.setConversationId(list.get(i).getString("conversationId"));
                            AVQuery<AVObject> avQuery = new AVQuery<>("_Conversation");
                            avQuery.whereEqualTo("objectId",list.get(i).getString("conversationId"));
                            int finalI = i;
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    if(e==null){
                                        bean.setConversation(list.get(0));
                                        beans.add(bean);
                                        if(finalI ==lists.size()-1){
                                            data.setValue(beans);
                                        }
                                    }
                                }
                            });
                        }else {
                            //如果当前用户是mine 对面的就是you
                            bean.setAvUser(list.get(i).getAVUser("you"));
                            bean.setConversationId(list.get(i).getString("conversationId"));
                            AVQuery<AVObject> avQuery = new AVQuery<>("_Conversation");
                            avQuery.whereEqualTo("objectId",list.get(i).getString("conversationId"));
                            int finalI = i;
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    if(e==null){
                                        bean.setConversation(list.get(0));
                                        beans.add(bean);
                                        if(finalI ==lists.size()-1){
                                            data.setValue(beans);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

        return data;
    }
}
