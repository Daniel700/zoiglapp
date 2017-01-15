package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbm.zoigl_kalender.DetailedTavernActivity;
import dbm.zoigl_kalender.R;


/**
 * Created by Daniel on 11.01.2017.
 */

public class TavernsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_view_taverns)               protected CardView cardView;
    @BindView(R.id.card_view_tavern_name)           protected TextView name;
    @BindView(R.id.card_view_tavern_location)       protected TextView location;
    @BindView(R.id.card_view_tavern_ratingBar)      protected RatingBar ratingBar;
    @BindView(R.id.card_view_tavern_ratingBarText)  protected TextView ratingBarText;
    @BindView(R.id.card_view_tavern_realZoigl)      protected TextView realZoigl;
    protected Context context;

    public TavernsViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
        this.context = v.getContext();
    }

    @OnClick(R.id.card_view_taverns)
    public void detailViewTavern(){
        Intent intent = new Intent(context, DetailedTavernActivity.class);
        intent.putExtra("Tavern", AdapterTaverns.tavernList.get(getLayoutPosition()));
        context.startActivity(intent);
    }

}