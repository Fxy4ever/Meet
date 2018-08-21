package com.mredrock.cyxbs.summer.ui.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
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
import com.mredrock.cyxbs.summer.utils.Glide4Engine;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.ui.view.fragment.ChatListFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.InfoFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.SummerFragment;
import com.mredrock.cyxbs.summer.utils.UriUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
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

    private RxPermissions rxPermissions;
    private boolean isAskPer = false;

    private int REQUEST_CODE_CHOOSE = 10086;
    private List<Uri> selects;
    private AVFile photo;
    private String name="";
    private String path="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        askPermissions();
        initMV();
        initDrawerLayout();
    }

    private void initMV(){
        pager = binding.materialViewPager;
        pager.setMaterialViewPagerListener(page -> {
            switch (page) {
                case 0:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.blue,getResources().getDrawable(R.drawable.summer_main_header1));

                case 1:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.green,
                            getResources().getDrawable(R.drawable.summer_main_header2));
                case 2:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.cyan,
                            getResources().getDrawable(R.drawable.summer_main_header3));
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
        viewPager.setOffscreenPageLimit(4);
        viewPager.onSaveInstanceState();

    }

    @SuppressLint({"CheckResult"})
    private void askPermissions(){
        rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.RECORD_AUDIO
                )
                .subscribe(permission -> {
                    if(permission.granted){
                        isAskPer = true;
                    }else{
                        Toasts.show("未获取权限");
                    }
                });
    }

    private void initDrawerLayout(){
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigation;
        navigationView.getChildAt(0).setVerticalScrollBarEnabled(false);
        View headerView = navigationView.inflateHeaderView(R.layout.summer_include_nav_header);
        TextView desc = headerView.findViewById(R.id.summer_nav_desc);
        TextView name = headerView.findViewById(R.id.summer_nav_nick_name);
        CircleImageView avatar = headerView.findViewById(R.id.summer_nav_avatar);

        avatar.setOnClickListener(v -> {
            if(isAskPer){
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(DensityUtils.getScreenWidth(this)/3)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .theme(R.style.Matisse_Dracula)
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });


        AVUser currentUser = AVUser.getCurrentUser();
        desc.setText(currentUser.getString("desc"));
        name.setText(currentUser.getUsername());
        AVFile avFile = currentUser.getAVFile("avatar");
        if(avFile!=null){
            Glide.with(this).load(avFile.getUrl()).into(avatar);
        }
        selects = new ArrayList<>();
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
                    AVUser.logOut();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            selects = Matisse.obtainResult(data);
            try{
                    name = System.currentTimeMillis()/1000 + ".jpg";
                    path = UriUtil.getRealPathFromUri(this,selects.get(0));
                    photo = AVFile.withAbsoluteLocalPath(name,path);

                AVUser.getCurrentUser().put("avatar",photo);
                AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null) {
                            Toasts.show("头像修改成功");
                        }
                    }
                });
            }catch (IOException e){
                Toasts.show("文件上传");
            }
        }
    }

}
