package com.mredrock.cyxbs.summer.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.base.BaseActivity;
import com.mredrock.cyxbs.summer.utils.DensityUtils;
import com.mredrock.cyxbs.summer.utils.network.ApiGenerator;
import com.mredrock.cyxbs.summer.utils.network.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.welcome_tb);
        DensityUtils.setTransparent(toolbar,this);

        new Handler().postDelayed(() -> {
           if((boolean)App.spHelper().get("isChecked",false))
           {
               Login();

               startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
               WelcomeActivity.this.finish();
           }else{
               startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
               WelcomeActivity.this.finish();
           }
            overridePendingTransition(R.anim.out_to_top,R.anim.in_from_bottm);
        },1000);
    }

    private void Login(){
        String tx_account = (String) App.spHelper().get("account","123");
        String tx_password = (String) App.spHelper().get("password","123");
        if(tx_account.length()>0&&tx_password.length()>0){
//            ApiGenerator
//                    .INSTANCE
//                    .getApiService(ApiService.class)
//                    .login(tx_account,tx_password)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(netBean -> {
//                        Log.d("test",netBean.toString());
//                    });
            AVUser.logInInBackground(tx_account, tx_password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {

                }
            });
        }
    }
}
