package com.mredrock.cyxbs.summer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.ChatUserBean;
import com.mredrock.cyxbs.summer.ui.view.activity.ChatActivity;
import com.mredrock.cyxbs.summer.ui.view.activity.UserActivity;
import com.mredrock.cyxbs.summer.utils.DateUtil;

import java.util.Date;
import java.util.List;

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
                CircleImageView avatar = baseHolder.getView(R.id.summer_chat_list_avatar);
                TextView name = baseHolder.getView(R.id.summer_chat_list_name);
                TextView content = baseHolder.getView(R.id.summer_chat_list_content);
                TextView time = baseHolder.getView(R.id.summer_chat_list_time);
                Glide.with(getContext()).load(beans.get(i).getAvUser().getAVFile("avatar").getUrl()).into(avatar);
                name.setText(beans.get(i).getAvUser().getUsername());
                time.setText("更新于"+DateUtil.getCurDate(beans.get(i).getConversation().getDate("updatedAt")));
                baseHolder.itemView.setOnClickListener(v->{
                    Intent intent = new Intent(getContext(),ChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("objectId",beans.get(i).getAvUser().getObjectId());
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                });
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
