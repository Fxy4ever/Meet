package com.mredrock.cyxbs.summer.ui.mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mredrock.cyxbs.summer.bean.ChatBean;
import com.mredrock.cyxbs.summer.ui.view.activity.App;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRepository {
    public static final String TAG = "ChatRepository";

    private static ChatRepository repository;
    private MutableLiveData<List<ChatBean>> data;
    private AVUser mine;
    private AVUser you;
    private AVIMConversation conversation;
    private boolean isCanChat = false;
    private List<ChatBean> beans;
    private AVIMClient client;

    private ChatRepository() {
    }

    public static ChatRepository getInstance() {
        if (repository == null) {
            synchronized (ChatRepository.class) {
                if (repository == null) {
                    repository = new ChatRepository();
                }
            }
        }
        return repository;
    }

    public void setUser(AVUser you, AVUser mine) {
        this.mine = mine;
        this.you = you;
    }

    //qnm leanCloud  连接leanCloud
    public LiveData<List<ChatBean>> createChat(AVIMClient client) {
        data = new MutableLiveData<>();
        client.open(new AVIMClientCallback() {//建立client连接
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    List<String> userList = new ArrayList<>();//初始化列表
                    userList.add(you.getUsername());
                    userList.add(mine.getUsername());
                    Collections.sort(userList);//这样做让单聊的名称能够统一
                    String name = userList.get(0) + " & " + userList.get(1);

                    AVQuery<AVObject> avQuery = new AVQuery("conRelation");//查询是否存在这个conversation
                    avQuery.whereEqualTo("conversationName", name);
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null) {
                                if (list.size() > 0) {//存在就直接找到改conversation
                                    conversation = client.getConversation((String) App.spHelper().get(name, "null"));
                                    Toasts.show("连接对话成功");
                                    isCanChat = true;
                                    beans = new ArrayList<>();
                                } else {//否则，建立新的conversation 并存进远程数据库
                                    client.createConversation(userList, name, null, new AVIMConversationCreatedCallback() {
                                        @Override
                                        public void done(AVIMConversation avimConversation, AVIMException e) {
                                            if (e == null) {
                                                Toasts.show("连接对话成功");
                                                AVObject avObject = new AVObject("conRelation");
                                                avObject.put("conversationName", name);
                                                avObject.put("conversationId", avimConversation.getConversationId());
                                                avObject.saveInBackground();
                                                conversation = avimConversation;
                                                isCanChat = true;
                                                // TODO: 2018/8/25 数据库查询
                                                beans = new ArrayList<>();
                                                data.setValue(beans);
                                            } else {
                                                Toasts.show("连接对话失败");
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    Toasts.show("连接服务器失败" + e.getMessage());
                }
            }
        });
        return data;
    }

    /**
     * 发送文字消息
     *
     * @param str
     */
    public void sendMessage(String str) {
        AVIMTextMessage msg = new AVIMTextMessage();
        if (str.length() != 0) {
            msg.setText(str);
            if (isCanChat) {
                conversation.sendMessage(msg, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (e == null) {
                            Log.d("chat", "done: 文字发送成功");
                            changeData(msg);
                        }
                    }
                });
            }
        }
    }

    public void sendAudio(String name,String path){
        try {
            AVFile file = AVFile.withAbsoluteLocalPath(name,path);
            AVIMAudioMessage m = new AVIMAudioMessage(file);
            m.setText(name);
            conversation.sendMessage(m, new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    if(e==null){
                        changeData(m);
                        Log.d("chat", "done: 语音发送成功");
                    }else {
                        Toasts.show("语音发送失败");
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendImage(String name,String path){
        try {
            AVIMImageMessage message = new AVIMImageMessage(path);
            message.setText(name);
            conversation.sendMessage(message, new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    if(e==null){
                        changeData(message);
                        Log.d("chat", "done: 图片发送成功");
                    }else{
                        Toasts.show("图片发送失败");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 刷新数据
     *
     * @param msg
     */
    public void changeData(AVIMMessage msg) {
        ChatBean bean = new ChatBean();
        //判断msg类型
        Log.d("chat", "changeData: 2" + mine.getUsername());
        if (msg.getFrom().equals(mine.getUsername())) {
            if (msg instanceof AVIMTextMessage) {
                bean.setKind("我的文字");
            } else if (msg instanceof AVIMAudioMessage) {
                bean.setKind("我的语音");
            } else {
                bean.setKind("我的图片");
            }
        } else {
            if (msg instanceof AVIMTextMessage) {
                bean.setKind("你的文字");
            } else if (msg instanceof AVIMAudioMessage) {
                bean.setKind("你的语音");
            } else {
                bean.setKind("你的图片");
            }
        }
        bean.setMessage(msg);
        beans.add(bean);
        data.setValue(beans);
    }
    // TODO: 2018/8/25 优化发送速度
}
