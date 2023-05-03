package com.jeff.schoolproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarService {

    @GET("/cars")
    Call<List<Car>> getCars();
}
