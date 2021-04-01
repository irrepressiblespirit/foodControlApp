package com.skibnev.foodcontrolapp.component;

import com.skibnev.foodcontrolapp.adapter.ProductsSuggestAdapter;
import com.skibnev.foodcontrolapp.annotation.SingletonScope;
import com.skibnev.foodcontrolapp.listener.AutoCompleteTextChangedListener;
import com.skibnev.foodcontrolapp.module.AutoCompleteWidgetModule;
import com.skibnev.foodcontrolapp.module.ContextModule;
import com.skibnev.foodcontrolapp.service.CalcCalories;
import com.skibnev.foodcontrolapp.db.ProductRepository;
import com.skibnev.foodcontrolapp.module.BusinessLogicModule;
import com.skibnev.foodcontrolapp.module.RepositoryModule;

import dagger.Component;

@SingletonScope
@Component(modules = {BusinessLogicModule.class, RepositoryModule.class})
public interface AppComponent {
    CalcCalories getCalcCaloriesService();

    ProductRepository getProductRepository();

    AutoCompleteWidgetComponent getAutoCompleteWidgetComponent(ContextModule contextModule, AutoCompleteWidgetModule autoCompleteWidgetModule);
}
