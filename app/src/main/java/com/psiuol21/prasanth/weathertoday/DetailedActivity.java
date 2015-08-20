package com.psiuol21.prasanth.weathertoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detailed, container, false);
            Intent intent=getActivity().getIntent();
            String forecas=intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView tv=(TextView)rootView.findViewById(R.id.textpresent);
            tv.setText(forecas);
            return rootView;
        }
    }

    public static class DetailFragment extends Fragment{
        String forecaster;
        public DetailFragment()
        {
        setHasOptionsMenu(true);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View rootview=inflater.inflate(R.layout.fragment_detailed, container, false);
            Intent intent=getActivity().getIntent();
            forecaster="I'm sleepy";
            if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT))
            {
                 forecaster=intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootview.findViewById(R.id.textpresent)).setText(forecaster);


            }
            return rootview;
        }

        Intent createsharedforecast()
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,forecaster+"-BROUGHT TO YOU BY WEATHER TODAY");
            return intent;
        }
    }
}