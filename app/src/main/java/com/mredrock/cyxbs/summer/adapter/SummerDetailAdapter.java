package com.mredrock.cyxbs.summer.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.ui.view.activity.UserActivity;
import com.mredrock.cyxbs.summer.utils.AudioPlayer;
import com.mredrock.cyxbs.summer.utils.AudioUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SummerDetailAdapter extends MultiLayoutBaseAdapter {
    private int NORMAL = 0;
    private List<CommentBean> list;

    public SummerDetailAdapter(Context context, List<CommentBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.list = data;
    }

    @Override
    public int getItemType(int i) {
        return NORMAL;
    }

    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                if(list.get(i).getUser()!=null){
                    CircleImageView avatar = baseHolder.getView(R.id.summer_comment_avatar);
                    TextView name = baseHolder.getView(R.id.summer_comment_name);
                    TextView content = baseHolder.getView(R.id.summer_comment_content);
                    ImageButton play = baseHolder.getView(R.id.summer_comment_play);
                    TextView playTime = baseHolder.getView(R.id.summer_comment_playTime);
                    TextView time = baseHolder.getView(R.id.summer_comment_time);

                    time.setText(list.get(i).getTime());

                    AVUser user = list.get(i).getUser();
                    if(user.getAVFile("avatar")!=null){
                        Glide.with(getContext()).load(user.getAVFile("avatar").getUrl()).apply(new RequestOptions().override(200,200)).into(avatar);
                    }
                    name.setText(user.getUsername());
                    if(list.get(i).getContent()!=null&&!list.get(i).getContent().equals("")){
                        content.setText(list.get(i).getContent());
                    }

                    avatar.setOnClickListener(v->{
                        Intent intent = new Intent(getContext(),UserActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("objectId",list.get(i).getUser().getObjectId());
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                    });

                    AudioUtil.setAudio(getContext(),list.get(i).getVoice(),playTime,play);

                }
                break;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
