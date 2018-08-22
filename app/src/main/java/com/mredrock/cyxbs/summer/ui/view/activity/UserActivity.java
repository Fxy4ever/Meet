package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.base.BaseMvpActivity;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.ActivityUser2Binding;
import com.mredrock.cyxbs.summer.ui.contract.UserContract;
import com.mredrock.cyxbs.summer.ui.model.UserModel;
import com.mredrock.cyxbs.summer.ui.presenter.UserPresenter;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.GlideBlurformation;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new SummerListAdapter(this,datas,new int[]{R.layout.summer_item_ask_rv});
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void detachPresenter() {
        presenter.detachView();
    }


    @Override
    public void setUser(AVUser avUser) {
        this.avUser = avUser;
        if(!avUser.getObjectId().equals(AVUser.getCurrentUser().getObjectId())){
            binding.summerUserSend.setVisibility(View.VISIBLE);
        }
        DensityUtils.setTransparent(binding.summerUserToolbar, this);
        setSupportActionBar(binding.summerUserToolbar);
        CircleImageView avatar = binding.summerUserAvatar;

        binding.summerUserToolbarLayout.setTitle(avUser.getUsername());

        if (avUser.getAVFile("avatar") != null) {
            Glide.with(this)
                    .load(avUser.getAVFile("avatar").getUrl())
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().bitmapTransform(new GlideBlurformation(this)))
                    .into(binding.summerUserBg);

            Glide.with(this)
                    .load(avUser.getAVFile("avatar").getUrl())
                    .into(avatar);
        }

        binding.summerUserAppBar.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (verticalOffset == 0) {
                avatar.setVisibility(View.VISIBLE);
                if(!avUser.getObjectId().equals(AVUser.getCurrentUser().getObjectId())){
                    binding.summerUserSend.setVisibility(View.VISIBLE);
                }
            } else {
                avatar.setVisibility(View.GONE);
                binding.summerUserSend.setVisibility(View.GONE);
            }
        });
        presenter.loadData(avUser);

        binding.summerUserToolbar.setNavigationOnClickListener(v->{
            finish();
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
            AVObject ask = AVObject.createWithoutData("askInfo", beans.get(i).getObjectId());
            ask.fetchInBackground("author", new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (e == null) {
                        AVUser user = avObject.getAVUser("author");
                        bean.setAuthor(user);
                        datas.add(bean);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public Context getContext () {
        return this;
    }

}
