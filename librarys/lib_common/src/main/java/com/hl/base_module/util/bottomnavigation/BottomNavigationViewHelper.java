package com.hl.base_module.util.bottomnavigation;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hl.base_module.util.glide.GlideUtil;

public class BottomNavigationViewHelper {
    /**
     * 设置图片尺寸
     *
     * @param view
     * @param width
     * @param height
     */
    public static void setImageSize(BottomNavigationView view, int width, int height) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = width;
                layoutParams.width = height;
                imageView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon距离顶部高度
     * 不需要了，直接设置design_bottom_navigation_margin属性即可
     *
     * @param view
     * @param marginTop
     */
    public static void setImageMarginTop(BottomNavigationView view, int marginTop) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                layoutParams.topMargin = marginTop;
                imageView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon为网络图标
     *
     * @param view
     * @param imgUrl
     */
    public static void replaceItemImage(BottomNavigationView view, String[] imgUrl) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
                GlideUtil.initImageWithFileCache(view.getContext(), imgUrl[i], imageView);
                Log.e("test", "00000=" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon为刷新图标
     *
     * @param view
     * @param gifUrl
     */
    public static void replaceRefreshImage(BottomNavigationView view, int index, String gifUrl) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(index);
            ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
            GlideUtil.initImageWithFileCache(view.getContext(), gifUrl, imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
