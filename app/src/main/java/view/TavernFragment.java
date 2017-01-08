package view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.AdapterTaverns;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.zoiglKalender.R;
import model.DatabaseHandler;
import model.Tavern;

/**
 * Created by Daniel on 05.01.2017.
 */

public class TavernFragment extends Fragment {

    @BindView(R.id.recycler_view_taverns)   RecyclerView recyclerView;
    @BindView(R.id.progressBar_tavernList)  ProgressBar progressBar;
    @BindView(R.id.fab_refresh_taverns)     FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_taverns, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new FetchTavernDataTask().execute();

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_taverns, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_sort:
                return true;
        }

        return false;
    }





    class FetchTavernDataTask extends AsyncTask<Void, Void, Void> {
        Exception error;
        ArrayList<Tavern> tavernArrayList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Load Data from DynamoDB
            try {
                DatabaseHandler handler = new DatabaseHandler(getContext());
                tavernArrayList = handler.loadTaverns();
                Thread.sleep(500);
            }
            catch (Exception e){
                error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (error != null){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), getString(R.string.connectionException), Toast.LENGTH_LONG).show();
            }
            else
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            AdapterTaverns adapterTaverns = new AdapterTaverns(tavernArrayList);
            recyclerView.setAdapter(adapterTaverns);
        }
    }



    @OnClick(R.id.fab_refresh_taverns)
    public void refreshTavernList(){
        new FetchTavernDataTask().execute();
    }

}
