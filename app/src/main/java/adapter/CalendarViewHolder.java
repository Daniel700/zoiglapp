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
import main.DetailedTavernActivity;
import main.zoiglKalender.R;
import model.DataHolder;
import model.OpeningDate;
import model.Tavern;

/**
 * Created by Daniel on 07.01.2017.
 */

public class CalendarViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_view)           protected CardView cardView;
    @BindView(R.id.cardText_Name)       protected TextView textName;
    @BindView(R.id.cardText_startDate)  protected TextView textStartDate;
    @BindView(R.id.cardText_startDay)   protected TextView textStartDay;
    @BindView(R.id.cardText_endDate)    protected TextView textEndDate;
    @BindView(R.id.cardText_endDay)     protected TextView textEndDay;
    @BindView(R.id.cardText_location)   protected TextView textLocation;
    @BindView(R.id.card_view_calendar_rating_bar)   protected RatingBar ratingBar;
    private Context context;

    public CalendarViewHolder(View v){
        super(v);
        ButterKnife.bind(this, v);
        this.context = v.getContext();
    }

    @OnClick(R.id.card_view)
    public void detailViewTavern(){
        OpeningDate openingDate = AdapterCalendar.calendarList.get(AdapterCalendar.getRealPosition(getLayoutPosition()));
        Tavern tavern = DataHolder.getInstance().getTavernHashMap().get(openingDate.getTavernName());
        Intent intent = new Intent(context, DetailedTavernActivity.class);
        intent.putExtra("Tavern", tavern);
        context.startActivity(intent);
    }

}
