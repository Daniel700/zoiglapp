package model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBVersionAttribute;

/**
 * Created by Daniel on 02.01.2017.
 */

@DynamoDBTable(tableName = "taverns")
public class Tavern {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String street;
    @DynamoDBAttribute
    private String city;
    @DynamoDBAttribute
    private String openingHours;
    @DynamoDBAttribute
    private boolean realZoigl;
    @DynamoDBAttribute
    private double rating;
    @DynamoDBAttribute
    private double ratingSum;
    @DynamoDBAttribute
    private double ratingCount;
    @DynamoDBVersionAttribute
    private Long version;

    //contact details
    @DynamoDBAttribute
    private String URL;
    @DynamoDBAttribute
    private String owner;
    @DynamoDBAttribute
    private String phoneNumber;
    @DynamoDBAttribute
    private String mobilePhoneNumber;
    @DynamoDBAttribute
    private String email;




    public double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(double ratingCount) {
        this.ratingCount = ratingCount;
    }

    public double getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(double ratingSum) {
        this.ratingSum = ratingSum;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isRealZoigl() {
        return realZoigl;
    }

    public void setRealZoigl(boolean realZoigl) {
        this.realZoigl = realZoigl;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
