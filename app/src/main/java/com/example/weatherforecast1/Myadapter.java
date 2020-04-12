package com.example.weatherforecast1;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, int position)
    {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
       int val = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i("date" , String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
        if ( i == 0)
            holder.date.setText("Today");
        else if ( val +i ==1)
            holder.date.setText("Sun");
        else if ( val +i ==2)
            holder.date.setText("Mon");
        else if ( val +i ==3)
            holder.date.setText("Tue");
        else if ( val +i ==4)
            holder.date.setText("Wed");
        else if ( val +i ==5)
            holder.date.setText("Thu");
        else if ( val +i ==6)
            holder.date.setText("Fri");
        else if ( val +i ==7)
             holder.date.setText("Sat");

        holder.condition.setText("Condition:\n"+this.weatherinfo.get(i).getCondition());
        holder.max_tem.setText( this.weatherinfo.get(i).getMax_temperature() +"°C");
        holder.min_temp.setText(this.weatherinfo.get(i).getMin_temperature() +"°C");


        if (this.weatherinfo.get(i).getCondition().contains("Partly cloudy"))
            holder.icon.setImageResource(R.drawable.partlycloudy);

        else if (this.weatherinfo.get(i).getCondition().contains("sun"))
            holder.icon.setImageResource(R.drawable.suny);

        else if (this.weatherinfo.get(i).getCondition().contains("snow"))
            holder.icon.setImageResource(R.drawable.snow);

        else if (this.weatherinfo.get(i).getCondition().contains("bizzard"))
            holder.icon.setImageResource(R.drawable.blizzard);

        else if (this.weatherinfo.get(i).getCondition().contains("cloud"))
            holder.icon.setImageResource(R.drawable.cloudy);

        else if (this.weatherinfo.get(i).getCondition().contains("overcast"))
            holder.icon.setImageResource(R.drawable.cloudy);

        else if (this.weatherinfo.get(i).getCondition().contains("heavy rain"))
            holder.icon.setImageResource(R.drawable.shower);

        else  if (this.weatherinfo.get(i).getCondition().contains("wind"))
            holder.icon.setImageResource(R.drawable.windy);

        else if (this.weatherinfo.get(i).getCondition().contains("thunder"))
            holder.icon.setImageResource(R.drawable.light);

        else if (this.weatherinfo.get(i).getCondition().contains("cluoudy"))
            holder.icon.setImageResource(R.drawable.cloudy);

        else if (this.weatherinfo.get(i).getCondition().contains("shower"))
            holder.icon.setImageResource(R.drawable.shower);

        else if (this.weatherinfo.get(i).getCondition().contains("rain"))
            holder.icon.setImageResource(R.drawable.rainy);

        else if (this.weatherinfo.get(i).getCondition().contains("Thundery"))
            holder.icon.setImageResource(R.drawable.light);
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



