package com.jeff.schoolproject;

public class Car {
    private String make;
    private String model;
    private int year;
    private String imageUrl;

    public Car(String make, String model, int year, String imageUrl) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}
