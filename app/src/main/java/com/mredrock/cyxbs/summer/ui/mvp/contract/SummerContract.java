package com.mredrock.cyxbs.summer.ui.mvp.contract;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;

import java.util.List;

public class SummerContract {

    public interface ISummerModel extends BaseContract.ISomethingModel{
        void loadData(int skip,LoadCallBack callBack);
    }

    public interface ISummerView extends BaseContract.ISomethingView{
        void setData(List<AVObject> data);
        void setMoreData(List<AVObject> data);
    }
}
