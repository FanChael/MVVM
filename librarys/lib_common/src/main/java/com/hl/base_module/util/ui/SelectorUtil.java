package com.hl.base_module.util.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.net.URL;

/**
 * Selector工具类 - StateListDrawable操作
*@Author: hl
*@Date: created at 2020/3/27 10:01
*@Description: com.hl.base_module.util
*/
public class SelectorUtil {
    /**
     * 从 drawable 获取图片 id 给 Imageview 添加 selector
     *
     * @param context  调用方法的 Activity
     * @param idNormal 默认图片的 id
     * @param idPress  点击图片的 id
     * @param iv       点击的 view
     */
    public static void addSelectorFromDrawable(Context context, int idNormal, int idPress, ImageView iv) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable normal = context.getResources().getDrawable(idNormal, null);
        Drawable press = context.getResources().getDrawable(idPress, null);
        drawable.addState(new int[]{android.R.attr.state_pressed}, press);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        iv.setBackground(drawable);
    }

    /**
     * 从 drawable 获取图片 id 给 Button 添加 selector
     *
     * @param context  调用方法的 Activity
     * @param idNormal 默认图片的 id
     * @param idPress  点击图片的 id
     * @param button   点击的 view
     */

    public static void addSelectorFromDrawable(Context context, int idNormal, int idPress, Button button) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable normal = context.getResources().getDrawable(idNormal, null);
        Drawable press = context.getResources().getDrawable(idPress, null);
        drawable.addState(new int[]{android.R.attr.state_pressed}, press);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        button.setBackground(drawable);
    }

    /**
     * 从 drawable 获取图片 id 给 Button 添加 selector
     *
     * @param context      调用方法的 Activity
     * @param idNormal     默认图片的 id
     * @param idPress      点击图片的 id
     * @param toggleButton 点击的 view
     */

    public static void addSelectorFromDrawable(Context context, int idNormal, int idPress, ToggleButton toggleButton) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable normal = context.getResources().getDrawable(idNormal, null);
        Drawable press = context.getResources().getDrawable(idPress, null);
        drawable.addState(new int[]{android.R.attr.state_checked}, press);
        drawable.addState(new int[]{-android.R.attr.state_checked}, normal);
        toggleButton.setButtonDrawable(drawable);
    }

    /**
     * 从网络获取图片 给 ImageView 设置 selector
     *
     * @param clazz     调用方法的类
     * @param normalUrl 获取默认图片的链接
     * @param pressUrl  获取点击图片的链接
     * @param imageView 点击的 view
     */
    public static void addSeletorFromNet(final Class clazz, final String normalUrl, final String pressUrl, final ImageView imageView) {
        new AsyncTask<Void, Void, Drawable>() {

            @Override
            protected Drawable doInBackground(Void... params) {
                StateListDrawable drawable = new StateListDrawable();
                Drawable normal = loadImageFromNet(clazz, normalUrl);
                Drawable press = loadImageFromNet(clazz, pressUrl);
                drawable.addState(new int[]{android.R.attr.state_pressed}, press);
                drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                imageView.setBackground(drawable);
            }
        }.execute();

    }

    /**
     * 从网络获取图片 给 Button 设置 selector
     *
     * @param clazz     调用方法的类
     * @param normalUrl 获取默认图片的链接
     * @param pressUrl  获取点击图片的链接
     * @param button    点击的 view
     */
    public static void addSeletorFromNet(final Class clazz, final String normalUrl, final String pressUrl, final Button button) {
        new AsyncTask<Void, Void, Drawable>() {

            @Override
            protected Drawable doInBackground(Void... params) {
                StateListDrawable drawable = new StateListDrawable();
                Drawable normal = loadImageFromNet(clazz, normalUrl);
                Drawable press = loadImageFromNet(clazz, pressUrl);
                drawable.addState(new int[]{android.R.attr.state_pressed}, press);
                drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                button.setBackground(drawable);
            }
        }.execute();

    }

    /**
     * 从网络获取图片
     *
     * @param clazz  调用方法的类
     * @param netUrl 获取图片的链接
     * @return 返回一个 drawable 类型的图片
     */
    private static Drawable loadImageFromNet(Class clazz, String netUrl) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(netUrl).openStream(), "netUrl.jpg");
        } catch (IOException e) {
            Log.e(clazz.getName(), e.getMessage());
        }

        return drawable;
    }
}
