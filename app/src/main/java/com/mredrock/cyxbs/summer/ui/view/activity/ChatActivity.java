package com.mredrock.cyxbs.summer.ui.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.ChatListAdapter;
import com.mredrock.cyxbs.summer.bean.ChatBean;
import com.mredrock.cyxbs.summer.databinding.ActivityChatBinding;
import com.mredrock.cyxbs.summer.event.TextEvent;
import com.mredrock.cyxbs.summer.utils.MyMessageHandler;
import com.mredrock.cyxbs.summer.utils.TextWatcheListener;
import com.mredrock.cyxbs.summer.utils.Toasts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChatBinding binding;
    private CircleImageView voice;
    private Button sendVoice;
    private EditText et;
    private ImageButton photo;
    private Button send;
    private TextView userName;
    private AVUser user;
    private AVUser mine;
    private android.support.v7.widget.Toolbar toolbar;
    private boolean isClickVoice = false;
    private MyMessageHandler myMessageHandler;
    private AVIMConversation conversation;
    private AVIMClient client;
    private boolean isCanChat = false;
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<ChatBean> datas;
    private String myAvatar;
    private String yourAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        EventBus.getDefault().register(this);

        setRecyclerView();
        setBinding();
        createChat();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myMessageHandler = new MyMessageHandler();
        AVIMMessageManager.registerMessageHandler(AVIMMessage.class, myMessageHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVIMMessageManager.unregisterMessageHandler(AVIMMessage.class, myMessageHandler);
    }


    private void setBinding() {
        /*
        绑定数据
         */
        toolbar = binding.summerChatTl;
        sendVoice = binding.summerPopChatSendVoice;
        send = binding.summerPopChatSend;
        et = binding.summerPopChatEt;
        photo = binding.summerPopChatPhoto;
        voice = binding.summerPopChatVoice;
        userName = binding.summerChatUserName;
        mine = AVUser.getCurrentUser();


        /*
        查询当前用户
         */
        Bundle bundle = getIntent().getExtras();
        AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        if (bundle != null) {
            userQuery.whereEqualTo("objectId", bundle.getString("objectId"));
            userQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null) {
                        user = list.get(0);
                        userName.setText(user.getUsername());
                        yourAvatar = user.getAVFile("avatar").getUrl();
                        myAvatar = mine.getAVFile("avatar").getUrl();
                        Log.d("chat", "done: "+yourAvatar);
                        Log.d("chat", "done: "+myAvatar);
                        adapter.setMyAvatar(myAvatar);
                        adapter.setYourAvatar(yourAvatar);
                    }
                }
            });
        }
    }

    private void setRecyclerView(){
        recyclerView = binding.summerChatRv;
        datas = new ArrayList<>();
        int[] layouts = new int[]{R.layout.summer_chat_right_str,R.layout.summer_chat_right_photo,
                R.layout.summer_chat_right_voice,R.layout.summer_chat_left_str,
                R.layout.summer_chat_right_photo,R.layout.summer_chat_right_voice};
        adapter = new ChatListAdapter(this,datas,layouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setListener() {
        //发照片
        photo.setOnClickListener(v -> {

        });

        //输入栏监听
        et.addTextChangedListener(new TextWatcheListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    send.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape_light));
                } else {
                    send.setBackground(getResources().getDrawable(R.drawable.summer_chat_send_shape));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.summer_pop_chat_send:
                sendMessage();
                break;
            case R.id.summer_pop_chat_voice:
                sendVoice();
                // TODO: 2018/8/25 加入发语音
                break;
            case R.id.summer_pop_chat_photo:
                // TODO: 2018/8/25 加入发照片
                break;
        }
    }

    private void sendVoice(){
        if (isClickVoice) {
            voice.setImageDrawable(getResources().getDrawable(R.drawable.summer_icon_voice));
            sendVoice.setVisibility(View.GONE);
            isClickVoice = false;
        } else {
            voice.setImageDrawable(getResources().getDrawable(R.drawable.summer_icon_keyboard));
            sendVoice.setVisibility(View.VISIBLE);
            isClickVoice = true;
        }
    }


    private void createChat() {

        //qnm leanCloud
        client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {//建立client连接
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    List<String> userList = new ArrayList<>();//初始化列表
                    userList.add(user.getUsername());
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

    }

    private void sendMessage() {
        AVIMTextMessage msg = new AVIMTextMessage();
        if (et.getText().length() != 0) {
            msg.setText(et.getText().toString());
            if (isCanChat) {
                conversation.sendMessage(msg, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (e == null) {
                            Log.d("chat", "done: 发送成功");
                            et.setText("");//发送成功就清空et里面的内容
                            getMyText(msg);
                        }
                    }
                });
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTextEvent(TextEvent event) {
        ChatBean bean = new ChatBean();
        bean.setMessage(event.getMessage());
        bean.setKind("你的文字");
        datas.add(bean);
        adapter.notifyDataSetChanged();
    }

    public void getMyText(AVIMTextMessage message){
        ChatBean bean = new ChatBean();
        bean.setKind("我的文字");
        bean.setMessage(message);
        datas.add(bean);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (client != null) {
            client.close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (e == null) {
                        Toasts.show("对话结束");
                    }
                }
            });
        }
    }
}
