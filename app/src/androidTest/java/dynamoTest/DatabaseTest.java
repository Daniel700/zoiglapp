package dynamoTest;


import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import model.DatabaseHandler;
import model.OpeningDate;
import model.Tavern;

/**
 * Created by Daniel on 02.01.2017.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DatabaseTest {


    @Test
    public void importTavernDataFromCSVtoDynamo(){
        DatabaseHandler handler = new DatabaseHandler(InstrumentationRegistry.getContext());
        ArrayList<Tavern> taverns = new ArrayList<>();

        try {
            InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream("Zoiglstuben.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //Skip first 2 lines
            reader.readLine();
            reader.readLine();

            int count = 3;
            String tmp;
            while ((tmp = reader.readLine()) != null){
                if (tmp.length() > 0 ) {
                    String parts[] = tmp.split("\\+");
                    Log.e("LINE", String.valueOf(count) + parts[0].trim());
                    if (parts.length > 0) {
                        Tavern tavern = new Tavern(parts[0].trim(),parts[1].trim(),Integer.valueOf(parts[2].trim()),parts[3].trim(),parts[4].trim(),
                                Boolean.valueOf(parts[5].trim()),parts[6].trim(),parts[7].trim(),parts[8].trim(),parts[9].trim(),parts[10].trim());
                        taverns.add(tavern);
                    }
                    count++;
                }
            }

            handler.saveTaverns(taverns);

        }
        catch (IOException e){
            e.printStackTrace();
        }


    }


    @Test
    public void importCalendarDataFromCSVtoDynamo(){
        DatabaseHandler handler = new DatabaseHandler(InstrumentationRegistry.getContext());
        ArrayList<OpeningDate> openingDates = new ArrayList<>();

        try {
            InputStream inputStream =  this.getClass().getClassLoader().getResourceAsStream("Termine.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String tmp;
            while ((tmp = reader.readLine()) != null){
                if (tmp.length() > 0){
                    String parts[] = tmp.split("\\+");
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        TimeZone utc = TimeZone.getTimeZone("UTC");
                        sdf.setTimeZone(utc);
                        startDate = sdf.parse(parts[1].trim());
                        endDate = sdf.parse(parts[2].trim());

                        Calendar cal = Calendar.getInstance(utc);
                        cal.setTime(endDate);
                        cal.set(Calendar.HOUR_OF_DAY, 23);
                        cal.set(Calendar.MINUTE,59);
                        endDate = cal.getTime();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if (parts.length > 0) {
                        OpeningDate openingDate = new OpeningDate(parts[0].trim(), startDate, endDate);
                        openingDates.add(openingDate);
                    }
                }
            }

            handler.saveOpeningDates(openingDates);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }




}
