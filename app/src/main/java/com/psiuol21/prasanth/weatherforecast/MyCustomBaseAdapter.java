package com.psiuol21.prasanth.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Prasanth on 9/1/2015.
 */
public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<SearchResults> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if(position==0)
                convertView = mInflater.inflate(R.layout.todayxml,parent,false);
            else
            convertView = mInflater.inflate(R.layout.backupxml,parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.list_item_date_forecast);
            holder.weather=(TextView)convertView.findViewById(R.id.list_item_forecast_textview);
            holder.high=(TextView)convertView.findViewById(R.id.list_item_high_textview);
            holder.low=(TextView)convertView.findViewById(R.id.list_item_low_textview);
            holder.iv=(ImageView)convertView.findViewById(R.id.list_item_icon);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(searchArrayList.get(position).getDate());
        holder.weather.setText(searchArrayList.get(position).getWeather());
        holder.high.setText(searchArrayList.get(position).getHigh());
        holder.low.setText(searchArrayList.get(position).getLow());
        switch(searchArrayList.get(position).getWeather())
        {
            case "Rain":holder.iv.setImageResource(R.drawable.art_rain);
                break;
            case "Clouds":holder.iv.setImageResource(R.drawable.art_clouds);
                break;
            case "Clear":holder.iv.setImageResource(R.drawable.art_clear);
                break;
            case "Fog":holder.iv.setImageResource(R.drawable.art_fog);
                break;
            case "Snow":holder.iv.setImageResource(R.drawable.art_snow);
                break;
        }


        return convertView;
            }


    static class ViewHolder {
        TextView date;
        TextView weather;
        TextView high;
        TextView low;
        ImageView iv;

    }
}
