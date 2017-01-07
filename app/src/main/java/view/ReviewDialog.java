package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.zoiglKalender.R;

/**
 * Created by Daniel on 06.01.2017.
 */

public class ReviewDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialog_button_positive) Button button_positive;
    @BindView(R.id.dialog_button_negative) Button button_negative;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_review,container, false);
        ButterKnife.bind(this, view);

        //getDialog().setCanceledOnTouchOutside(true);
        getDialog().setTitle("Rezension verfassen");


        //ToDo remove empty lines of reviews / limit newlines?
        return view;
    }





    @OnClick(R.id.dialog_button_positive)
    public void pressedButtonPositive(){
        Toast.makeText(getContext(), "Positiv", Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.dialog_button_negative)
    public void pressedButtonNegative(){
        dismiss();
    }

}
