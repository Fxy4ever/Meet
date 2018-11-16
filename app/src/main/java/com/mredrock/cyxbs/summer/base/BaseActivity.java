package com.mredrock.cyxbs.summer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.event.EmptyEvent;
import com.mredrock.cyxbs.summer.utils.ActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ActivityManager.getInstance().addActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void empty(EmptyEvent emptyEvent){}

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().finishActivity(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
