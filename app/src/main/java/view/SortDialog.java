package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import adapter.SortChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbm.zoigl_kalender.R;

/**
 * Created by Daniel on 16.01.2017.
 */

public class SortDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialog_sort_rbName)          RadioButton radioButton_name;
    @BindView(R.id.dialog_sort_rbRating)        RadioButton radioButton_rating;
    @BindView(R.id.dialog_sort_rbRatingCount)   RadioButton radioButton_ratingCount;
    @BindView(R.id.dialog_sort_button_positive) Button button_positive;
    @BindView(R.id.dialog_sort_button_negative) Button button_negative;

    private SortChangedListener sortChangedListener;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_sort,container, false);
        ButterKnife.bind(this, rootView);
        getDialog().setTitle("Sortieren");

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            this.sortChangedListener = (SortChangedListener) getTargetFragment();
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement SortChangedListener");
        }
    }


    @OnClick(R.id.dialog_sort_button_positive)
    public void pressedButtonPositive(){
        if (radioButton_name.isChecked()){
            sortChangedListener.sendSortOption(1);
            dismiss();
        }
        if (radioButton_rating.isChecked()){
            sortChangedListener.sendSortOption(2);
            dismiss();
        }
        if (radioButton_ratingCount.isChecked()){
            sortChangedListener.sendSortOption(3);
            dismiss();
        }
    }


    @OnClick(R.id.dialog_sort_button_negative)
    public void pressedButtonNegative(){
        dismiss();
    }

}
