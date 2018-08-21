package com.mredrock.cyxbs.summer.ui.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.base.BaseMvpActivity;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.ActivityAskDetailBinding;
import com.mredrock.cyxbs.summer.ui.contract.AskDetailContract;
import com.mredrock.cyxbs.summer.ui.model.AskDetailModel;
import com.mredrock.cyxbs.summer.ui.presenter.AskDetailPresenter;
import com.mredrock.cyxbs.summer.utils.AudioPlayer;
import com.mredrock.cyxbs.summer.utils.DensityUtils;

public class AskDetailActivity extends BaseMvpActivity implements AskDetailContract.IAskDetailView{
    private AskBean bean;
    private ActivityAskDetailBinding binding;
    private AskDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_ask_detail);
        initData();
    }

    @Override
    public void attachPresenter() {
        presenter = new AskDetailPresenter(new AskDetailModel());
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
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
            Glide.with(this).load(bean.getAuthor().getAVFile("avatar").getUrl()).into(binding.summerSmDetailItemAvatar);
        }

        if(bean.getPhoto()!=null){
            Glide
                    .with(this).asBitmap()
                    .load(bean.getPhoto().getUrl())
                    .thumbnail(0.1f)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                    .into(binding.summerSmDetailItemImg);
            binding.summerSmDetailItemImg.setOnClickListener(v -> {
                /**
                 * 点击大图dialog
                 */
                Dialog dialog = new Dialog(this,R.style.edit_AlertDialog_style);
                dialog.setContentView(R.layout.show_img_dialog);
                ImageView imageView = dialog.findViewById(R.id.show_img);
                Glide.with(this)
                        .load(bean.getPhoto().getUrl())
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                        .into(imageView);
                dialog.setCanceledOnTouchOutside(true);
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.height = (int)(defaultDisplay.getHeight()*0.8);
                lp.width = (int)(defaultDisplay.getWidth()*0.8);
                dialogWindow.setAttributes(lp);
                imageView.setOnClickListener(v1->{
                    dialog.hide();
                });
                dialog.show();
            });
        }else{
            binding.summerSmDetailItemImg.setVisibility(View.GONE);
        }

        if(bean.getVoice()!=null){
            AudioPlayer audioPlayer = new AudioPlayer();
            audioPlayer.setStatusChangedListener(AudioPlayer.Status.STATUS_READY, (lapt, status, msg) -> {
                binding.summerSmDetailItemPlayTime.setText(audioPlayer.getmPlayer().getDuration() / 1000 + "s");
                onPlayerStatusChanged(lapt, status, msg, binding.summerSmDetailItemPlay);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_COMPLETE, (lapt, status, msg) -> {
                binding.summerSmDetailItemPlayTime.setText("");
                onPlayerStatusChanged(lapt, status, msg, binding.summerSmDetailItemPlay);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_ERROR, (lapt, status, msg) -> {
                binding.summerSmDetailItemPlayTime.setText("");
                onPlayerStatusChanged(lapt, status, msg, binding.summerSmDetailItemPlay);
            });

            binding.summerSmDetailItemPlay.setOnClickListener(v -> audioPlayer.Play(this, bean.getVoice().getUrl()));
        }else{
            binding.summerSmDetailItemPlay.setVisibility(View.GONE);
            binding.summerSmDetailItemPlayTime.setVisibility(View.GONE);
        }
        binding.summerSmDetailItemTl.setNavigationOnClickListener(v->{
            finish();
        });
        DensityUtils.setTransparent(binding.summerSmDetailItemTl,this);
    }

    private void onPlayerStatusChanged(AudioPlayer lapt, int status, @Nullable Object msg, ImageButton button){
        switch (status){
            case AudioPlayer.Status.STATUS_READY:
                button.setImageResource(R.drawable.summer_icon_play_light);
                break;
            case AudioPlayer.Status.STATUS_COMPLETE:
                button.setImageResource(R.drawable.summer_icon_play);
                break;
            case AudioPlayer.Status.STATUS_ERROR:
                button.setImageResource(R.drawable.summer_icon_play);
                break;
        }
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
}
