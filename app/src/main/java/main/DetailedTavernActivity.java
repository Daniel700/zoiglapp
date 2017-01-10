package main;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import adapter.AdapterReviews;
import adapter.InterfaceCommunicator;
import adapter.RatingChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.zoiglKalender.R;
import model.DatabaseHandler;
import model.Review;
import model.Tavern;
import view.ReviewDialog;

/**
 * Created by Daniel on 06.01.2017.
 */

public class DetailedTavernActivity extends AppCompatActivity implements InterfaceCommunicator, RatingChangedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.coordinator_layout_detail_tavern) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.detail_button_navigation) ImageButton button_navigation;
    @BindView(R.id.detail_button_call) ImageButton button_call;
    @BindView(R.id.detail_button_mail) ImageButton button_mail;
    @BindView(R.id.detail_button_website) ImageButton button_website;
    @BindView(R.id.detail_ratingBarSum) RatingBar ratingBarSum;
    @BindView(R.id.detail_ratingBarOwn) RatingBar ratingBarOwn;

    @BindView(R.id.detail_ratingBarSumText) TextView textView_ratingBarSum;
    @BindView(R.id.detail_hours) TextView textView_hours;
    @BindView(R.id.detail_street) TextView textView_street;
    @BindView(R.id.detail_person) TextView textView_person;
    @BindView(R.id.detail_call) TextView textView_call;
    @BindView(R.id.detail_mail) TextView textView_mail;
    @BindView(R.id.detail_website) TextView textView_website;

    @BindView(R.id.swipe_refresh_reviews) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_reviews) RecyclerView recyclerView;
    @BindView(R.id.progressBar_reviewList) ProgressBar progressBar;
    @BindView(R.id.fab_review) FloatingActionButton fab_review;

    private Tavern tavern;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tavern);
        ButterKnife.bind(this);

        tavern = (Tavern) getIntent().getSerializableExtra("Tavern");
        if (tavern != null){
            initToolbar();
            setContent();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        swipeRefreshLayout.setOnRefreshListener(this);
        new LoadReviewsTask().execute();
    }


    @OnClick(R.id.detail_button_navigation)
    public void startNavigation(){
        Snackbar.make(coordinatorLayout, "Action triggered", Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.fab_review)
    public void createReview(){
        SharedPreferences sharedPreferences = getSharedPreferences("InstallSettings", MODE_PRIVATE);
        boolean alreadyVoted = sharedPreferences.getBoolean(tavern.getName(), false);
        //ToDo show message in info alert dialog if you already wrote a review or not
        //if (!alreadyVoted){
            FragmentManager fm = getSupportFragmentManager();
            ReviewDialog reviewDialog = new ReviewDialog();
            reviewDialog.show(fm, "review-dialog");
       // }
       // else {
        //    Snackbar.make(coordinatorLayout, "Du hast für diese Zoiglstube bereits eine Bewertung abgegeben", Snackbar.LENGTH_LONG).show();
       // }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        new LoadReviewsTask().execute();
    }


    @Override
    public void sendRequestCode(int requestCode) {
        if (requestCode == 1)
            Snackbar.make(coordinatorLayout, "Bewertung wurde erfolgreich an den Server gesendet", Snackbar.LENGTH_LONG).show();
        if (requestCode == 400)
            Snackbar.make(coordinatorLayout, "Bewertung konnte nicht an den Server gesendet werden", Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void sendRating(float rating) {
        ratingBarOwn.setRating(rating);
        //ToDo invalidate ratingBarOwn???
        //ToDo recalculate ratingSum
    }


    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tavern.getName());
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    public void setContent(){
        ratingBarSum.setRating(tavern.getRating());
        SharedPreferences sharedPreferences = getSharedPreferences("InstallSettings", MODE_PRIVATE);
        Float rating = sharedPreferences.getFloat(tavern.getName().concat("_rating"), 0);
        ratingBarOwn.setRating(rating);
        textView_ratingBarSum.setText("(" + Math.round(tavern.getRatingCount()) + ")");
        textView_hours.setText(tavern.getOpeningHours().replace("-", "\n"));
        textView_street.setText(tavern.getStreet() + ", " + String.valueOf(tavern.getPostalCode()) + " " + tavern.getCity());
        textView_person.setText(tavern.getOwner());
        textView_call.setText("Telefon: " + tavern.getPhoneNumber() + "\n Mobil: " + tavern.getMobilePhoneNumber());
        textView_mail.setText(tavern.getEmail());
        textView_website.setText(tavern.getUrl());
    }



    public Tavern getTavern(){
        return tavern;
    }





    class LoadReviewsTask extends AsyncTask<Void, Void, Void> {
        Exception error;
        ArrayList<Review> reviewList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
                reviewList = handler.loadAllReviews();
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
            if (error != null)
                Toast.makeText(getApplicationContext(), getString(R.string.connectionException), Toast.LENGTH_LONG).show();
            else{
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);

                if (reviewList.size() > 0){
                    AdapterReviews adapterReviews = new AdapterReviews(reviewList);
                    recyclerView.setAdapter(adapterReviews);
                }
                else {
                    ArrayList<Review> dummyList = new ArrayList<>();
                    dummyList.add(new Review(tavern.getName(), "noUser", "Name", "Noch keine Rezensionen vorhanden", 0, new Date()));
                    AdapterReviews adapterReviews = new AdapterReviews(dummyList);
                    recyclerView.setAdapter(adapterReviews);
                }

            }
        }

    }




}
