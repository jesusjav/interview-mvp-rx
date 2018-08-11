package com.coding.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

public class LangResponse {

    @Expose
    @SerializedName("langs")
    private HashMap<String, String> lang;

    public HashMap<String, String> getLang() {
        return lang;
    }

    public void setLang(HashMap<String, String> lang) {
        this.lang = lang;
    }
}
