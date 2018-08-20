package com.mredrock.cyxbs.summer.ui.contract;

import com.avos.avoscloud.AVObject;
import com.mredrock.cyxbs.summer.base.BaseContract;

import java.util.List;

public class SummerContract {

    public interface ISummerModel extends BaseContract.ISomethingModel{
        void loadData(LoadCallBack callBack);
    }

    public interface ISummerView extends BaseContract.ISomethingView{
        void setData(List<AVObject> data);
    }
}
