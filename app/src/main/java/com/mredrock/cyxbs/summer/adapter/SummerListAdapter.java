package com.mredrock.cyxbs.summer.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.utils.DensityUtils;

import java.util.List;

public class SummerListAdapter  extends MultiLayoutBaseAdapter{
    private int NORMAL = 0;
    private List<AskBean> beans;


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
                LinearLayout layout = baseHolder.getView(R.id.summer_sm_item_photoParent);
                name.setText(beans.get(i).getAuthorName());
                content.setText(beans.get(i).getAskContent());
                title.setText(beans.get(i).getAskName());
                Log.d("fxy", "onBinds: "+beans.get(i).getUpdateTime());
                time.setText(beans.get(i).getUpdateTime());
                if(beans.get(i).getAskAvatar()!=null){
                    Glide.with(getContext()).load(beans.get(i).getAskAvatar()).into(avatar);
                }

//                if(beans.get(i).getPhotos()!=null){
//                    for (int j = 0; j < beans.get(i).getPhotos().size(); j++) {
//                        ImageView imageView = new ImageView(getContext());
//                        layout.addView(imageView);
//                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//                        layoutParams.width = DensityUtils.getScreenWidth(getContext())/3;
//                        imageView.setLayoutParams(layoutParams);
//                        imageView.setPadding(5,5,5,5);
//                        Glide.with(getContext()).load(beans.get(i).getPhotos().get(j).getUrl()).into(imageView);
//                    }
//                }

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
