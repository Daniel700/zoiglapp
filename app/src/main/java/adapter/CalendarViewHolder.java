package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.DetailedTavernActivity;
import main.zoiglKalender.R;

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
    private Context context;

    public CalendarViewHolder(View v){
        super(v);
        ButterKnife.bind(this, v);
        this.context = v.getContext();
    }

    @OnClick(R.id.card_view)
    public void detailViewTavern(){
        Intent intent = new Intent(context, DetailedTavernActivity.class);
        context.startActivity(intent);
    }

}
