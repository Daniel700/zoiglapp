package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.AdapterTaverns;
import butterknife.BindView;
import butterknife.ButterKnife;
import main.zoiglKalender.R;
import model.Tavern;

/**
 * Created by Daniel on 05.01.2017.
 */

public class TavernFragment extends Fragment {

    @BindView(R.id.recycler_view_taverns) RecyclerView recyclerView;

    private AdapterTaverns adapterTaverns;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_taverns, container, false);
        ButterKnife.bind(this, rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //ToDo Dummy Data...
        Tavern tavern = new Tavern();
        Tavern tavern1 = new Tavern();
        ArrayList<Tavern> arrayList = new ArrayList<Tavern>();
        arrayList.add(tavern);
        arrayList.add(tavern1);

        adapterTaverns = new AdapterTaverns(arrayList, getContext());
        recyclerView.setAdapter(adapterTaverns);

        return rootView;
    }
}
