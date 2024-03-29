package adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import dbm.zoigl_kalender.R;
import misc.Constants;
import misc.Settings;
import model.DataHolder;
import model.OpeningDate;
import model.Tavern;

import static misc.Constants.AD_POSITION;
import static misc.Constants.ITEM_TYPE_AD;
import static misc.Constants.ITEM_TYPE_NORMAL;


/**
 * Created by Daniel on 10.02.2016.
 */
public class AdapterCalendar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static ArrayList<OpeningDate> calendarList;
    private Context context;

    public AdapterCalendar(ArrayList<OpeningDate> dates){
        calendarList = new ArrayList<>();
        Collections.sort(dates);
        for (int i = 0; i < dates.size(); i++){
            if (i % AD_POSITION == 0)
                calendarList.add(null);

            calendarList.add(dates.get(i));
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_adview, parent, false);
            this.context = parent.getContext();
            return new AdViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calendar, parent, false);
            this.context = parent.getContext();
            return new CalendarViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL){

            OpeningDate event = calendarList.get(position);

            //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = DateFormat.getDateInstance();
            Date startDate = event.getStartDate();
            Date endDate = event.getEndDate();
            cal.setTime(startDate);
            String startDay = Constants.WEEKDAYS[cal.get(Calendar.DAY_OF_WEEK)];
            cal.setTime(endDate);
            String endDay = Constants.WEEKDAYS[cal.get(Calendar.DAY_OF_WEEK)];

            ((CalendarViewHolder)holder).textName.setText(event.getTavernName());
            ((CalendarViewHolder)holder).textStartDate.setText(dateFormat.format(startDate));
            ((CalendarViewHolder)holder).textEndDate.setText(dateFormat.format(endDate));
            ((CalendarViewHolder)holder).textStartDay.setText(startDay);
            ((CalendarViewHolder)holder).textEndDay.setText(endDay);

            Tavern tv = DataHolder.getInstance().getTavernHashMap().get(event.getTavernName());
            if (tv != null){
                ((CalendarViewHolder)holder).textLocation.setText(tv.getPostalCode() + " " + tv.getCity());
                ((CalendarViewHolder)holder).ratingBar.setRating(tv.getRating());
            }
            else
                ((CalendarViewHolder)holder).ratingBar.setRating(0);


            Date currentDate = new Date();
            if ((currentDate.compareTo(startDate) > 0) && (currentDate.compareTo(endDate) < 0))
                ((CalendarViewHolder)holder).textName.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            else
                ((CalendarViewHolder)holder).textName.setBackgroundColor(ContextCompat.getColor(context, R.color.calendarHeadlineNegative));

        }
        else if (getItemViewType(position) == ITEM_TYPE_AD){
            if (Settings.AD_MOB_PRODUCTION_MODE){
                AdRequest adRequest = new AdRequest.Builder().build();
                ((AdViewHolder)holder).adView.loadAd(adRequest);
            }
            else {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice("8CF4B5B17F64F9A1465E73166A097A93").build();
                ((AdViewHolder)holder).adView.loadAd(adRequest);
            }
        }
    }


    @Override
    public int getItemCount() {
        return calendarList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (calendarList.get(position) == null)
            return ITEM_TYPE_AD;
        else
            return ITEM_TYPE_NORMAL;
    }

}
