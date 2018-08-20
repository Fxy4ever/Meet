package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.databinding.SummerFragmentChatBinding;

/**
 * 用来显示聊天列表
 */
public class ChatListFragment extends Fragment {
    private SummerFragmentChatBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_chat,container,false);
        return binding.getRoot();
    }
}
