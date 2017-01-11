package view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapter.AdapterCalendar;
import adapter.MySpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import main.zoiglKalender.R;
import misc.Settings;
import model.DataHolder;
import model.DatabaseHandler;
import model.OpeningDate;


public class CalendarFragment extends Fragment implements AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.linear_layout_calendar)  LinearLayout linearLayout;
    @BindView(R.id.swipe_refresh_calendar)  SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_calendar)  RecyclerView recyclerView;
    @BindView(R.id.fab_refresh_calendar)    FloatingActionButton fab;
    @BindView(R.id.spinner_months)          MySpinner spinnerMonths;
    @BindView(R.id.textView_Date)           TextView textView_Date;
    @BindView(R.id.progressBar)             ProgressBar progressBar;
    @BindView(R.id.activeDatesCheckBox)     CheckBox activeDatesCheckBox;
    private InterstitialAd interstitialAd;
    private Unbinder unbinder;
    private boolean firstSelection = true;
    private boolean showAd = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        loadInterstitialAd();

        DateFormat dateFormat = DateFormat.getDateInstance();
        textView_Date.setText(dateFormat.format(new Date()));

        spinnerMonths.setOnItemSelectedEvenIfUnchangedListener(this);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        spinnerMonths.setSelection(cal.get(Calendar.MONTH));

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calendar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_filter:
                return true;
        }

        return false;
    }


    @Override
    public void onRefresh() {
        new FetchCalendarDataTask().execute();
    }


    @OnClick(R.id.activeDatesCheckBox)
    public void showActiveDates(){
        //show only currently opened taverns
        if (activeDatesCheckBox.isChecked()){
            //Show interstitial Ad
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
            loadInterstitialAd();

            ArrayList<OpeningDate> calendar = DataHolder.getInstance().getCalendar();
            ArrayList<OpeningDate> openedCalendar = new ArrayList<>();
            Date currentDate = new Date();
            for (OpeningDate openingDate: calendar){
                if ((currentDate.compareTo(openingDate.getStartDate()) > 0) && (currentDate.compareTo(openingDate.getEndDate()) < 0))
                    openedCalendar.add(openingDate);
            }
            AdapterCalendar adapterCalendar = new AdapterCalendar(openedCalendar);
            recyclerView.setAdapter(adapterCalendar);
        }
        //show dates for selected month
        else {
            getCalendarDataForMonth(spinnerMonths.getSelectedItemPosition());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (firstSelection){
            new FetchCalendarDataTask().execute();
            firstSelection = false;
        }
        else {
            getCalendarDataForMonth(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    private void getCalendarDataForMonth(int position){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        ArrayList<OpeningDate> calendar = DataHolder.getInstance().getCalendar();

        //All Months
        if (position == 12) {
            AdapterCalendar adapterCalendar = new AdapterCalendar(calendar);
            recyclerView.setAdapter(adapterCalendar);
        }
        //Selected Month equal to position
        else {
            ArrayList<OpeningDate> selectedMonthList = new ArrayList<OpeningDate>();
            for (OpeningDate date : calendar) {
                cal.setTime(date.getStartDate());
                int startMonth = cal.get(Calendar.MONTH);
                cal.setTime(date.getEndDate());
                int endMonth = cal.get(Calendar.MONTH);

                if (startMonth == position || endMonth == position)
                    selectedMonthList.add(date);
            }
            AdapterCalendar adapterCalendar = new AdapterCalendar(selectedMonthList);
            recyclerView.setAdapter(adapterCalendar);
        }
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








    class FetchCalendarDataTask extends AsyncTask<Void, Void, Void> {
        Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Load Data from DynamoDB
            try {
                DatabaseHandler handler = new DatabaseHandler(getContext());
                handler.loadCalendar();
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
            if (error != null){
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);
                Snackbar.make(linearLayout, getString(R.string.connectionException), Snackbar.LENGTH_LONG).show();
            }
            else {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                spinnerMonths.setSelection(cal.get(Calendar.MONTH));

                if (!showAd)
                    showAd = true;
                else {
                    //Show interstitial Ad
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                    loadInterstitialAd();
                }

            }

        }
    }



    @OnClick(R.id.fab_refresh_calendar)
    public void refreshCalendarList(){
        loadInterstitialAd();
        new FetchCalendarDataTask().execute();
    }

}
