package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.DetailedTavernActivity;
import main.zoiglKalender.R;
import model.Event;

/**
 * Created by Daniel on 10.02.2016.
 */
public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.ViewHolder>{

    private ArrayList<Event> list;
    private Context context;

    public AdapterCalendar(ArrayList<Event> events, Context ctx){
        this.list = events;
        this.context = ctx;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)           protected CardView cardView;
        @BindView(R.id.cardText_Name)       protected TextView textName;
        @BindView(R.id.cardText_startDate)  protected TextView textStartDate;
        @BindView(R.id.cardText_startDay)   protected TextView textStartDay;
        @BindView(R.id.cardText_endDate)    protected TextView textEndDate;
        @BindView(R.id.cardText_endDay)     protected TextView textEndDay;
        @BindView(R.id.cardText_location)   protected TextView textLocation;

       public ViewHolder(View v){
           super(v);
           ButterKnife.bind(this, v);
       }
   }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_calendar, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Event event = list.get(position);
        DateFormat dateFormat = DateFormat.getDateInstance();
        String StringStartDate = dateFormat.format(event.getStartDate());
        String StringEndDate = dateFormat.format(event.getEndDate());
        String StringCompleteDate = StringStartDate + " bis " + StringEndDate;

        holder.textName.setText(event.getName());
        holder.textLocation.setText(event.getLocation());

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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedTavernActivity.class);
                context.startActivity(intent);
            }
        });
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
        return list.size();
    }

}
