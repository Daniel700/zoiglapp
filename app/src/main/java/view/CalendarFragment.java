package view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adapter.AdapterCalendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dbm.zoigl_kalender.R;
import misc.Settings;
import model.DataHolder;
import model.DatabaseHandler;


public class CalendarFragment extends Fragment {

    @BindView(R.id.recycler_view_calendar)  RecyclerView recyclerView;
    @BindView(R.id.fab_refresh)             FloatingActionButton fab;
    @BindView(R.id.spinner_months)          Spinner spinnerMonths;
    @BindView(R.id.textView_Date)           TextView textView_Date;
    @BindView(R.id.progressBar)             ProgressBar progressBar;
    @BindView(R.id.adView_banner)           AdView adView;
    private InterstitialAd interstitialAd;
    private Unbinder unbinder;
    private AdapterCalendar adapterCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchDataTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        loadAds();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        textView_Date.setText(simpleDateFormat.format(new Date()));

        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Show interstitial Ad
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                loadInterstitialAd();

                adapterCalendar = new AdapterCalendar(DataHolder.getInstance().getListPerMonth(position), getContext());
                recyclerView.setAdapter(adapterCalendar);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
                    fab.setVisibility(View.INVISIBLE);
                }
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





    private void loadAds(){
        loadInterstitialAd();
        loadBannerAd();
    }

    private void loadInterstitialAd(){
        //Initialize Interstitial Ad
        interstitialAd = new InterstitialAd(getContext());

        if (Settings.AD_MOB_PRODUCTION_MODE){
            interstitialAd.setAdUnitId(getString(R.string.interstitial));
            AdRequest adRequest2 = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest2);
        }
        else {
            interstitialAd.setAdUnitId(getString(R.string.interstitial_test));
            //AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            AdRequest adRequest3 = new AdRequest.Builder().addTestDevice("2D18A580DC26C325F086D6FB9D84F765").build();
            interstitialAd.loadAd(adRequest3);
        }
    }

    private void loadBannerAd(){
        if (Settings.AD_MOB_PRODUCTION_MODE){
            //Production Mode
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
        else {
            //Test Mode
            AdRequest adRequest1 = new AdRequest.Builder().addTestDevice("2D18A580DC26C325F086D6FB9D84F765").build();
            adView.loadAd(adRequest1);
        }
    }










    class FetchDataTask extends AsyncTask<Void, Void, Void> {

        Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (recyclerView != null && progressBar != null){
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected Void doInBackground(Void... params) {

            //Load Data from DynamoDB
            try {
                DatabaseHandler handler = new DatabaseHandler(getContext());
                handler.downloadDataAndSaveToDataHolder();
                Thread.sleep(500);
            }
            catch (Exception e){
                error = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (error != null && progressBar != null){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), getString(R.string.connectionException), Toast.LENGTH_LONG).show();
            }

            if (recyclerView != null && progressBar != null){
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            spinnerMonths.setSelection(calendar.get(Calendar.MONTH));

        }
    }


    @OnClick(R.id.fab_refresh)
    public void refreshEventList(){
        loadInterstitialAd();
        new FetchDataTask().execute();
    }

}
