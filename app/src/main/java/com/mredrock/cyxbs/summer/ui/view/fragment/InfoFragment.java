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
import com.mredrock.cyxbs.summer.databinding.SummerFragmentInfoBinding;

/**
 * 好友信息Fragment
 */
public class InfoFragment extends Fragment{
    private SummerFragmentInfoBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_info,container,false);
        return binding.getRoot();
    }
}
