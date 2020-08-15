package com.hl.base_module.util.system;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.hl.base_module.util.storage.SharedPreferencesUtil;

public class DayNightUtil {
    /**
     * 首次设置夜间白天模式
     * @param context
     */
    public static void setDayOrNight(Context context) {
        if (SharedPreferencesUtil.getInstance(context).getSBool(getDayOrNightKey())){
            //夜间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //日间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 设置是否是白天模式
     * @param context
     * @param isDay
     */
    public static void setIsDay(Context context, boolean isDay){
        if(isDay) {
            //日间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            //夜间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        SharedPreferencesUtil.getInstance(context).putSBool(getDayOrNightKey(), isDay);
    }

    /**
     * 是否是白天模式
     * @param context
     * @return
     */
    public static boolean bIsDay(Context context){
        return SharedPreferencesUtil.getInstance(context).getSBool(getDayOrNightKey());
    }

    private static String getDayOrNightKey(){
        return "day_or_night";
    }
}
