package model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Date;

/**
 * Created by Daniel on 10.02.2016.
 */
@Deprecated
@DynamoDBTable(tableName = "Zoigl_Kalender")
public class Event implements Comparable<Event> {

    private Integer id;
    private Date startDate;
    private Date endDate;
    private String days;
    private boolean realZoigl;
    private String location;
    private String name;


    @DynamoDBAttribute(attributeName = "Days")
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @DynamoDBAttribute(attributeName = "Date_End")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBAttribute(attributeName = "Echter_Zoigl")
    public boolean isRealZoigl() {
        return realZoigl;
    }

    public void setRealZoigl(boolean realZoigl) {
        this.realZoigl = realZoigl;
    }

    @DynamoDBAttribute(attributeName = "Date_Start")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }



    @Override
    public int compareTo(Event another) {
        return this.getStartDate().compareTo(another.getStartDate());
    }

}
