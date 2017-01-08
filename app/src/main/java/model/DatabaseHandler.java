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


    public void establishDBConnection(){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(context, Settings.IDENTITY_POOL_ID, Regions.EU_WEST_1);
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setEndpoint(Settings.AWS_ENDPOINT);
        mapper = new DynamoDBMapper(ddbClient);
    }




    public void loadReviews(String tableName, String tavernName){

    }

    public void saveReview(String tableName, Review review){
        mapper.save(review);
    }


    public ArrayList<OpeningDate> loadCalendar(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedList<OpeningDate> resultList = mapper.scan(OpeningDate.class, scanExpression);
        resultList.loadAllResults();

        ArrayList<OpeningDate> dates = new ArrayList<>();
        dates.addAll(resultList);
        Collections.sort(dates);

        DataHolder.getInstance().saveCalendar(dates);
        loadTaverns();
        return dates;
    }


    public ArrayList<Tavern> loadTaverns(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedList<Tavern> resultList = mapper.scan(Tavern.class, scanExpression);
        resultList.loadAllResults();

        ArrayList<Tavern> taverns = new ArrayList<>();
        taverns.addAll(resultList);
        Collections.sort(taverns);

        DataHolder.getInstance().saveTaverns(taverns);
        return taverns;
    }


    public void updateTavern(String tavernName){
        //1. load specific tavern
        //2. update rating number
        //3. save
    }


    public void saveTaverns(ArrayList<Tavern> list){
        //Each Tavern must be saved separately so that the version attribute gets saved (version attribute will not be saved with batchSave)
        for (int i = 0; i < list.size(); i++){
            mapper.save(list.get(i));
        }
    }


    public void saveOpeningDates(ArrayList<OpeningDate> list){
        mapper.batchSave(list);
    }

}
