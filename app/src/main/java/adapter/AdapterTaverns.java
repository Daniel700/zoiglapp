package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.DetailedTavernActivity;
import main.zoiglKalender.R;
import model.Tavern;

/**
 * Created by Daniel on 06.01.2017.
 */

public class AdapterTaverns extends RecyclerView.Adapter<AdapterTaverns.ViewHolder>{

    private static ArrayList<Tavern> tavernList;

    public AdapterTaverns(ArrayList<Tavern> taverns){
        this.tavernList = taverns;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_taverns)               protected CardView cardView;
        @BindView(R.id.card_view_tavern_name)           protected TextView name;
        @BindView(R.id.card_view_tavern_location)       protected TextView location;
        @BindView(R.id.card_view_tavern_ratingBar)      protected RatingBar ratingBar;
        @BindView(R.id.card_view_tavern_ratingBarText)  protected TextView ratingBarText;
        @BindView(R.id.card_view_tavern_realZoigl)      protected TextView realZoigl;
        private Context context;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            this.context = v.getContext();
        }

        @OnClick(R.id.card_view_taverns)
        public void detailViewTavern(){
            Intent intent = new Intent(context, DetailedTavernActivity.class);
            intent.putExtra("Tavern", tavernList.get(getLayoutPosition()));
            context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return tavernList.size();
    }

    @Override
    public AdapterTaverns.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_taverns, parent, false);
        return new AdapterTaverns.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tavern currentTavern = tavernList.get(position);
        holder.name.setText(currentTavern.getName());
        holder.location.setText(currentTavern.getStreet() + ", " + String.valueOf(currentTavern.getPostalCode()) + " " + currentTavern.getCity());
        holder.ratingBar.setRating(currentTavern.getRating());
        holder.ratingBarText.setText("(" + String.valueOf(currentTavern.getRatingCount()) + ")");
        if (currentTavern.isRealZoigl())
            holder.realZoigl.setText("Echter Zoigl vom Kommunbrauer");
        else
            holder.realZoigl.setText("");

    }




}
