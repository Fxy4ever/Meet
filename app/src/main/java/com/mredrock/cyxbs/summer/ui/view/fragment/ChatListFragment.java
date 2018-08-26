package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimeUtils;
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
import com.mredrock.cyxbs.summer.ui.view.activity.MainActivity;
import com.mredrock.cyxbs.summer.utils.NotificationUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static android.support.v4.app.NotificationCompat.FLAG_AUTO_CANCEL;

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
    public static final String TAG = "ChatListFragment";
    private boolean isFirstResume = true;
    private Disposable disposable;

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
                refreshLayout.finishRefresh(1000);
                data.clear();
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
