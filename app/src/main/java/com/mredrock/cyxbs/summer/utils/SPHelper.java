package com.mredrock.cyxbs.summer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SPHelper {
    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SPHelper(Context context, String fileName) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void put(String key,Object value){
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    public Object get(String key, Object value) {
        if (value instanceof String) {
            return sharedPreferences.getString(key, (String) value);
        } else if (value instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) value);
        } else if (value instanceof Long) {
            return sharedPreferences.getLong(key, (Long) value);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }

    public void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public boolean contain(String key){
        return sharedPreferences.contains(key);
    }

    public Map<String,?> getAll(){
        return sharedPreferences.getAll();
    }


}
