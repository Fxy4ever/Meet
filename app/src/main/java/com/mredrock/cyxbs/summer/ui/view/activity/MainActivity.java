package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.FFragmentPagerAdapter;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.databinding.ActivityMainBinding;
import com.mredrock.cyxbs.summer.utils.ActivityManager;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.ui.view.fragment.ChatListFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.InfoFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.SummerFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    private MaterialViewPager pager;
    private ActivityMainBinding binding;
    private List<Fragment> fragments;
    private List<String> titlelist;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initMV();
        initDrawerLayout();
    }

    private void initMV(){
        pager = binding.materialViewPager;
        pager.setMaterialViewPagerListener(page -> {
            switch (page) {
                case 0:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534683102076&di=4bf7289903196cdaae00e1c63f77a2a6&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F4d517784f47a2c1da977592231aefbe656d89913.jpg");
                case 1:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534683102075&di=c5b7ed32c91ed71c5b643c8332f5fe1a&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2Fcac512f370968bb8ebe4082407a2af969a84e913.png");
                case 2:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.cyan,
                            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534683222961&di=4cea520b24364eea365c4ca9060ec8d0&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F12b08571e65489d41220bbd012fed364e6ba4d5d.jpg");
            }
            return null;
        });
        Toolbar toolbar = pager.getToolbar();

        DensityUtils.setTransparent(toolbar,this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> {

            });
        }

        ViewPager viewPager = pager.getViewPager();
        fragments = new ArrayList<>();
        titlelist = new ArrayList<>();
        fragments.add(new SummerFragment());
        fragments.add(new ChatListFragment());
        fragments.add(new InfoFragment());
        titlelist.add("广场");
        titlelist.add("消息");
        titlelist.add("联系人");

        FFragmentPagerAdapter adapter = new FFragmentPagerAdapter(getSupportFragmentManager(),fragments,titlelist);
        viewPager.setAdapter(adapter);
        pager.getPagerTitleStrip().setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

    }

    private void initDrawerLayout(){
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigation;
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
        View headerView = navigationView.inflateHeaderView(R.layout.summer_include_nav_header);
        TextView desc = headerView.findViewById(R.id.summer_nav_desc);
        TextView name = headerView.findViewById(R.id.summer_nav_nick_name);
        CircleImageView avatar = headerView.findViewById(R.id.summer_nav_avatar);

        AVUser currentUser = AVUser.getCurrentUser();
        desc.setText(currentUser.getString("desc"));
        name.setText(currentUser.getUsername());
        AVFile avFile = currentUser.getAVFile("avatar");
        if(avFile!=null){
            Glide.with(this).load(avFile.getUrl()).into(avatar);
        }

        toggle = new ActionBarDrawerToggle(this,drawerLayout,pager.getToolbar(),0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_main:
                    Toasts.show("主界面");
                    break;
                case R.id.nav_myself:
                    Toasts.show("个人信息");
                    break;
                case R.id.nav_photo:
                    Toasts.show("相册");
                    break;
                case R.id.nav_setting:
                    Toasts.show("设置");
                    break;
                case R.id.nav_about:
                    Toasts.show("关于");
                    break;
                case R.id.nav_back:
                    App.spHelper().remove("isChecked");
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    break;
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }


}
