package com.example.weatherforecast1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>{
    private ArrayList<City> weatherinfo;
    int i =0;
    public Myadapter(ArrayList<City> weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    @NonNull
    @Override
    public Myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item , parent , false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, int position)
    {

        holder.date.setText("Date: "+this.weatherinfo.get(i).getDate());
        holder.condition.setText("Condition: "+this.weatherinfo.get(i).getCondition());
        holder.max_tem.setText("Max_temp: "+ this.weatherinfo.get(i).getMax_temperature() +"°C");
        holder.min_temp.setText("Min_temp: "+ this.weatherinfo.get(i).getMin_temperature() +"°C");
        if (this.weatherinfo.get(i).getCondition().contains("rain")) {
            holder.icon.setImageResource(R.drawable.rainy);
        }
        if (this.weatherinfo.get(i).getCondition().contains("sun")) {
            holder.icon.setImageResource(R.drawable.suny);
        }
        if (this.weatherinfo.get(i).getCondition().contains("snow")) {
            holder.icon.setImageResource(R.drawable.snow);
        }
        if (this.weatherinfo.get(i).getCondition().contains("bizzard")) {
            holder.icon.setImageResource(R.drawable.blizzard);
        }
        if (this.weatherinfo.get(i).getCondition().contains("cloud")) {
            holder.icon.setImageResource(R.drawable.cloudy);
        }
        if (this.weatherinfo.get(i).getCondition().contains("overcast")) {
            holder.icon.setImageResource(R.drawable.cloudy);
        }
        if (this.weatherinfo.get(i).getCondition().contains("wind")) {
            holder.icon.setImageResource(R.drawable.windy);
        }
        if (this.weatherinfo.get(i).getCondition().contains("thunder")) {
            holder.icon.setImageResource(R.drawable.thunder);
        }


        i +=1;



    }

    @Override
    public int getItemCount() {
        return weatherinfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView date;
        public TextView max_tem;
        public TextView min_temp;
        public TextView condition;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.date);
            max_tem = itemView.findViewById(R.id.maxtemp);
            min_temp= itemView.findViewById(R.id.mintemp);
            condition = itemView.findViewById(R.id.weather);
            icon = itemView.findViewById(R.id.conditionicon);

        }
    }
}



