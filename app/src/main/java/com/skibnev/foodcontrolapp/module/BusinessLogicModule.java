package com.skibnev.foodcontrolapp.module;

import com.skibnev.foodcontrolapp.annotation.SingletonScope;
import com.skibnev.foodcontrolapp.service.CalcCalories;

import dagger.Module;
import dagger.Provides;

@Module
public class BusinessLogicModule {

    @SingletonScope
    @Provides
    CalcCalories provideCaloriesCalc() {
        return new CalcCalories();
    }
}
