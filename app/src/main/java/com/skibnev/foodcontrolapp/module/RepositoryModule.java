package com.skibnev.foodcontrolapp.module;

import com.skibnev.foodcontrolapp.annotation.SingletonScope;
import com.skibnev.foodcontrolapp.db.ProductRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @SingletonScope
    @Provides
    ProductRepository provideProductRepository() {
        return new ProductRepository();
    }
}
