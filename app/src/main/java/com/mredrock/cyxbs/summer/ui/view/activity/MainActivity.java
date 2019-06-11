package com.mredrock.cyxbs.summer.ui.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.FFragmentPagerAdapter;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.bean.InfoBean;
import com.mredrock.cyxbs.summer.databinding.ActivityMainBinding;
import com.mredrock.cyxbs.summer.ui.view.fragment.ChatListFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.InfoFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.SearchFragment;
import com.mredrock.cyxbs.summer.ui.view.fragment.SummerFragment;
import com.mredrock.cyxbs.summer.utils.ActivityManager;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.Glide4Engine;
import com.mredrock.cyxbs.summer.utils.HttpUtilManager;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.utils.UriUtil;
import com.mredrock.cyxbs.summer.utils.network.ApiGenerator;
import com.mredrock.cyxbs.summer.utils.network.ApiService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity implements LifecycleOwner {

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
    private CircleImageView avatar;
    private Dialog changeDesc;
    private FeedbackAgent agent;
    public static AVIMClient client;
    public static String token;

    private AVUser currentUser = AVUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        askPermissions();
        setFeedBack();
        initMV();
        initDrawerLayout();
    }

    private void setFeedBack(){
        agent = new FeedbackAgent(this);
        agent.sync();
    }

    @SuppressLint("CheckResult")
    private void initMV(){
        pager = binding.materialViewPager;
        pager.setMaterialViewPagerListener(page -> {
            switch (page) {
                case 0:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.cardview_dark_background,getResources().getDrawable(R.drawable.summer_main_header1));

//                case 1:
//                    return HeaderDesign.fromColorResAndDrawable(
//                            R.color.green,
//                            getResources().getDrawable(R.drawable.summer_main_header4));
                case 1:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.cyan,
                            getResources().getDrawable(R.drawable.summer_main_header1));
                case 2:
                    return HeaderDesign.fromColorResAndDrawable(
                            R.color.blue,
                            getResources().getDrawable(R.drawable.summer_main_header4));
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
//        fragments.add(new ChatListFragment());
//        fragments.add(new SearchFragment());
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setKind("粉丝");
        InfoFragment infoFragment1 = new InfoFragment();
        infoFragment1.setKind("关注");
        fragments.add(infoFragment);
        fragments.add(infoFragment1);
        titlelist.add("广场");
//        titlelist.add("消息");
//        titlelist.add("发现");
        titlelist.add("我的粉丝");
        titlelist.add("我的关注");



        FFragmentPagerAdapter adapter = new FFragmentPagerAdapter(getSupportFragmentManager(),fragments,titlelist);
        viewPager.setAdapter(adapter);
        pager.getPagerTitleStrip().setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.onSaveInstanceState();

//        //注册
//        HttpUtilManager
//                .getInstance()
//                .register(currentUser.getObjectId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(infoBean -> {
//                    if(infoBean.getStatus()==200){
//                        Log.d("meet_register","成功");
//                    }
//                });
//        HttpUtilManager.getInstance()
//                .getToken(currentUser.getObjectId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(infoBean -> {
//                    if(infoBean.getStatus()==200){
//                        Log.d("meet_register","刷新token成功");
//                        token = infoBean.getData().getToken();
//                    }
//                });

        client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {
            @SuppressLint("CheckResult")
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if(e==null){
                    Toasts.show("登陆成功");
                }
            }
        });
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
        /*
        初始化header的控件
         */
        View headerView = navigationView.inflateHeaderView(R.layout.summer_include_nav_header);
        TextView desc = headerView.findViewById(R.id.summer_nav_desc);
        TextView name = headerView.findViewById(R.id.summer_nav_nick_name);
        avatar = headerView.findViewById(R.id.summer_nav_avatar);
        ImageButton desc_change = headerView.findViewById(R.id.summer_nav_change);
        /*
        初始化改变签名的dialog
         */
        changeDesc = new Dialog(this);
        changeDesc.setContentView(R.layout.summer_change_dialog);
        changeDesc.setCancelable(true);
        EditText changeEt = changeDesc.findViewById(R.id.summer_change_et);
        Button changeBack = changeDesc.findViewById(R.id.summer_change_back);
        Button changeCommit = changeDesc.findViewById(R.id.summer_change_commit);
        changeBack.setOnClickListener(v->{
            changeDesc.cancel();
            changeEt.setText("");
        });
        /*
        修改个性签名
         */
        changeCommit.setOnClickListener(v->{
            String descContent = changeEt.getText().toString();
            if(descContent.length()>0){
                AVUser user = AVUser.getCurrentUser();
                user.put("desc",descContent);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            Toasts.show("修改成功");
                            changeEt.setText("");
                            desc.setText(descContent);
                            changeDesc.cancel();
                        }
                    }
                });
            }
        });

        desc_change.setOnClickListener(v->{
            changeDesc.show();
        });

        /*
        头像选择
         */
        avatar.setOnClickListener(v -> {
            if(isAskPer){
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .capture(true)  // 开启相机，和 captureStrategy 一并使用否则报错
                        .captureStrategy(new CaptureStrategy(true,"com.mredrock.cyxbs.summer.fileprovider"))
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

        if(currentUser.getString("desc")!=null){
            desc.setText(currentUser.getString("desc"));
        }else{
            desc.setText("还没有签名噢!");
        }
        name.setText(currentUser.getUsername());
        AVFile avFile = currentUser.getAVFile("avatar");
        if(avFile!=null){
            Glide.with(this).load(avFile.getUrl()).apply(new RequestOptions().override(200,200)).into(avatar);
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
                    Intent intent = new Intent(MainActivity.this,UserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("objectId",currentUser.getObjectId());
                    intent.putExtras(bundle);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,avatar,"summer_toUser").toBundle());
                    break;
                case R.id.nav_about:
                    startActivity(new Intent(MainActivity.this,AboutActivity.class));
                    overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                    break;
                case R.id.nav_back:
                    App.spHelper().remove("isChecked");
                    App.spHelper().remove("SetQuestion_q1");
                    App.spHelper().remove("SetQuestion_q2");
                    App.spHelper().remove("SetQuestion_q3");
                    App.spHelper().remove("SetQuestion_a1");
                    App.spHelper().remove("SetQuestion_a2");
                    App.spHelper().remove("SetQuestion_a3");
                    App.spHelper().remove("SetQuestion_note");
                    AVUser.logOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    break;

                case R.id.nav_change:
                    startActivity(new Intent(MainActivity.this,ChangeInfoActivity.class));
                    break;

//                case R.id.nav_question:
//                    startActivity(new Intent(MainActivity.this,SetQuestionActivity.class));
//                    break;
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
                    if(selects.get(0).toString().contains("provider")){
                        path = UriUtil.getFPUriToPath(this,selects.get(0));
                        photo = AVFile.withAbsoluteLocalPath(name,path);
                    }
                    else{
                        path = UriUtil.getRealPathFromUri(this,selects.get(0));
                        photo = AVFile.withAbsoluteLocalPath(name,path);
                    }

                AVUser.getCurrentUser().put("avatar",photo);
                AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null) {
                            Toasts.show("头像修改成功");
                            Glide.with(App.getContext()).load(photo.getUrl()).apply(new RequestOptions().override(200,200)).into(avatar);
                            SummerFragment.presenter.start();
                        }
                    }
                });
            }catch (IOException e){
                Toasts.show("文件上传失败");
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
