package com.hl.lib_network.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.hl.lib_network.net.response.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        //        HttpStatus httpStatus = gson.fromJson(response, HttpStatus.class);
        //        if (httpStatus.isCodeInvalid()) {
        //            value.close();
        //            throw new ApiException(httpStatus.getCode(), httpStatus.getMessage());
        //        }
        HttpResponse<String> httpResponse  = new HttpResponse<>();
        try {
            JSONObject jsonObject = new JSONObject (response);
            httpResponse.setErrorCode(jsonObject.getInt("errorCode"));
            httpResponse.setErrorMsg(jsonObject.getString("errorMsg"));
            //Object objorarray = new JSONTokener(jsonObject.getString("data")).nextValue();
            //if (objorarray instanceof JSONArray) {}
            // 直接转换为String，由封装的ParseManager处理器处理
            httpResponse.setData(jsonObject.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return (T) httpResponse;
        } finally {
            value.close();
        }
    }
}
