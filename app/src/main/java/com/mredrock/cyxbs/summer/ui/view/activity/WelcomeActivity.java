package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.avos.avoscloud.AVOSCloud;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.utils.DensityUtils;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.welcome_tb);
        DensityUtils.setTransparent(toolbar,this);
        String app_Key = "VijaFweItDqe1KIUTHux1K7X";
        String app_Id = "UYy61kgDl429l4zkc1jzHJR5-gzGzoHsz";
        AVOSCloud.initialize(this, app_Id, app_Key);//初始化
        AVOSCloud.setDebugLogEnabled(true);//开启日志
        new Handler().postDelayed(() -> {
           if((boolean)App.spHelper().get("isChecked",false))
           {
               startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
               WelcomeActivity.this.finish();
           }else{
               startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
               WelcomeActivity.this.finish();
           }
            overridePendingTransition(R.anim.out_to_top,R.anim.in_from_bottm);
        },1000);
    }
}
