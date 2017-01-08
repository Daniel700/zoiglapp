package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 11.02.2016.
 */
public class DataHolder {

    private static DataHolder singleton = new DataHolder();
    private HashMap<String, Tavern> tavernHashMap = new HashMap<String, Tavern>();
    private ArrayList<OpeningDate> calendar = new ArrayList<>();

    public static DataHolder getInstance(){
        return singleton;
    }

    private DataHolder(){
    }





    public void saveCalendar(ArrayList<OpeningDate> list){
        this.calendar = list;
    }


    public void saveTaverns(ArrayList<Tavern> taverns){
        for (Tavern tavern: taverns){
            tavernHashMap.put(tavern.getName(), tavern);
        }
    }


    public HashMap<String, Tavern> getTavernHashMap() {
        return tavernHashMap;
    }

    public ArrayList<OpeningDate> getCalendar() {
        return calendar;
    }
}
