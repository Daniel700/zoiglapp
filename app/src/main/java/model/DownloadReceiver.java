package model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import misc.Constants;

/**
 * Created by Daniel on 27.10.2016.
 */

public class DownloadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(Constants.LOGGER_DEBUG, intent.getStringExtra("test"));
        ArrayList<Event> events = intent.getParcelableExtra("data");
        Log.d(Constants.LOGGER_DEBUG, String.valueOf(events.size()));


    }

}
