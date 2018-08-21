package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        detacheView();
    }


    public abstract void detacheView();
}
