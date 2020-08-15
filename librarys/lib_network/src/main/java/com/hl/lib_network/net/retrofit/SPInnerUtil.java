package com.hl.lib_network.net.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hl on 2018/3/23.
 */

public class SPInnerUtil {
    public static final String mTAG = "SharedPreferencesUtil";
    ///< 创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SPInnerUtil mSharedPreferencesUtil;

    ///< 构造方法
    public SPInnerUtil(Context context) {
        mPreferences = context.getSharedPreferences(mTAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    ///< 单例模式
    public static SPInnerUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null) {
            mSharedPreferencesUtil = new SPInnerUtil(context);
        }
        return mSharedPreferencesUtil;
    }

    ///< 存入数据
    public void putSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    ///< 获取数据
    public String getSP(String key) {
        return mPreferences.getString(key, "");
    }

    ///< 存入数据
    public void putSBool(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    ///< 存入数据
    public void putSInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    ///< 存入数据
    public int getSInt(String key) {
        return mPreferences.getInt(key, -1);
    }

    ///< 获取数据
    public boolean getSBool(String key) {
        return mPreferences.getBoolean(key, false);
    }

    ///< 移除数据
    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 保存对象
     *
     * @param key 键
     * @param obj 要保存的对象（Serializable的子类）
     * @param <T> 泛型定义
     */
    public <T extends Serializable> void putObject(String key, T obj) {
        try {
            put(key, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @param <T> 指定泛型
     * @return 泛型对象
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getObject(String key) {
        try {
            return (T) get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储List集合
     *
     * @param key  存储的键
     * @param list 存储的集合
     */
    public void putList(String key, List<? extends Serializable> list) {
        try {
            put(key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     *
     * @param key 键
     * @param <E> 指定泛型
     * @return List集合
     */
    @SuppressWarnings("unchecked")
    public <E extends Serializable> List<E> getList(String key) {
        try {
            return (List<E>) get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储Map集合
     *
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
     */
    public <K extends Serializable, V extends Serializable> void putMap(String key, Map<K, V> map) {
        try {
            put(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取map对象
     * @param context
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <K extends Serializable, V extends Serializable> Map<K, V> getMap(Context context,
                                                                             String key) {
        try {
            return (Map<K, V>) get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象
     */
    private void put(String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putSP(key, objectStr);
    }

    /**
     * 获取对象
     */
    private Object get(String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getSP(key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }
}
