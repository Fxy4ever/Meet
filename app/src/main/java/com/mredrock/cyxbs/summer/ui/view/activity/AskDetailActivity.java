package com.mredrock.cyxbs.summer.ui.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerDetailAdapter;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.base.BaseMvpActivity;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.databinding.ActivityAskDetailBinding;
import com.mredrock.cyxbs.summer.ui.contract.AskDetailContract;
import com.mredrock.cyxbs.summer.ui.model.AskDetailModel;
import com.mredrock.cyxbs.summer.ui.presenter.AskDetailPresenter;
import com.mredrock.cyxbs.summer.utils.AudioPlayer;
import com.mredrock.cyxbs.summer.utils.AudioUtil;
import com.mredrock.cyxbs.summer.utils.DateUtil;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;
import com.mredrock.cyxbs.summer.utils.RecorderUtil;
import com.mredrock.cyxbs.summer.utils.Toasts;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AskDetailActivity extends BaseMvpActivity implements AskDetailContract.IAskDetailView{
    private AskBean bean;
    private ActivityAskDetailBinding binding;
    private AskDetailPresenter presenter;
    private Dialog commentDlg;
    private RxPermissions rxPermissions;
    private boolean isAskPer = false;
    private String fileName="";
    private String filePath="";
    private String content="";
    private Dialog dialog;
    private RecyclerView recyclerView;
    private SummerDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ask_detail);
        askPermissions();
        initRV();
        initComment();
        initData();
    }


    @SuppressLint("ClickableViewAccessibility")
    /**
     * 初始化评论Dialog
     */
    private void initComment(){
        commentDlg = new Dialog(this,R.style.summer_custom_dialog);
        commentDlg.setContentView(R.layout.summer_comment_layout);
        commentDlg.setCanceledOnTouchOutside(true);
        EditText contentEt = commentDlg.findViewById(R.id.summer_ask_comment_et);
        ImageButton voice  = commentDlg.findViewById(R.id.summer_ask_comment_voice);
        Button commit = commentDlg.findViewById(R.id.summer_ask_comment_commit);

        contentEt.setText("");

        ViewGroup.LayoutParams layoutParams = contentEt.getLayoutParams();
        layoutParams.width = DensityUtils.getScreenWidth(this)/2;
        contentEt.setLayoutParams(layoutParams);
        binding.summerSmCommentItem.setOnClickListener(v->{
            commentDlg.show();
        });

        RecorderUtil recorderUtil = new RecorderUtil();
        voice.setOnTouchListener((v, event) -> {
            if(isAskPer){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    voice.setBackgroundResource(R.drawable.summer_icon_voice_light);
                    recorderUtil.recordStart();
                }
                try{
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        voice.setBackgroundResource(R.drawable.summer_icon_voice);
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
            }
            return false;
        });

        commit.setOnClickListener(v->{
            content = contentEt.getText().toString();
            if(content.length()>0||filePath.length()>0){//语音和文字必选一个
                CommentBean bean = new CommentBean();
                bean.setUser(AVUser.getCurrentUser());
                bean.setAskInfo(SummerListAdapter.bean.getAskInfo());
                if(content.length()>0){
                    bean.setContent(content);
                }else{
                    bean.setContent("");
                }

                if(filePath.length()>0){
                    try {
                        AVFile avFile = AVFile.withAbsoluteLocalPath(fileName,filePath);
                        bean.setVoice(avFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                presenter.comment(bean);
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


    @Override
    public void attachPresenter() {
        presenter = new AskDetailPresenter(new AskDetailModel());
        presenter.attachView(this);
        presenter.start(SummerListAdapter.bean.getObjectId());
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }


    private void initRV(){
        recyclerView = binding.summerSmCommentRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setData(List<AVObject> data) {
        List<CommentBean> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CommentBean bean = new CommentBean();
            bean.setContent(data.get(i).getString("content"));
            bean.setVoice(data.get(i).getAVFile("voice"));
            bean.setTime(DateUtil.getCurDate(data.get(i).getCreatedAt()));
            AVObject comment = AVObject.createWithoutData("comment",data.get(i).getObjectId());
            comment.fetchInBackground("user", new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if(e==null){
                        AVUser user = avObject.getAVUser("user");
                        bean.setUser(user);
                        list.add(bean);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        adapter = new SummerDetailAdapter(this,list,new int[]{R.layout.summer_item_comment_rv});
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    /**
     * 初始化共享组件的内容
     */
    private void initData(){
        bean = SummerListAdapter.bean;
        binding.summerSmDetailItemName.setText(bean.getAuthor().getUsername());
        binding.summerSmDetailItemContent.setText(bean.getAskContent());
        binding.summerSmDetailItemTitle.setText(bean.getAskName());
        binding.summerSmDetailItemTime.setText(bean.getUpdatedAt());
        if(bean.getAuthor().getAVFile("avatar")!=null){
            Glide.with(this).load(bean.getAuthor().getAVFile("avatar").getUrl()).apply(new RequestOptions().override(200,200)).into(binding.summerSmDetailItemAvatar);
        }

        binding.summerSmDetailItemAvatar.setOnClickListener(v->{
            Intent intent = new Intent(getContext(),UserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("objectId",bean.getAuthor().getObjectId());
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        if(bean.getPhoto()!=null){
            Glide
                    .with(this).asBitmap()
                    .load(bean.getPhoto().getUrl())
                    .thumbnail(0.1f)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                    .into(binding.summerSmDetailItemImg);
            binding.summerSmDetailItemImg.setOnClickListener(v -> {
                Dialog dialog = DialogBuilder.buildImgDialog(getContext(),bean.getPhoto().getUrl());
                dialog.show();
            });
        }else{
            binding.summerSmDetailItemImg.setVisibility(View.GONE);
        }
        /*
        语音播放
         */
        AudioUtil.setAudio(getContext(),bean.getVoice(),binding.summerSmDetailItemPlayTime,binding.summerSmDetailItemPlay);

        binding.summerSmDetailItemTl.setNavigationOnClickListener(v->{
            finish();
        });
        DensityUtils.setTransparent(binding.summerSmDetailItemTl,this);
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
        dialog = new DialogBuilder(this).title("").message("评论中...").setCancelable(false).build();
        dialog.show();
    }

    @Override
    public void hideLoad() {
        dialog.hide();
        commentDlg.hide();
    }


}
