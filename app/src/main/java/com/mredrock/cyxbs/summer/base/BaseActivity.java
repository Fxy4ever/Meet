package com.mredrock.cyxbs.summer.base;

import android.support.v7.app.AppCompatActivity;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.utils.ActivityManager;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
