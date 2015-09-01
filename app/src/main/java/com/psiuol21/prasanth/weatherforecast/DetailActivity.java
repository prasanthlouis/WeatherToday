package com.psiuol21.prasanth.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {
    String forecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.newfragmodify);
       forecast=getIntent().getStringExtra("Forecast");



        String s[]=forecast.split(" ");
        String s1[]=s[6].split("/");
        TextView date = (TextView)findViewById(R.id.list_item_date_forecast);
        TextView weather=(TextView)findViewById(R.id.list_item_forecast_textview);
        TextView high=(TextView)findViewById(R.id.list_item_high_textview);
        TextView low=(TextView)findViewById(R.id.list_item_low_textview);
        ImageView iv=(ImageView)findViewById(R.id.list_item_icon);
        date.setText(s[0]+" "+s[1]+" "+s[2]);
        weather.setText(s[4]);
        high.setText(s1[0]);
        low.setText(s1[1]);
        switch(s[4])
        {
            case "Rain":iv.setImageResource(R.drawable.art_rain);
                break;
            case "Clouds":iv.setImageResource(R.drawable.art_clouds);
                break;
            case "Clear":iv.setImageResource(R.drawable.art_clear);
                break;
            case "Fog":iv.setImageResource(R.drawable.art_fog);
                break;
            case "Snow":iv.setImageResource(R.drawable.art_snow);
                break;
        }



    }
    private Intent createshareintent(){
    Intent shareintent=new Intent(Intent.ACTION_SEND);
    shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    shareintent.setType("text/plain");
    shareintent.putExtra(Intent.EXTRA_TEXT, forecast +"-->"+ "#WeatherForecast App");
    return shareintent;
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        getMenuInflater().inflate(R.menu.detailfargment,menu);
        MenuItem menuItem=menu.findItem(R.id.action_share);
        ShareActionProvider sap=(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if(sap!=null)
        {
            sap.setShareIntent(createshareintent());
        }

                return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id==R.id.action_settings)
        {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
