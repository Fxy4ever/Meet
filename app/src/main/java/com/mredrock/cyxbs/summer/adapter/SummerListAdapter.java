package com.mredrock.cyxbs.summer.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.ui.view.activity.AskDetailActivity;
import com.mredrock.cyxbs.summer.ui.view.activity.UserActivity;
import com.mredrock.cyxbs.summer.utils.AudioPlayer;
import com.mredrock.cyxbs.summer.utils.AudioUtil;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;

import java.util.List;

public class SummerListAdapter  extends MultiLayoutBaseAdapter{
    private int NORMAL = 0;
    private List<AskBean> beans;
    public static AskBean bean;

    private int lastAnimatedPosition=-1;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    public SummerListAdapter(Context context, List<AskBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.beans = data;
    }

    @Override
    public int getItemType(int i) {
        return NORMAL;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                runEnterAnim(baseHolder.itemView,i);
                TextView name = baseHolder.getView(R.id.summer_sm_item_name);
                TextView content = baseHolder.getView(R.id.summer_sm_item_content);
                TextView title = baseHolder.getView(R.id.summer_sm_item_title);
                TextView time = baseHolder.getView(R.id.summer_sm_item_time);
                ImageView avatar = baseHolder.getView(R.id.summer_sm_item_avatar);
                ImageView img = baseHolder.getView(R.id.summer_sm_item_img);
                ImageButton play = baseHolder.getView(R.id.summer_sm_item_play);
                TextView playTime = baseHolder.getView(R.id.summer_sm_item_play_time);
                CardView parent = baseHolder.getView(R.id.summer_sm_parent);

                name.setText(beans.get(i).getAuthor().getUsername());
                content.setText(beans.get(i).getAskContent());
                title.setText(beans.get(i).getAskName());
                time.setText(beans.get(i).getUpdatedAt());
                if(beans.get(i).getAuthor().getAVFile("avatar")!=null){
                    Glide.with(getContext()).load(beans.get(i).getAuthor().getAVFile("avatar").getUrl()).apply(new RequestOptions().override(300,300)).into(avatar);
                }
                avatar.setOnClickListener(v->{
                    Intent intent = new Intent(getContext(),UserActivity.class);
                    Bundle bundle = new Bundle();
                    Log.d("fxy", "onBinds: "+beans.get(i).getAuthor().getObjectId());
                    bundle.putString("objectId",beans.get(i).getAuthor().getObjectId());
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                });

                if(beans.get(i).getPhoto()!=null){
                            Glide
                            .with(getContext()).asBitmap()
                            .load(beans.get(i).getPhoto().getUrl())
                            .thumbnail(0.1f)
                                    .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                                    .into(img);

                            img.setOnClickListener(v -> {
                                Dialog dialog = DialogBuilder.buildImgDialog(getContext(),beans.get(i).getPhoto().getUrl());
                                dialog.show();
                            });
                }else{
                    Glide.with(getContext()).load(R.drawable.summer_place_img).apply(new RequestOptions().override(100,100)).into(img);
                }

                AudioUtil.setAudio(getContext(),beans.get(i).getVoice(),playTime,play);

                baseHolder.itemView.setOnClickListener(v -> {
                        bean = beans.get(i);
                        Intent intent = new Intent(getContext(), AskDetailActivity.class);
                        getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(),parent,"share").toBundle());
                });

                break;
        }
    }



    private void runEnterAnim(View view,int position){
        if(animationsLocked) return;//有item才开始画

        if(position > lastAnimatedPosition){
            view.setAlpha(0.f);//初始设置透明
            view.setTranslationX(-500);//初始位置
            view.animate()
                    .translationX(0)//移动位置
                    .alpha(1.f)//渐变的透明的
                    .setStartDelay(delayEnterAnimation ? 100 * (position) : 0)//item位置设置延迟时间
                    .setInterpolator(new DecelerateInterpolator(0.5f))//插值器
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;//屏幕下方的item滑动是没有效果的
                        }
                    }).start();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
