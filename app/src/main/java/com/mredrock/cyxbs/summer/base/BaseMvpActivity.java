package com.mredrock.cyxbs.summer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public abstract class BaseMvpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        super.onDestroy();
    }

    public abstract void attachPresenter();

    public abstract void detachPresenter();
}
