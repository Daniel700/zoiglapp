package adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.zoiglKalender.R;
import model.Event;

/**
 * Created by Daniel on 10.02.2016.
 */
public class AdapterCalendar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int AD_POSITION = 5;
    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_AD = 1;
    private ArrayList<Event> list;

    public AdapterCalendar(ArrayList<Event> events){
        this.list = events;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_adview, parent, false);
            return new AdViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calendar, parent, false);
            return new CalendarViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL){
            Log.e("POS", String.valueOf(position));
            Log.e("SIZE", String.valueOf(list.size()));
            Log.e("REAL", String.valueOf(getRealPosition(position)));
            Event event = list.get(getRealPosition(position));
            DateFormat dateFormat = DateFormat.getDateInstance();
            String StringStartDate = dateFormat.format(event.getStartDate());
            String StringEndDate = dateFormat.format(event.getEndDate());
            String StringCompleteDate = StringStartDate + " bis " + StringEndDate;

            ((CalendarViewHolder)holder).textName.setText(event.getName());
            ((CalendarViewHolder)holder).textLocation.setText(event.getLocation());

            Date currentDate = new Date();
            Date startDate = event.getStartDate();
            Date endDate = event.getEndDate();

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY,0);
            startDate = cal.getTime();

            cal.setTime(endDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE,59);
            endDate = cal.getTime();
        }
        else if (getItemViewType(position) == ITEM_TYPE_AD){
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("2D18A580DC26C325F086D6FB9D84F765").build();
            ((AdViewHolder)holder).adView.loadAd(adRequest);
        }
/*
        if ((currentDate.compareTo(startDate) > 0) && (currentDate.compareTo(endDate) < 0)) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardViewStandard));
        }
*/
    }

    @Override
    public int getItemCount() {
        int additionalContent = 0;
        if (list.size() > 0 && list.size() > AD_POSITION) {
            additionalContent = list.size() / AD_POSITION;
        }
        return list.size() + additionalContent;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % AD_POSITION == 0 && position > 0){
            return ITEM_TYPE_AD;
        }
        return ITEM_TYPE_NORMAL;
    }

    private int getRealPosition(int position) {
        if (position % AD_POSITION != 0 && position > AD_POSITION){
            return position - (position/AD_POSITION);
        }
        else
            return position;
    }

}
