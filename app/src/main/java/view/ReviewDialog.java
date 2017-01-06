package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import main.zoiglKalender.R;

/**
 * Created by Daniel on 06.01.2017.
 */

public class ReviewDialog extends AppCompatDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_review,container, false);


        getDialog().setTitle("Rezension verfassen");


        //ToDo remove empty lines of reviews / limit newlines?
        return view;
    }
}
