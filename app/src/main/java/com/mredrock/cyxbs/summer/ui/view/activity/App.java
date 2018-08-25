package com.mredrock.cyxbs.summer.ui.view.activity;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.event.AudioEvent;
import com.mredrock.cyxbs.summer.event.ImageEvent;
import com.mredrock.cyxbs.summer.event.TextEvent;
import com.mredrock.cyxbs.summer.utils.SPHelper;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.greenrobot.eventbus.EventBus;

public class App extends Application {
    private static Context appContext;
    private static SPHelper spHelper;
    private static boolean chatIsLoad = false;

    public static boolean isChatIsLoad() {
        return chatIsLoad;
    }

    public static void setChatIsLoad(boolean chatIsLoad) {
        App.chatIsLoad = chatIsLoad;
    }



    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        spHelper = new SPHelper(appContext,"Default");
        String app_Key = "VijaFweItDqe1KIUTHux1K7X";
        String app_Id = "UYy61kgDl429l4zkc1jzHJR5-gzGzoHsz";
        AVOSCloud.initialize(this, app_Id, app_Key);//初始化
        AVIMMessageManager.registerDefaultMessageHandler(new App.CustomMessageHandler());
        AVIMClient.setUnreadNotificationEnabled(true);
        AVOSCloud.setDebugLogEnabled(false);//开启日志
    }



    public static Context getContext(){return appContext;}

    public static SPHelper spHelper(){return spHelper;}

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                TextEvent event = new TextEvent();
                event.setMessage((AVIMTextMessage) message);
                EventBus.getDefault().post(event);
            }else if(message instanceof AVIMImageMessage){
                ImageEvent event = new ImageEvent();
                event.setMessage((AVIMImageMessage) message);
                EventBus.getDefault().post(event);
            }else{
                AudioEvent event = new AudioEvent();
                event.setMessage((AVIMAudioMessage) message);
                EventBus.getDefault().post(event);
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

        }
    }
}
