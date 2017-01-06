package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private Context context;

    public AdapterReviews(ArrayList<Review> reviews, Context ctx){
        this.reviewList = reviews;
        context = ctx;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_reviews)   protected CardView cardView;

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

    }


}
