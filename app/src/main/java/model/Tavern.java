package model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBVersionAttribute;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Daniel on 02.01.2017.
 */

@DynamoDBTable(tableName = "taverns")
public class Tavern implements Comparable<Tavern>, Serializable {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String street;
    @DynamoDBAttribute
    private int postalCode;
    @DynamoDBAttribute
    private String city;
    @DynamoDBAttribute
    private String openingHours;
    @DynamoDBAttribute
    private boolean realZoigl;
    @DynamoDBAttribute
    private float rating;
    @DynamoDBAttribute
    private float ratingSum;
    @DynamoDBAttribute
    private float ratingCount;
    @DynamoDBVersionAttribute
    private Long version;

    //contact details
    @DynamoDBAttribute
    private String url;
    @DynamoDBAttribute
    private String owner;
    @DynamoDBAttribute
    private String phoneNumber;
    @DynamoDBAttribute
    private String mobilePhoneNumber;
    @DynamoDBAttribute
    private String email;


    public Tavern(String name, String street, int postalCode, String city, String openingHours, boolean realZoigl, String url, String owner, String phoneNumber, String mobilePhoneNumber, String email) {
        this.name = name;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.openingHours = openingHours;
        this.realZoigl = realZoigl;
        this.url = url;
        this.owner = owner;
        this.phoneNumber = phoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;

        this.rating = 0;
        this.ratingSum = 0;
        this.ratingCount = 0;
    }

    public Tavern() {
    }


    @Override
    public int compareTo(Tavern tavern) {
        return Comparators.NAME.compare(this, tavern);
    }


    public static class Comparators {

        public static Comparator<Tavern> NAME = new Comparator<Tavern>() {
            @Override
            public int compare(Tavern o1, Tavern o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        public static Comparator<Tavern> RATING = new Comparator<Tavern>() {
            @Override
            public int compare(Tavern o1, Tavern o2) {
                return Float.compare(o2.getRating(), o1.getRating());
            }
        };
        public static Comparator<Tavern> RATING_COUNT = new Comparator<Tavern>() {
            @Override
            public int compare(Tavern o1, Tavern o2) {
                return Float.compare(o2.getRatingCount(), o1.getRatingCount());
            }
        };

    }







    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(float ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(float ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
