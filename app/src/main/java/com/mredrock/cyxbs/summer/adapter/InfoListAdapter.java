package com.mredrock.cyxbs.summer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.ui.view.activity.UserActivity;
import com.mredrock.cyxbs.summer.utils.DensityUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoListAdapter extends MultiLayoutBaseAdapter {
    private List<AVUser> data;
    private int NORMAL = 0;

    public InfoListAdapter(Context context, List<AVUser> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.data = data;
    }

    @Override
    public int getItemType(int i) {
        return NORMAL;
    }

    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                ViewGroup.LayoutParams params = baseHolder.itemView.getLayoutParams();
                params.width = DensityUtils.getScreenWidth(getContext())/2;
                baseHolder.itemView.setLayoutParams(params);
                CircleImageView avatar = baseHolder.getView(R.id.summer_info_avatar);
                TextView name = baseHolder.getView(R.id.summer_info_name);
                TextView desc = baseHolder.getView(R.id.summer_info_desc);
                if(data.get(i).getAVFile("avatar")!=null) {
                    Glide
                            .with(getContext())
                            .load(data.get(i).getAVFile("avatar").getUrl())
                            .into(avatar);
                }
                desc.setText(data.get(i).getString("desc"));
                name.setText(data.get(i).getUsername());
                baseHolder.itemView.setOnClickListener(v->{
                    Intent intent = new Intent(getContext(),UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("objectId",data.get(i).getObjectId());
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
