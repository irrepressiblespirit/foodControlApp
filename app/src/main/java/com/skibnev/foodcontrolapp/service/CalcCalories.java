package com.skibnev.foodcontrolapp.service;

import com.skibnev.foodcontrolapp.model.Product;

public class CalcCalories {

    public float calcCaloriesByHarrisBenedickt(String gender, float weight, float growth, int age) {
        switch (gender) {
            case "мужчина":
                return ((88.362f + (13.397f * weight) + (4.799f * growth)) - (5.677f * age));
            case "женщина":
                return ((447.593f + (9.247f * weight) + (3.098f * growth)) - (4.330f * age));
            default:
                return 0;
        }
    }

    public float calcCaloriesByMiffinSanJeora(String gender, float weight, float growth, int age, String phisActivity) {
        switch (gender) {
            case "мужчина":
                return (((((10f * weight) + (6.25f * growth)) - (5f * age)) + 5) * PhisActivities.getCoefByName(phisActivity));
            case "женщина":
                return (((((10f * weight) + (6.25f * growth)) - (5f * age)) - 161) * PhisActivities.getCoefByName(phisActivity));
            default:
                return 0;
        }
    }

    public double calcProductCalories(boolean grammIsChecked, boolean piecesIsChecked, double quantity, Product product) {
        if (grammIsChecked) {
            if (product.getWeight() != 0) {
                return ((quantity * product.getCalories()) / product.getWeight());
            } else {
                return ((quantity * product.getCalories())) / 100;
            }
        } else if (piecesIsChecked) {
            return (quantity * product.getCalories());
        }
        return 0;
    }
}
