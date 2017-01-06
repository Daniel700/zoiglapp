package main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.zoiglKalender.R;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tavern);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Zoiglname");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }






    @OnClick(R.id.detail_button_navigation)
    public void startNavigation(){
        Snackbar.make(layout, "Action triggered", Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.fab_review)
    public void createReview(){
        Snackbar.make(layout, "Action triggered", Snackbar.LENGTH_LONG).show();
    }

}
