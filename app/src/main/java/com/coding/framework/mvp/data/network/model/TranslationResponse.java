package com.coding.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Property;

import java.util.List;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class TranslationResponse {

    @Expose
    @SerializedName("code")
    @Property(nameInDb = "code")
    private String code;

    @Expose
    @SerializedName("lang")
    @Property(nameInDb = "lang")
    private String lang;

    @Expose
    @SerializedName("text")
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
