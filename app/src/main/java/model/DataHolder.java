package model;

import android.app.Application;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import misc.Settings;

/**
 * Created by Daniel on 11.02.2016.
 */
public class DataHolder extends Application{

    private static DataHolder singleton = null;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;
    private PaginatedList<Event> resultList = null;
    private ArrayList<Event> eventList = new ArrayList<>();


    public static DataHolder getInstance(){
        if (singleton == null){
            singleton = new DataHolder();
        }
        return singleton;
    }


    public void establishDBConnection(){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), Settings.IDENTITY_POOL_ID, Regions.EU_WEST_1);

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setEndpoint(Settings.AWS_ENDPOINT);
        mapper = new DynamoDBMapper(ddbClient);
    }


    public void fetchAllData(){
        if (resultList == null) {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            resultList = mapper.scan(Event.class, scanExpression);
            resultList.loadAllResults();
            }
    }

    public ArrayList<Event> getListPerMonth(int month){

        if (resultList != null) {
            if (eventList.size() == 0) {
                for (Event event : resultList) {
                    eventList.add(event);
                }
                Collections.sort(eventList);
            }

            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            ArrayList<Event> tmpList = new ArrayList<>();

            if (month == 12) {
                return eventList;
            } else {
                for (Event e : eventList) {
                    cal.setTime(e.getStartDate());
                    cal2.setTime(e.getEndDate());
                    if (cal.get(Calendar.MONTH) == month || cal2.get(Calendar.MONTH) == month)
                        tmpList.add(e);
                }
                return tmpList;
            }
        }

        return new ArrayList<>();
    }

}
