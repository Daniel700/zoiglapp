package model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Daniel on 11.02.2016.
 */
public class DataHolder {

    private static DataHolder singleton = new DataHolder();
    private ArrayList<Event> eventList;


    public static DataHolder getInstance(){
        return singleton;
    }

    private DataHolder(){
    }


    public void saveEventsToDataHolder(ArrayList<Event> events){
        this.eventList = events;
    }

    public ArrayList<Event> getListPerMonth(int month){

            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            ArrayList<Event> tmpList = new ArrayList<>();

            if (month == 12) {
                return eventList;
            }
            else {
                for (Event e : eventList) {
                    cal.setTime(e.getStartDate());
                    cal2.setTime(e.getEndDate());
                    if (cal.get(Calendar.MONTH) == month || cal2.get(Calendar.MONTH) == month)
                        tmpList.add(e);
                }
                return tmpList;
            }
    }

}
