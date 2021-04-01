package com.skibnev.foodcontrolapp.service;

public enum PhisActivities {
    MINIMAL("минимальная", 1.2f),
    WEAK("слабая", 1.375f),
    MODERATE("умеренная", 1.55f),
    HAVY("тяжелая", 1.7f),
    MAXIMUM("максимальная", 1.9f);

    private String name;
    private float coef;

    private PhisActivities(String name, float coef) {
        this.name = name;
        this.coef = coef;
    }

    public static float getCoefByName(String activityName) {
        for (PhisActivities activity : PhisActivities.values()) {
            if (activity.getName().equals(activityName)) {
                return activity.getCoef();
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public float getCoef() {
        return coef;
    }
}
