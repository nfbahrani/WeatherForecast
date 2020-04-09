package com.example.weatherforecast1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, int position) {
        holder.date.setText(this.weatherinfo.get(i).getDate());
        holder.condition.setText(this.weatherinfo.get(i).getCondition());
        holder.max_tem.setText( this.weatherinfo.get(i).getMax_temperature());
        holder.min_temp.setText( this.weatherinfo.get(i).getMin_temperature());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.date);
            max_tem = itemView.findViewById(R.id.maxtemp);
            min_temp= itemView.findViewById(R.id.mintemp);
            condition = itemView.findViewById(R.id.weather);

        }
    }
}



