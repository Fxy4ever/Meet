package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.InfoListAdapter;
import com.mredrock.cyxbs.summer.databinding.SummerFragmentInfoBinding;
import com.mredrock.cyxbs.summer.ui.mvvm.model.InfoViewModel;
import com.mredrock.cyxbs.summer.ui.view.activity.App;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 好友信息Fragment 利用mvvm架构
 */
public class InfoFragment extends Fragment implements LifecycleOwner{
    private SummerFragmentInfoBinding binding;
    private String kind;
    private RecyclerView recyclerView;
    private InfoListAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private List<AVUser> data;
    private InfoViewModel model;

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_info,container,false);
        init();
        return binding.getRoot();
    }

    private void init(){
        recyclerView = binding.summerInfoRv;
        refreshLayout = binding.summerInfoRefreshLayout;
        data = new ArrayList<>();
        adapter = new InfoListAdapter(getContext(),data,new int[]{R.layout.summer_item_info});
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        refreshLayout.setOnRefreshListener(v->{
           refreshLayout.finishRefresh(1000);
            if(kind.equals("粉丝")){
                data.clear();
                model.setFenSi(AVUser.getCurrentUser());
            }else{
                data.clear();
                model.setGuanZhu(AVUser.getCurrentUser());
            }
        }).setOnLoadMoreListener(v->{
            refreshLayout.finishLoadMoreWithNoMoreData();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(kind!=null){
            if(kind.equals("粉丝")){
                model = ViewModelProviders.of(this).get(InfoViewModel.class);
                model.setFenSi(AVUser.getCurrentUser());
            }else{
                model = ViewModelProviders.of(this).get(InfoViewModel.class);
                model.setGuanZhu(AVUser.getCurrentUser());
            }
            observeModel(model);
        }
    }



    private void observeModel(InfoViewModel model){
        if(kind.equals("粉丝")){
            model.getFollowerList().observeForever(avUsers -> {
                Log.d("info", "observeModel: ");
                refreshLayout.finishRefresh();
                if(avUsers!=null){
                    data.addAll(avUsers);
                    adapter.notifyDataSetChanged();
                }
            });
        }else{
            model.getFolloweeList().observeForever(avUsers -> {
                if(avUsers!=null){
                    data.clear();
                    data.addAll(avUsers);
                    adapter.notifyDataSetChanged();
                }
                refreshLayout.finishRefresh();
            });
        }
    }
}
