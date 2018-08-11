package com.coding.framework.mvp.di.module;

import android.app.Service;

import dagger.Module;


/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
