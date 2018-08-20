package com.mredrock.cyxbs.summer.utils;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {
    private static Stack<Activity> activityStack;

    private static ActivityManager manager;

    private ActivityManager(){}

    public static ActivityManager getInstance(){
        if(manager == null){
            synchronized (ActivityManager.class){
                if(manager == null){
                    manager = new ActivityManager();
                }
            }
        }
        return manager;
    }

    public void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public Activity getCurrentActivity(){
        return activityStack.lastElement();
    }

    public void finishActivity(){
        finishActivity(getCurrentActivity());
    }

    public void finishActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> mclass){
        for (Activity a : activityStack) {
            if(a.getClass().equals(mclass)){
                finishActivity(a);
            }
        }
    }

    public void finishAllActivity(){
        for (Activity a : activityStack) {
            if(a != null){
                a.finish();
            }
        }
    }
}
