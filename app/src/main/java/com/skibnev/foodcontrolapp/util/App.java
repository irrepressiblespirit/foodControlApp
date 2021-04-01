package com.skibnev.foodcontrolapp.util;

import android.app.Application;

import com.skibnev.foodcontrolapp.component.AppComponent;
import com.skibnev.foodcontrolapp.component.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
