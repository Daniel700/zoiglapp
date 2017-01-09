package adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.zoiglKalender.R;
import model.Review;

/**
 * Created by Daniel on 06.01.2017.
 */

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.ViewHolder>{

    private ArrayList<Review> reviewList;

    public AdapterReviews(ArrayList<Review> reviews){
        this.reviewList = reviews;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_reviews)           protected CardView cardView;
        @BindView(R.id.card_view_review_author)     protected TextView author;
        @BindView(R.id.card_view_review_date)       protected TextView date;
        @BindView(R.id.card_view_review_message)    protected TextView message;
        @BindView(R.id.card_view_review_ratingBar)  protected RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }



    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_reviews, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AdapterReviews.ViewHolder holder, int position) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            Review review = reviewList.get(position);

            holder.author.setText(review.getUserName());
            holder.date.setText(dateFormat.format(review.getDate()));
            holder.message.setText(review.getMessage());
            holder.ratingBar.setRating(review.getRating());
    }


}
