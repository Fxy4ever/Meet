package com.mredrock.cyxbs.summer.ui.presenter;

import com.mredrock.cyxbs.summer.base.BasePresenter;
import com.mredrock.cyxbs.summer.ui.contract.AskDetailContract;
import com.mredrock.cyxbs.summer.ui.model.AskDetailModel;

public class AskDetailPresenter extends BasePresenter<AskDetailContract.IAskDetailView> {
    private AskDetailModel model;

    public AskDetailPresenter(AskDetailModel model) {
        this.model = model;
    }

    public void start(){

    }

    public void comment(){

    }
}
