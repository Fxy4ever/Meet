package com.mredrock.cyxbs.summer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.utils.ActivityManager;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

}
