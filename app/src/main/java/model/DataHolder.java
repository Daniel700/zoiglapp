package model;

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

import dbm.zoigl_kalender.Config;

/**
 * Created by Daniel on 11.02.2016.
 */
public class DataHolder{

    private static DataHolder singleton = null;
    AmazonDynamoDBClient ddbClient;
    DynamoDBMapper mapper;
    private Context context;

    private PaginatedList<Event> resultList = null;
    private ArrayList<Event> eventList = new ArrayList<>();

    public static DataHolder getInstance(Context ctx){
        if (singleton == null){
            singleton = new DataHolder(ctx);
        }
        return singleton;
    }

    private DataHolder(Context c){
        this.context = c;
    }

    public void establishDBConnection(){
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                Config.IDENTITY_POOL_ID, // Identity Pool ID
                Regions.EU_WEST_1 // Region
        );

        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setEndpoint(Config.AWS_ENDPOINT);
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
