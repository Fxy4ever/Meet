package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.base.BaseMvpActivity;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.ActivityUser2Binding;
import com.mredrock.cyxbs.summer.ui.mvp.contract.UserContract;
import com.mredrock.cyxbs.summer.ui.mvp.model.UserModel;
import com.mredrock.cyxbs.summer.ui.mvp.presenter.UserPresenter;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.GlideBlurformation;
import com.mredrock.cyxbs.summer.utils.Toasts;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseMvpActivity implements UserContract.IUserView {

    private AVUser avUser;
    private ActivityUser2Binding binding;
    private UserPresenter presenter;
    private String objectId = "";
    private List<AskBean> datas;
    private SummerListAdapter adapter;
    private RecyclerView recyclerView;
    private boolean isFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user2);
        initRV();
    }

    @Override
    public void attachPresenter() {
        presenter = new UserPresenter(new UserModel());
        presenter.attachView(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            objectId = bundle.getString("objectId");
        }
        presenter.loadUser(objectId);
        datas = new ArrayList<>();
    }

    private void initRV() {
        recyclerView = binding.summerUserRecent;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SummerListAdapter(this,datas,new int[]{R.layout.summer_item_user_rv});
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void detachPresenter() {
        presenter.detachView();
    }


    @Override
    public void setUser(AVUser avUser,boolean isFav) {
        this.avUser = avUser;
        isFavorite = isFav;
        if(!avUser.getObjectId().equals(AVUser.getCurrentUser().getObjectId())){
            binding.summerUserSend.setVisibility(View.VISIBLE);
            binding.summerUserChat.setVisibility(View.VISIBLE);
            binding.summerUserPersonInfo.setVisibility(View.VISIBLE);
        }else{
            binding.summerUserSend.setVisibility(View.GONE);
            binding.summerUserChat.setVisibility(View.GONE);
            binding.summerUserPersonInfo.setVisibility(View.GONE);
        }
        DensityUtils.setTransparent(binding.summerUserToolbar, this);
        setSupportActionBar(binding.summerUserToolbar);
        CircleImageView avatar = binding.summerUserAvatar;

        if(avUser.getBoolean("isAdmin")){
            binding.summerUserAdmin.setVisibility(View.VISIBLE);
        }
        binding.summerUserToolbarLayout.setTitle(avUser.getUsername());
        binding.summerUserSex.setText(avUser.getString("sex"));
        if(isFav) binding.summerUserSend.setText("已关注");
        binding.summerUserSend.setOnClickListener(v->{
            if(!isFavorite){
                AVUser.getCurrentUser().followInBackground(avUser.getObjectId(), new FollowCallback() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if(e == null){
                            Toasts.show("关注成功");
                            binding.summerUserSend.setText("已关注");
                            isFavorite = true;
                        }
                    }
                });
            }else{
                AVUser.getCurrentUser().unfollowInBackground(avUser.getObjectId(), new FollowCallback() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if(e==null){
                            binding.summerUserSend.setText("关注");
                            isFavorite = false;
                        }
                    }
                });

            }
        });

        binding.summerUserMoney.setText("财富为："+avUser.getInt("money"));

        if (avUser.getAVFile("avatar") != null) {
            Glide.with(this)
                    .load(avUser.getAVFile("avatar").getUrl())
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().bitmapTransform(new GlideBlurformation(this)))
                    .into(binding.summerUserBg);

            Glide.with(this)
                    .load(avUser.getAVFile("avatar").getUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.summer_user_avatar).error(R.drawable.summer_user_avatar))
                    .into(avatar);
        }

        binding.summerUserAppBar.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (verticalOffset == 0) {
                avatar.setVisibility(View.VISIBLE);
                if(!avUser.getObjectId().equals(AVUser.getCurrentUser().getObjectId())){
                    binding.summerUserSend.setVisibility(View.VISIBLE);
//                    binding.summerUserChat.setVisibility(View.VISIBLE);
                    binding.summerUserPersonInfo.setVisibility(View.VISIBLE);
                }
                binding.summerUserMoney.setVisibility(View.VISIBLE);
                binding.summerUserSex.setVisibility(View.VISIBLE);
                if(avUser.getBoolean("isAdmin")){
                    binding.summerUserAdmin.setVisibility(View.VISIBLE);
                }
            } else {
                avatar.setVisibility(View.GONE);
                binding.summerUserMoney.setVisibility(View.GONE);
                binding.summerUserSend.setVisibility(View.GONE);
//                binding.summerUserChat.setVisibility(View.GONE);
                binding.summerUserSex.setVisibility(View.GONE);
                binding.summerUserAdmin.setVisibility(View.GONE);
                binding.summerUserPersonInfo.setVisibility(View.GONE);
            }
        });
        presenter.loadData(avUser);

        binding.summerUserToolbar.setNavigationOnClickListener(v->{
            finish();
        });

        binding.summerUserChat.setOnClickListener(v->{
            Intent intent = new Intent(UserActivity.this,ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("objectId",avUser.getObjectId());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }




    @Override
    public void setData(List<AVObject> beans) {
        datas.clear();
        for (int i = 0; i < beans.size(); i++) {
            AskBean bean = new AskBean();
            bean.setAskName(beans.get(i).getString("askName"));
            bean.setPhoto(beans.get(i).getAVFile("photo"));
            bean.setAskContent(beans.get(i).getString("askContent"));
            bean.setVoice(beans.get(i).getAVFile("voice"));
            bean.setAskInfo(beans.get(i));
            bean.setUpdatedAt(DateUtil.getCurDate(beans.get(i).getUpdatedAt()));
            bean.setObjectId(beans.get(i).getObjectId());
            bean.setAuthor(beans.get(i).getAVUser("author"));
            datas.add(bean);
            if(i ==datas.size()-1){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public Context getContext () {
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
    }
}
