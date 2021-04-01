package com.skibnev.foodcontrolapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private double weight;
    private double protein;
    private double fats;
    private double carbohydrates;
    private double calories;
}
