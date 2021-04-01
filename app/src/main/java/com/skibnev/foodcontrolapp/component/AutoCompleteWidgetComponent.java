package com.skibnev.foodcontrolapp.component;


import com.skibnev.foodcontrolapp.adapter.ProductsSuggestAdapter;
import com.skibnev.foodcontrolapp.annotation.SingletonScope;
import com.skibnev.foodcontrolapp.listener.AutoCompleteTextChangedListener;
import com.skibnev.foodcontrolapp.module.AutoCompleteWidgetModule;

import dagger.Subcomponent;

@SingletonScope
@Subcomponent(modules = AutoCompleteWidgetModule.class)
public interface AutoCompleteWidgetComponent {

    ProductsSuggestAdapter getAutoCompleteProductSuggestAdapter();

    AutoCompleteTextChangedListener getAutoCompleteTextChangedListener();
}
