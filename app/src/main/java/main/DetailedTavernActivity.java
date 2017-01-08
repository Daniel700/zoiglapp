package main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.AdapterReviews;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.zoiglKalender.R;
import model.Review;
import model.Tavern;
import view.ReviewDialog;

/**
 * Created by Daniel on 06.01.2017.
 */

public class DetailedTavernActivity extends AppCompatActivity{

    @BindView(R.id.activity_detailed) LinearLayout layout;
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

    @BindView(R.id.recycler_view_reviews) RecyclerView recyclerView;
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


        //ToDo Handle Review Data...
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<Review> tmpList = new ArrayList<>();
        tmpList.add(new Review());
        tmpList.add(new Review());
        tmpList.add(new Review());
        AdapterReviews adapterReviews = new AdapterReviews(tmpList, getApplicationContext());
        recyclerView.setAdapter(adapterReviews);

    }


    @OnClick(R.id.detail_button_navigation)
    public void startNavigation(){
        Snackbar.make(layout, "Action triggered", Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.fab_review)
    public void createReview(){
        FragmentManager fm = getSupportFragmentManager();
        ReviewDialog reviewDialog = new ReviewDialog();
        reviewDialog.show(fm, "review-dialog");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
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
        //ToDo set Text Views
    }


}
