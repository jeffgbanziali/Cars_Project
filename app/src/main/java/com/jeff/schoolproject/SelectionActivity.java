package com.jeff.schoolproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private List<Car> carList = new ArrayList<>();
    private CarService carService;

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        user = auth.getCurrentUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser Retrofit
        // J'ai utlisé une API dde voiture sur le site Zyla
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zylalabs.com/api/10/automobile+data+api/43/cars/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Créer une instance du service
        carService = retrofit.create(CarService.class);

        // Initialiser l'adaptateur avec la liste vide des voitures
        adapter = new CarAdapter(carList, SelectionActivity.this);
        recyclerView.setAdapter(adapter);

        // Charger la liste des voitures
        loadCars();
    }

    private void loadCars() {
        Call<List<Car>> call = carService.getCars();
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful()) {
                    carList.clear(); // Supprimer tous les éléments de la liste actuelle
                    carList.addAll(response.body()); // Ajouter les éléments de la réponse à la liste
                    adapter.notifyDataSetChanged(); // Mettre à jour l'adaptateur avec la nouvelle liste
                } else {
                    Toast.makeText(SelectionActivity.this, "Erreur : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.e("SelectionActivity", "Erreur : " + t.getMessage());
                Toast.makeText(SelectionActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}



