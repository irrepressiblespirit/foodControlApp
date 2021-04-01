package com.skibnev.foodcontrolapp.module;

import android.content.Context;

import com.skibnev.foodcontrolapp.adapter.ProductsSuggestAdapter;
import com.skibnev.foodcontrolapp.annotation.SingletonScope;
import com.skibnev.foodcontrolapp.listener.AutoCompleteTextChangedListener;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class AutoCompleteWidgetModule {

    int resource;

    public AutoCompleteWidgetModule(int resource) {
        this.resource = resource;
    }

    @SingletonScope
    @Provides
    ProductsSuggestAdapter provideProductsSuggestAdapter(Context context, int resource) {
        return new ProductsSuggestAdapter(context, resource);
    }

    @Provides
    AutoCompleteTextChangedListener provideAutoCompleteTextChangedListener(ProductsSuggestAdapter adapter) {
        return new AutoCompleteTextChangedListener(adapter);
    }

    @Provides
    int provideResourceId() {
        return resource;
    }
}
