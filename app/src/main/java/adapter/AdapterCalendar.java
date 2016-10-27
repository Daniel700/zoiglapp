package adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import dbm.zoigl_kalender.R;
import model.Event;

/**
 * Created by Daniel on 10.02.2016.
 */
public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.ViewHolder>{

    ArrayList<Event> list;
    Context context;

    public AdapterCalendar(ArrayList<Event> events, Context ctx){
        this.list = events;
        this.context = ctx;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardText_Name)
        protected TextView textName;
        @BindView(R.id.cardText_Date)
        protected TextView textDate;
        @BindView(R.id.cardText_Days)
        protected TextView textDays;
        @BindView(R.id.cardText_Location)
        protected TextView textLocation;
        @BindView(R.id.cardText_realZoigl)
        protected TextView textRealZoigl;
        @BindView(R.id.card_view)
        protected CardView cardView;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String StringStartDate = simpleDateFormat.format(event.getStartDate());
        String StringEndDate = simpleDateFormat.format(event.getEndDate());
        String StringCompleteDate = StringStartDate + " bis " + StringEndDate;

        holder.textName.setText(event.getName());
        holder.textDate.setText(StringCompleteDate);
        holder.textDays.setText(event.getDays());
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

        if ((currentDate.compareTo(startDate) > 0) && (currentDate.compareTo(endDate) < 0)) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.cardViewStandard));
        }

        if (event.isRealZoigl()){
            holder.textRealZoigl.setText(context.getString(R.string.echterZoigl));
        }
        else {
            holder.textRealZoigl.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
