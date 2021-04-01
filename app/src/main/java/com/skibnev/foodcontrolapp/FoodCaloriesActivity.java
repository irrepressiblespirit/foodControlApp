package com.skibnev.foodcontrolapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.skibnev.foodcontrolapp.component.AutoCompleteWidgetComponent;
import com.skibnev.foodcontrolapp.module.AutoCompleteWidgetModule;
import com.skibnev.foodcontrolapp.module.ContextModule;
import com.skibnev.foodcontrolapp.service.CalcCalories;
import com.skibnev.foodcontrolapp.model.Product;
import com.skibnev.foodcontrolapp.util.App;
import com.skibnev.foodcontrolapp.util.CallBack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FoodCaloriesActivity extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView mAutoCompleteTextView;
    RadioButton gramm, pieces;
    TextView calorie_content, totalFoodCalories, dailyHumanNorm;
    EditText quantity;
    Button calcFoodCalories, saveProduct;
    ChipGroup chipGroup;
    RadioGroup radioGroup;
    private Map<String, Double> productMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_calories);

        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.product_name);
        quantity = (EditText) findViewById(R.id.quantity);
        calorie_content = (TextView) findViewById(R.id.calorie_content);
        gramm = (RadioButton) findViewById(R.id.gramm);
        pieces = (RadioButton) findViewById(R.id.pieces);
        calcFoodCalories = (Button) findViewById(R.id.calc_food_calories);
        saveProduct = (Button) findViewById(R.id.save_product);
        chipGroup = (ChipGroup) findViewById(R.id.productsWithCaloriesChipGroup);
        totalFoodCalories = (TextView) findViewById(R.id.total_food_calories);
        dailyHumanNorm = (TextView) findViewById(R.id.daily_human_norm);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        dailyHumanNorm.setText(getIntent().getStringExtra(getResources().getString(R.string.daily_human_norm)));
        calcFoodCalories.setOnClickListener(this);
        saveProduct.setOnClickListener(this);

        AutoCompleteWidgetComponent widgetComponent = App.getComponent()
                .getAutoCompleteWidgetComponent(new ContextModule(this), new AutoCompleteWidgetModule(android.R.layout.simple_dropdown_item_1line));

        mAutoCompleteTextView.setAdapter(widgetComponent.getAutoCompleteProductSuggestAdapter());

        mAutoCompleteTextView.addTextChangedListener(widgetComponent.getAutoCompleteTextChangedListener());

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int checkedRadioButtonId = group.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.gramm) {
                pieces.setChecked(false);
            } else if (checkedRadioButtonId == R.id.pieces) {
                gramm.setChecked(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onCalcDailyCaloriesMenuClick(MenuItem item) {
        Intent intent = new Intent(FoodCaloriesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onFoodCaloriesMenuClick(MenuItem item) {
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(quantity.getText()) || TextUtils.isEmpty(mAutoCompleteTextView.getText())) {
            return;
        }
        String productName = mAutoCompleteTextView.getText().toString();
        if (v.getId() == R.id.calc_food_calories) {
            calcTotalProductCalories(productName);
        } else {
            addChip(productName);
        }
    }

    private void calcTotalProductCalories(String productName) {
        CalcCalories calcCaloriesService = App.getComponent().getCalcCaloriesService();
        App.getComponent().getProductRepository().getByName(productName, new CallBack() {

            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    Product product = (Product) object;
                    Log.i("Find by name", product.toString());
                    double calories = calcCaloriesService.calcProductCalories(gramm.isChecked(), pieces.isChecked(), Double.parseDouble(quantity.getText().toString()), product);
                    productMap.put(product.getName(), calories);
                    calorie_content.setText(String.valueOf(calories));
                } else {
                    Log.i("Find by name", "Product is null");
                }
            }

            @Override
            public void onError(Object object) {
                Log.e("Find by name: ", "Error " + object.toString());
            }
        });
    }

    private void addChip(String productName) {
        if (productMap.containsKey(productName) && productMap.get(productName) != null) {
            Chip productChip = new Chip(this);
            productChip.setCloseIconVisible(true);
            totalFoodCalories.setText(String.valueOf(calcCaloriesForAllProducts(productMap)));
            productChip.setText(productName + "/" + productMap.get(productName));
            productChip.setOnCloseIconClickListener(v -> {
                String fullName = ((Chip) v).getText().toString();
                productMap.remove(fullName.substring(0, fullName.indexOf("/")));
                totalFoodCalories.setText(String.valueOf(calcCaloriesForAllProducts(productMap)));
                chipGroup.removeView(v);
            });
            chipGroup.addView(productChip);
        }
    }

    private double calcCaloriesForAllProducts(Map<String, Double> allProducts) {
        double calories = 0;
        Iterator<Double> iterator = allProducts.values().iterator();
        while (iterator.hasNext()) {
            calories += iterator.next();
        }
        return calories;
    }
}
