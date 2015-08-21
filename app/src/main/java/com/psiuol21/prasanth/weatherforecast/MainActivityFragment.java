package com.psiuol21.prasanth.weatherforecast;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ArrayAdapter<String> listAdapter;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecastfragment, menu);

    }

    @Override
    public void onStart() {
        super.onStart();
        FetchWeatherTask fwt = new FetchWeatherTask();
        fwt.execute("18954");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FetchWeatherTask fwt = new FetchWeatherTask();
            fwt.execute("18954");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<String> fakedata = new ArrayList<String>();
        fakedata.add("Today-Sunny- 80/30");
        fakedata.add("Tomorrow-Sunny- 90/50");
        fakedata.add("Wednesday-Sunny- 70/40");
        fakedata.add("Thursday-Sunny- 60/20");
        fakedata.add("Friday-Sunny- 60/20");
        fakedata.add("Saturday-Sunny- 60/20");
        fakedata.add("Sunday-Sunny- 60/20");
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, fakedata);
        ListView lv = (ListView) view.findViewById(R.id.listview_forecast);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast=listAdapter.getItem(position);
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("Forecast",forecast);
                startActivity(intent);

            }
        });


        return view;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String forecastJsonStr = null;
            String mode = "json";
            String units = "metric";
            String cnt = "7";
            try {

                String base = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                String query_param = "q";
                String mode_param = "mode";
                String units_param = "units";
                String count_param = "cnt";

                Uri uribuild = Uri.parse(base).buildUpon()
                        .appendQueryParameter(query_param, params[0])
                        .appendQueryParameter(mode_param, mode)
                        .appendQueryParameter(units_param, units)
                        .appendQueryParameter(count_param, cnt)
                        .build();


                URL url = new URL(uribuild.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v("Recieve", forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            try {
                return getWeatherDataFromJson(forecastJsonStr, 7);
            } catch (JSONException e) {
            }
            return null;
        }

        private String getReadableDateString(long time) {

            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }


        private String formatHighLows(double high, double low) {

            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }


        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            listAdapter.clear();
            for(String x:strings)
            listAdapter.add(x);

        }

        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {


            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);



            Time dayTime = new Time();
            dayTime.setToNow();


            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);


            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for (int i = 0; i < weatherArray.length(); i++) {

                String day;
                String description;
                String highAndLow;


                JSONObject dayForecast = weatherArray.getJSONObject(i);


                long dateTime;

                dateTime = dayTime.setJulianDay(julianStartDay + i);
                day = getReadableDateString(dateTime);


                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);


                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            for (String s : resultStrs) {
                Log.v("Entry", "Forecast entry: " + s);
            }
            return resultStrs;

        }
    }
}





