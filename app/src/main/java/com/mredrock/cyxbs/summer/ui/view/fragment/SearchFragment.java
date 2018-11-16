package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.ChickenBean;
import com.mredrock.cyxbs.summer.ui.view.activity.MainActivity;
import com.mredrock.cyxbs.summer.ui.view.activity.MeetActivity;
import com.mredrock.cyxbs.summer.ui.view.activity.ShakeActivity;
import com.mredrock.cyxbs.summer.utils.HttpUtilManager;
import com.mredrock.cyxbs.summer.utils.Toasts;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * create by:Fxymine4ever
 * time: 2018/11/12
 */
public class SearchFragment extends Fragment {
    private View parent;
    private Button shake;
    private Button meet;
    public static String url;
    public FrameLayout progressbar;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.summer_fragment_search,container,false);
        shake = parent.findViewById(R.id.search_btn_shake);
        meet = parent.findViewById(R.id.search_btn_meet);
        progressbar = parent.findViewById(R.id.search_progressbar);
        shake.setOnClickListener(v->{
            progressbar.setVisibility(View.VISIBLE);
            HttpUtilManager.getInstance()
                    .getChicken()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(chickenBean -> {
                        if(chickenBean.getStatus()==200&&chickenBean.getData()!=null){
                            progressbar.setVisibility(View.GONE);
                            Intent intent = new Intent(getContext(),ShakeActivity.class);
                            url = chickenBean.getData().get((int) (Math.random()*100%chickenBean.getData().size()));
                            startActivity(intent);
                        }else{
                            progressbar.setVisibility(View.GONE);
                            Toasts.show("网络错误噢～");
                        }
                    });
                });
        meet.setOnClickListener(v->{
            startActivity(new Intent(getContext(),MeetActivity.class));
        });
        return parent;
    }
}
