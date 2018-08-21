package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.SummerFragmentListBinding;
import com.mredrock.cyxbs.summer.ui.contract.SummerContract;
import com.mredrock.cyxbs.summer.ui.model.SummerModel;
import com.mredrock.cyxbs.summer.ui.presenter.SummerPresenter;
import com.mredrock.cyxbs.summer.ui.view.activity.AskFriendsActivity;
import com.mredrock.cyxbs.summer.ui.widget.ScrollLinearLayoutManager;
import com.mredrock.cyxbs.summer.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用来显示所有的随机信息
 */
public class SummerFragment  extends BaseFragment implements SummerContract.ISummerView{
    private RecyclerView recyclerView;
    private SummerFragmentListBinding binding;
    private List<AskBean> datas;
    public static SummerPresenter presenter;
    private SummerListAdapter adapter;
    private ScrollLinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_list,container,false);
        attachView();
        initRV();
        initFab();
        return binding.getRoot();
    }


    public void attachView() {
        presenter = new SummerPresenter(new SummerModel());
        presenter.attachView(this);
        presenter.start();
    }

    @Override
    public void detacheView() {
        presenter.detachView();
    }

    private void initRV(){
        recyclerView = binding.summerListRv;
        datas = new ArrayList<>();
        manager = new ScrollLinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());//很关键
        binding.summerSmRefresh.setOnRefreshListener(refreshLayout -> {
            manager.setScrollEnabled(false);
            presenter.start();
            binding.summerSmRefresh.finishRefresh(1000);
        }).setOnLoadMoreListener(refreshLayout -> {
//            manager.setScrollEnabled(false);
//            presenter.loadMore();
//            binding.summerSmRefresh.finishLoadMore(1000);
            binding.summerSmRefresh.finishLoadMoreWithNoMoreData();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFab(){
        binding.summerGcFab.setOnClickListener( v -> {
            startActivity(new Intent(getActivity(), AskFriendsActivity.class));
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.out_to_top,R.anim.in_from_bottm);
        });
    }

    @Override
    public void setData(List<AVObject> data) {
        datas.clear();
        for (int i = 0; i < data.size(); i++) {
            AskBean bean = new AskBean();
            bean.setAskName(data.get(i).getString("askName"));
            bean.setPhoto(data.get(i).getAVFile("photo"));
            bean.setAskContent(data.get(i).getString("askContent"));
            bean.setVoice(data.get(i).getAVFile("voice"));
            bean.setAskInfo(data.get(i));
            bean.setUpdatedAt(DateUtil.getCurDate(data.get(i).getUpdatedAt()));
            bean.setObjectId(data.get(i).getObjectId());
            AVObject ask = AVObject.createWithoutData("askInfo",data.get(i).getObjectId());
            ask.fetchInBackground("author", new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if(e==null){
                        AVUser user = avObject.getAVUser("author");
                        bean.setAuthor(user);
                        datas.add(bean);
                        binding.summerSmRefresh.finishRefresh();
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
        adapter = new SummerListAdapter(getActivity(),datas,new int[]{R.layout.summer_item_ask_rv});
        recyclerView.setAdapter(adapter);
        manager.setScrollEnabled(true);
    }

    @Override
    public void setMoreData(List<AVObject> data) {
        for (int i = 0; i < data.size(); i++) {
            AskBean bean = new AskBean();
            bean.setAskName(data.get(i).getString("askName"));
            bean.setObjectId(data.get(i).getObjectId());
            bean.setPhoto(data.get(i).getAVFile("photo"));
            bean.setAskContent(data.get(i).getString("askContent"));
            bean.setVoice(data.get(i).getAVFile("voice"));
            bean.setAskInfo(data.get(i));
            bean.setUpdatedAt(DateUtil.getCurDate(data.get(i).getUpdatedAt()));
            AVObject ask = AVObject.createWithoutData("askInfo",data.get(i).getObjectId());
            ask.fetchInBackground("author", new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if(e==null){
                        AVUser user = avObject.getAVUser("author");
                        bean.setAuthor(user);
                        datas.add(bean);
                    }
                }
            });
        }
        binding.summerSmRefresh.finishLoadMore();

        adapter.notifyDataSetChanged();

        manager.setScrollEnabled(true);
    }
}
