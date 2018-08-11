package com.coding.framework.mvp.data;


import com.coding.framework.mvp.data.db.DbHelper;
import com.coding.framework.mvp.data.network.ApiService;
import com.coding.framework.mvp.data.prefs.PreferencesHelper;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

public interface DataManager extends DbHelper, PreferencesHelper, ApiService {

}
