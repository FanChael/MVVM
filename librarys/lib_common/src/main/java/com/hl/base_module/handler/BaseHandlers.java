package com.hl.base_module.handler;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hl.base_module.util.system.LogUtil;

import java.lang.ref.WeakReference;

/**
 * 点击事件基础配置
 *
 * @Author: hl
 * @Date: created at 2020/3/27 13:35
 * @Description: com.hl.base_module.handler
 */
public class BaseHandlers<C, T> {
    private WeakReference<T> tWeakReference;
    private WeakReference<C> cWeakReference;

    public BaseHandlers(C c, T t) {
        tWeakReference = new WeakReference<>(t);
        cWeakReference = new WeakReference<>(c);
    }

    public T getBindingView() {
        return tWeakReference.get();
    }

    public C getBindedView() {
        return cWeakReference.get();
    }

    public Context getContext(){
        Object object = cWeakReference.get();
        if (object instanceof Fragment) {
            return ((Fragment) object).requireContext();
        } else if (object instanceof AppCompatActivity) {
            return (AppCompatActivity) object;
        } else {
            try {
                throw new Exception("没有指定组件，无法获取上下文！");
            } catch (Exception e) {
                LogUtil.outLogger(BaseHandlers.class, e.getMessage());
            }
            return null;
        }
    }
}
