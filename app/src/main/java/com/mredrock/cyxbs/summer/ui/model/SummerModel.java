package com.mredrock.cyxbs.summer.ui.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.mredrock.cyxbs.summer.ui.contract.SummerContract;

import java.util.Date;
import java.util.List;

public class SummerModel implements SummerContract.ISummerModel {


    @Override
    public void loadData(int skip,LoadCallBack callBack) {
        AVQuery<AVObject> query = new AVQuery<>("askInfo");
        query.whereExists("askName");
        query.orderByDescending("updatedAt");
        query.limit(20);// 最多返回 20 条结果
        query.skip(skip);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e == null){
                    callBack.succeed(list);
                }else{
                    callBack.failed(e.getMessage());
                }
            }
        });

    }
}
