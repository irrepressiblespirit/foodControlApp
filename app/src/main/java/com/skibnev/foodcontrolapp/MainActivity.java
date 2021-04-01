package com.skibnev.foodcontrolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.skibnev.foodcontrolapp.service.CalcCalories;
import com.skibnev.foodcontrolapp.util.App;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText weight, growth, age;
    Spinner gender, phisicalActivity;
    Button calcCalories;
    TextView harrison_result, mifflin_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        growth = (EditText) findViewById(R.id.growth);
        age = (EditText) findViewById(R.id.age);
        gender = (Spinner) findViewById(R.id.gender);
        phisicalActivity = (Spinner) findViewById(R.id.physical_activity);
        calcCalories = (Button) findViewById(R.id.calc_calories);
        harrison_result = (TextView) findViewById(R.id.harrison_result);
        mifflin_result = (TextView) findViewById(R.id.mifflin_result);
        calcCalories.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(weight.getText()) || TextUtils.isEmpty(growth.getText()) || TextUtils.isEmpty(age.getText())) {
            return;
        }
        CalcCalories calcCalories = App.getComponent().getCalcCaloriesService();
        String harris_res = String.valueOf(calcCalories.calcCaloriesByHarrisBenedickt(gender.getSelectedItem().toString(), Float.parseFloat(weight.getText().toString()), Float.parseFloat(growth.getText().toString()), Integer.parseInt(age.getText().toString())));
        Log.i("Result by Harrison: ", harris_res);
        harrison_result.setText(harris_res);
        String mifflin_res = String.valueOf(calcCalories.calcCaloriesByMiffinSanJeora(gender.getSelectedItem().toString(), Float.parseFloat(weight.getText().toString()), Float.parseFloat(growth.getText().toString()), Integer.parseInt(age.getText().toString()), phisicalActivity.getSelectedItem().toString()));
        Log.i("Result by Mifflin: ", mifflin_res);
        mifflin_result.setText(mifflin_res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onFoodCaloriesMenuClick(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, FoodCaloriesActivity.class);
        intent.putExtra(getResources().getString(R.string.daily_human_norm), TextUtils.isEmpty(harrison_result.getText()) ? String.valueOf(0) : harrison_result.getText().toString());
        startActivity(intent);
    }

    public void onCalcDailyCaloriesMenuClick(MenuItem item) {
    }
}