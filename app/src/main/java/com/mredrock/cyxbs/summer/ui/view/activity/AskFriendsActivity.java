package com.mredrock.cyxbs.summer.ui.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.base.BaseMvpActivity;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.ActivityAskFriendsBinding;
import com.mredrock.cyxbs.summer.ui.contract.AskFriednsContract;
import com.mredrock.cyxbs.summer.ui.model.AskFriendsModel;
import com.mredrock.cyxbs.summer.ui.presenter.AskFriendsPresenter;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;
import com.mredrock.cyxbs.summer.utils.Glide4Engine;
import com.mredrock.cyxbs.summer.utils.RecorderUtil;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.mredrock.cyxbs.summer.utils.UriUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class AskFriendsActivity extends BaseMvpActivity implements AskFriednsContract.IAskFriendsView {
    private ActivityAskFriendsBinding binding;
    private AskFriendsPresenter presenter;
    private RxPermissions rxPermissions;
    private RecorderUtil recorderUtil;
    private boolean isAskPer = false;
    private AVUser user;
    private String fileName = "";
    private String filePath = "";
    private Dialog dialog;

    private int REQUEST_CODE_CHOOSE = 10086;
    private List<Uri> selects;
    private List<AVFile> photos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ask_friends);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        rxPermissions = new RxPermissions(this);
        recorderUtil = new RecorderUtil();
        selects = new ArrayList<>();
        photos = new ArrayList<>();
        user = AVUser.getCurrentUser();
        askPermissions();

        DensityUtils.setTransparent(binding.summerAskTl,this);
        DensityUtils.setSystemUi(getWindow());

        //返回按钮
        binding.summerAskBack.setOnClickListener(v-> finish());

        //麦克风按钮

            binding.summerAskVoice.setOnTouchListener((v, event) -> {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    binding.summerAskVoice.setBackgroundResource(R.drawable.summer_icon_voice_light);
                    recorderUtil.recordStart();
                }
                try{
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        binding.summerAskVoice.setBackgroundResource(R.drawable.summer_icon_voice);
                        recorderUtil.recordStop((fileName,filePath) -> {
                            Toasts.show(fileName);
                            this.fileName = fileName;
                            this.filePath = filePath;
                        });
                    }
                }catch (Exception e){
                    Toasts.show("1");
                }
                return false;
            });


        //图片选择按钮
        binding.summerAskPhoto.setOnClickListener(v -> {
            if(isAskPer){
                Matisse.from(this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(3)
                        .gridExpectedSize(DensityUtils.getScreenWidth(this)/3)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .theme(R.style.Matisse_Dracula)
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });

        //提交按钮
        binding.summerAskCommit.setOnClickListener(v -> {
            if(isAskPer&&binding.summerAskName.getText().length()>0
                    &&binding.summerAskContent.getText().length()>0){
                AskBean bean = new AskBean();
                if(user.getAVFile("avatar")!=null){
                    bean.setAskAvatar(user.getAVFile("avatar").getUrl());
                }
                bean.setAskContent(binding.summerAskContent.getText().toString());
                bean.setAskName(binding.summerAskName.getText().toString());
                bean.setAuthorName(user.getUsername());
                try {
                    if(filePath.length()>0){
                        AVFile voice = AVFile.withAbsoluteLocalPath(fileName,filePath);
                        bean.setVoice(voice);
                    }
                    bean.setPhotos(photos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                presenter.ask(bean);
            }
        });



    }

    @SuppressLint({"CheckResult"})
    private void askPermissions(){
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

    @Override
    public void attachPresenter() {
        presenter = new AskFriendsPresenter(new AskFriendsModel());
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoad() {
        dialog = new DialogBuilder(this).title("").message("发布中...").setCancelable(false).build();
        dialog.show();
    }

    @Override
    public void hideLoad() {
        dialog.hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK){
            photos.clear();
            selects.clear();
            selects = Matisse.obtainResult(data);
            try{
                for (int i = 0; i < selects.size(); i++) {
                    String name = System.currentTimeMillis()/1000 + ".jpg";
                    String path = UriUtil.getRealPathFromUri(this,selects.get(i));
                        photos.add(AVFile.withAbsoluteLocalPath(name,path));
                    }
                    binding.summerAskPhoto.setBackgroundResource(R.drawable.summer_icon_photo_light);
            }catch (IOException e){
                Toasts.show("文件写入失败");
            }
        }
    }
}
