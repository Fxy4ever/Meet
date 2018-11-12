package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mredrock.cyxbs.summer.R;

/**
 * create by:Fxymine4ever
 * time: 2018/11/12
 */
public class SearchFragment extends Fragment {
    private View parent;
    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.summer_fragment_search,container,false);
        return parent;
    }
}
