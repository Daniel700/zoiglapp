package model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Date;

/**
 * Created by Daniel on 02.01.2017.
 */

@DynamoDBTable(tableName = "reviews")
public class Review implements Comparable<Review> {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String tavernName;
    @DynamoDBRangeKey
    @DynamoDBAttribute
    private String userID;
    @DynamoDBAttribute
    private Date date;
    @DynamoDBAttribute
    private float rating;
    @DynamoDBAttribute
    private String message;
    @DynamoDBAttribute
    private String userName;


    public Review(String tavernName, String userID, String userName, String message, float rating, Date date) {
        this.tavernName = tavernName;
        this.userID = userID;
        this.userName = userName;
        this.message = message;
        this.rating = rating;
        this.date = date;
    }

    public Review() {
    }


    @Override
    public int compareTo(Review another) {
        return this.getDate().compareTo(another.getDate());
    }




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTavernName() {
        return tavernName;
    }

    public void setTavernName(String tavernName) {
        this.tavernName = tavernName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
