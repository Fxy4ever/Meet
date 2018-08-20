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
    public void loadData(LoadCallBack callBack) {
        AVQuery<AVObject> query = new AVQuery<>("askInfo");
        Date now = new Date();
        query.whereLessThanOrEqualTo("createdAt", now);//查询今天之前创建的
        query.limit(40);// 最多返回 10 条结果
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
