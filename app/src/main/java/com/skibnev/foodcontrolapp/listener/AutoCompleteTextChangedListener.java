package com.skibnev.foodcontrolapp.listener;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.skibnev.foodcontrolapp.adapter.ProductsSuggestAdapter;
import com.skibnev.foodcontrolapp.model.Product;
import com.skibnev.foodcontrolapp.util.App;
import com.skibnev.foodcontrolapp.util.CallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AutoCompleteTextChangedListener implements TextWatcher {

    private ProductsSuggestAdapter adapter;

    public AutoCompleteTextChangedListener(ProductsSuggestAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s != null && s.length() > 2) {
            String prefix = s.toString().replace(s.charAt(0), s.toString().toUpperCase().charAt(0));
            App.getComponent().getProductRepository().searchByNamePrefix(prefix, new CallBack() {

                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        Log.i("AutoCompleteListener", "find next variants: " + (Set<Product>) object);
                        List<String> products = new ArrayList<>();
                        Iterator<Product> iterator = ((Set<Product>) object).iterator();
                        while (iterator.hasNext()) {
                            Product product = iterator.next();
                            if (product != null) {
                                products.add(product.getName());
                            }
                        }
                        adapter.setData(products);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.setData(Collections.emptyList());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Object object) {
                    Log.i("AutoCompleteListener", "Products list is null");
                }
            });
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
