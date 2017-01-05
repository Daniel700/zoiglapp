package dynamoTest;


import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import model.DataHolder;
import model.DatabaseHandler;
import model.Event;
import model.OpeningDate;
import model.Tavern;

/**
 * Created by Daniel on 02.01.2017.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DatabaseTest {


    @Test
    public void testConnection(){

        DatabaseHandler handler = new DatabaseHandler(InstrumentationRegistry.getContext());

        handler.loadEventsAndSaveToDataHolder();

        ArrayList<Event> list = DataHolder.getInstance().getListPerMonth(1);

        for (Event event: list) {
            System.out.println(event.getId() + " " + event.getName() + " "  + event.getStartDate());
        }

    }


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

            String tmp;

            while ((tmp = reader.readLine()) != null){
                String parts[] = tmp.split("\\+");

                if (parts.length > 0) {
                Tavern tavern = new Tavern();
                tavern.setName(!parts[0].trim().equals("?") ? parts[0].trim() : null);
                    tavern.setStreet(!parts[1].trim().equals("?") ? parts[1].trim() : null);
                    tavern.setCity(!parts[2].trim().equals("?") ? parts[2].trim() : null);
                    tavern.setOpeningHours(!parts[3].trim().equals("?") ? parts[3].trim() : null);
                    tavern.setRealZoigl(parts[4].trim().equals("true"));
                    tavern.setRating(0);
                    tavern.setURL(!parts[6].trim().equals("?") ? parts[6].trim() : null);
                    tavern.setOwner(!parts[7].trim().equals("?") ? parts[7].trim() : null);
                    tavern.setPhoneNumber(!parts[8].trim().equals("?") ? parts[8].trim() : null);
                    tavern.setMobilePhoneNumber(!parts[9].trim().equals("?") ? parts[9].trim() : null);
                    tavern.setEmail(!parts[10].trim().equals("?") ? parts[10].trim() : null);

                    handler.saveTaverns(tavern);
                    taverns.add(tavern);
                }

                System.out.println(parts[0]);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }


    }


    @Test
    public void importEventDataFromCSVtoDynamo(){

    }




}
