package com.psiuol21.prasanth.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v7.widget.ShareActionProvider;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
public static final String Log_tag=DetailActivityFragment.class.getSimpleName();
    public static final String Forecast_Hashtag="#WeatherForecast";
            public String mForecast;
    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }
    private Intent createshareintent(){
        Intent shareintent=new Intent(Intent.ACTION_SEND);
        shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareintent.setType("text/plain");
        shareintent.putExtra(Intent.EXTRA_TEXT, mForecast + Forecast_Hashtag);
        return shareintent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        String forecast=getActivity().getIntent().getStringExtra("Forecast");
        mForecast=forecast;
        TextView tv=(TextView)view.findViewById(R.id.TextView1);
        tv.setText(mForecast);
// TODO Check if this works
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(getActivity(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.detailfargment,menu);
        MenuItem menuItem=menu.findItem(R.id.action_share);
        ShareActionProvider sap=(ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if(sap!=null)
        {
            sap.setShareIntent(createshareintent());
        }

    }
}
