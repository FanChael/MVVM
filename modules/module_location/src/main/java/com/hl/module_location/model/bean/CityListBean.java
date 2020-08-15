package com.hl.module_location.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.promeg.pinyinhelper.Pinyin;
import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class CityListBean extends BaseMulDataModel implements Parcelable {
    /**
     * id :
     * name : 全部
     */

    private String id;
    private String name;
    private String letter;

    public CityListBean(){}

    protected CityListBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        letter = in.readString();
    }

    public static final Creator<CityListBean> CREATOR = new Creator<CityListBean>() {
        @Override
        public CityListBean createFromParcel(Parcel in) {
            return new CityListBean(in);
        }

        @Override
        public CityListBean[] newArray(int size) {
            return new CityListBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        String[] pinyin = Pinyin.toPinyin(name,"/").split("/");
        if (name.indexOf("重") == 0){
            return "C";
        }
        if (name.indexOf("全部") == 0){
            return "#";
        }
        return String.valueOf(pinyin[0].toCharArray()[0]);
        //return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(letter);
    }
}
