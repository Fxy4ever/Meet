package com.mredrock.cyxbs.summer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FFragmentPagerAdapter extends FragmentPagerAdapter {
    private List fragmentList;
    private List titleList;

    public  FFragmentPagerAdapter(FragmentManager fm, List fragmentList, List titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) titleList.get(position);
    }
}
