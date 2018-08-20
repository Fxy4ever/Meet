package com.mredrock.cyxbs.summer.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.mredrock.cyxbs.summer.ui.view.activity.App;

public class Toasts {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void show(String text){
        if(toast == null){
            toast = Toast.makeText(App.getContext(),text,Toast.LENGTH_LONG);
        }else{
            toast.setText(text);
        }
        toast.show();
    }
}
