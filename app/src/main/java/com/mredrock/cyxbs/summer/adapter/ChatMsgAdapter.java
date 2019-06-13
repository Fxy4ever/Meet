package com.mredrock.cyxbs.summer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.google.gson.Gson;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.ChatUserBean;
import com.mredrock.cyxbs.summer.bean.MsgType;
import com.mredrock.cyxbs.summer.ui.view.activity.ChatActivity;
import com.mredrock.cyxbs.summer.ui.view.activity.MainActivity;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMsgAdapter extends MultiLayoutBaseAdapter {
    private List<ChatUserBean> beans;


    public ChatMsgAdapter(Context context, List<ChatUserBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.beans = data;
    }

    @Override
    public int getItemType(int i) {
        return 0;
    }

    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                if(beans.get(i).getConversation()!=null){
                    CircleImageView avatar = baseHolder.getView(R.id.summer_chat_list_avatar);
                    TextView name = baseHolder.getView(R.id.summer_chat_list_name);
                    TextView content = baseHolder.getView(R.id.summer_chat_list_content);
                    TextView time = baseHolder.getView(R.id.summer_chat_list_time);
                    TextView unReadCount = baseHolder.getView(R.id.summer_chat_list_count);
                    if(beans.get(i).getAvUser().getAVFile("avatar")!=null){
                        Glide.with(getContext()).load(beans.get(i).getAvUser().getAVFile("avatar").getUrl()).into(avatar);
                    }
                    name.setText(beans.get(i).getAvUser().getUsername());
                    time.setText("更新于"+DateUtil.getCurDate(beans.get(i).getConversation().getDate("updatedAt")));

                    baseHolder.itemView.setOnClickListener(v->{
                        unReadCount.setVisibility(View.GONE);
                        unReadCount.setText("");
                        Intent intent = new Intent(getContext(),ChatActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("objectId",beans.get(i).getAvUser().getObjectId());
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                        ((Activity)getContext()).overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                        });
                    baseHolder.itemView.setOnLongClickListener(v -> {
                        Dialog dialog  = new DialogBuilder(getContext())
                                .title("是否删除该对话")
                                .sureText("是")
                                .message("点击方框外部取消")
                                .setCancelable(true)
                                .setSureOnClickListener(v1 -> {
                                    beans.remove(i);
                                    notifyDataSetChanged();
                                }).build();
                        dialog.show();
                        return false;
                    });

                    AVIMConversationsQuery query = MainActivity.client.getConversationsQuery();
                    query.whereEqualTo("objectId",beans.get(i).getConversationId());
                    query.setWithLastMessagesRefreshed(true);

                    query.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);//这里好坑！竟然还给我缓存了 我就说怎么拿不到实时数据
                    query.findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> list, AVIMException e) {
                            if(e==null){
                                if(list!=null){

                                    AVIMMessage message = list.get(0).getLastMessage();
                                    String cont = message.getContent();
                                    MsgType msgType = new Gson().fromJson(cont,MsgType.class);
                                    if(msgType!=null){
                                        if(msgType.get_lctype()==-1){
                                            content.setText(msgType.get_lctext());
                                        }else if(msgType.get_lctype()==-2){
                                            content.setText("[图片]");
                                        }else{
                                            content.setText("[语音]");
                                        }
                                    }
                                        if(list.get(0).getUnreadMessagesCount()>0){
                                            unReadCount.setVisibility(View.VISIBLE);
                                            unReadCount.setText(list.get(0).getUnreadMessagesCount()+"");
                                        }
                                }
                            }else {
                                Log.d("chat", "done: "+e.getMessage());
                            }
                        }
                    });
                }else{
                   baseHolder.itemView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
