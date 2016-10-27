package model;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.Collections;

import misc.Settings;

/**
 * Created by Daniel on 27.10.2016.
 */

public class DatabaseHandler {

    private DynamoDBMapper mapper;
    private Context context;

    public DatabaseHandler(Context ctx){
        context = ctx;
        establishDBConnection();
    }


    public void downloadDataAndSaveToDataHolder() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedList<Event> resultList = mapper.scan(Event.class, scanExpression);
        resultList.loadAllResults();

        ArrayList<Event> eventList = new ArrayList<>();
        eventList.addAll(resultList);
        Collections.sort(eventList);

        DataHolder.getInstance().saveEventsToDataHolder(eventList);
    }


    public void establishDBConnection(){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(context, Settings.IDENTITY_POOL_ID, Regions.EU_WEST_1);
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setEndpoint(Settings.AWS_ENDPOINT);
        mapper = new DynamoDBMapper(ddbClient);
    }


}
