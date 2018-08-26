package com.mredrock.cyxbs.summer.ui.view.activity;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.databinding.ActivityAboutBinding;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;
import com.mredrock.cyxbs.summer.utils.Toasts;

public class AboutActivity extends AppCompatActivity {
    private ActivityAboutBinding binding;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about);
        init();
    }

    private void init(){
        DensityUtils.setTransparent(binding.summerAboutTl,this);
        dialog = new DialogBuilder(this).title("").message("检查更新中").setCancelable(false).build();
        binding.summerAboutCheck.setOnClickListener(v->{
            dialog.show();
            new Handler().postDelayed(() -> {
                dialog.cancel();
                Toasts.show("已经是最新版本了!~");
            },2000);
        });
        binding.summerAboutTl.setNavigationOnClickListener(v->{});
        finish();
    }
}