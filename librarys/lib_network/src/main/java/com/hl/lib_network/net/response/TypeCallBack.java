package com.hl.lib_network.net.response;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
*@Description: 获取泛型实体类类型 - 用于HttpResponse对象的泛型T的data类型的数据转换
*@Author: hl
*@Time: 2019/1/18 14:10
*/
public abstract class TypeCallBack<T> {
    public Type mType;

    public TypeCallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }

    public Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superClass = subclass.getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superClass;
        return $Gson$Types
                .canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public Class getClassType(){
        return getClass();
    }
}
