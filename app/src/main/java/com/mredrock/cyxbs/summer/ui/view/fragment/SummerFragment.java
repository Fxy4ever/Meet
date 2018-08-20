package com.mredrock.cyxbs.summer.ui.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVObject;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.adapter.SummerListAdapter;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.databinding.SummerFragmentListBinding;
import com.mredrock.cyxbs.summer.ui.contract.SummerContract;
import com.mredrock.cyxbs.summer.ui.model.SummerModel;
import com.mredrock.cyxbs.summer.ui.presenter.SummerPresenter;
import com.mredrock.cyxbs.summer.ui.view.activity.AskFriendsActivity;
import com.mredrock.cyxbs.summer.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用来显示所有的随机信息
 */
public class SummerFragment  extends BaseFragment implements SummerContract.ISummerView{
    private RecyclerView recyclerView;
    private SummerFragmentListBinding binding;
    private List<AskBean> datas;
    private SummerPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.summer_fragment_list,container,false);
        initRV();
        initFab();
        return binding.getRoot();
    }

    @Override
    public void attachView() {
        presenter = new SummerPresenter(new SummerModel());
        presenter.attachView(this);
        presenter.start();
    }

    @Override
    public void detacheView() {
        presenter.detachView();
    }

    private void initRV(){
        recyclerView = binding.summerListRv;
        datas = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFab(){
        binding.summerGcFab.setOnClickListener( v -> {
            startActivity(new Intent(getActivity(), AskFriendsActivity.class));
            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.out_to_top,R.anim.in_from_bottm);
        });
    }

    @Override
    public void setData(List<AVObject> data) {
        Log.d("fxy", "setData: "+data.size());
        for (int i = 0; i < data.size(); i++) {
            AskBean bean = new AskBean();
            bean.setAskName(data.get(i).getString("askName"));
            bean.setPhotos(data.get(i).getList("photos"));
            bean.setAskContent(data.get(i).getString("askContent"));
            bean.setUpdateTime(DateUtil.getCurDate(data.get(i).getUpdatedAt()));
            bean.setAskId(data.get(i).getObjectId());
            bean.setAuthorName(data.get(i).getString("authorName"));
            bean.setVoice(data.get(i).getAVFile("voice"));
            bean.setAskAvatar(data.get(i).getString("askAvatar"));
//            Log.d("fxy", "setData: "+bean.toString());
            datas.add(bean);
        }

        recyclerView.setAdapter(new SummerListAdapter(getActivity(),datas,new int[]{R.layout.summer_item_ask_rv}));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());//很关键
    }
}
