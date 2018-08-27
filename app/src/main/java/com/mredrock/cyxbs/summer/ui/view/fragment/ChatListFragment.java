package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.ChatMsgAdapter;
import com.mredrock.cyxbs.summer.bean.ChatUserBean;
import com.mredrock.cyxbs.summer.databinding.SummerFragmentChatBinding;
import com.mredrock.cyxbs.summer.event.EmptyEvent;
import com.mredrock.cyxbs.summer.ui.mvvm.model.ChatListViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来显示聊天列表
 */
public class ChatListFragment extends Fragment {
    private SummerFragmentChatBinding binding;
    private ChatListViewModel model;
    private RecyclerView recyclerView;
    private ChatMsgAdapter adapter;
    private List<ChatUserBean> data;
    private SmartRefreshLayout refreshLayout;
    private boolean isFirst  = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_chat,container,false);
        EventBus.getDefault().register(this);
        setRecyclerView();
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(ChatListViewModel.class);
        observe(model.getList());
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            data.clear();
            adapter.notifyDataSetChanged();
            model.loadData();
        }).setOnLoadMoreListener(RefreshLayout::finishLoadMoreWithNoMoreData);
    }

    public void setRecyclerView(){
        recyclerView = binding.summerChatListRv;
        refreshLayout = binding.summerChatListSrl;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        data = new ArrayList<>();
        adapter = new ChatMsgAdapter(getContext(),data,new int[]{R.layout.summer_item_chat_list});
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadData(EmptyEvent event){
            model.loadData();
    }




    private void observe(LiveData<List< ChatUserBean>> list){
        list.observe(this, chatUserBeans -> {
            if (chatUserBeans != null) {
                data.clear();
                refreshLayout.finishRefresh(1000);
                data.addAll(chatUserBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
