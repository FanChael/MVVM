package com.hl.base_module.page.observer;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class ActivityObserver implements DefaultLifecycleObserver {
    private static final String TAG = "ActivityObserver";
    private Context context;
    private Lifecycle lifecycle;

    public ActivityObserver(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "当前生命周期状态=" + lifecycle.getCurrentState().name());
    }
}
