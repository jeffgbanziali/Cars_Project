package com.jeff.schoolproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars;
    private Context context;

    public CarAdapter(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.textViewMakeModel.setText(car.getModel());
        holder.textViewYear.setText(String.valueOf(car.getYear()));
        Glide.with(context).load(car.getImageUrl()).into(holder.imageViewCar);
    }


    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCar;
        TextView textViewMakeModel;
        TextView textViewYear;

        public CarViewHolder(View itemView) {
            super(itemView);

            imageViewCar = itemView.findViewById(R.id.imageViewCar);
            textViewMakeModel = itemView.findViewById(R.id.textViewMakeModel);
            textViewYear = itemView.findViewById(R.id.textViewYear);
        }
    }
}
