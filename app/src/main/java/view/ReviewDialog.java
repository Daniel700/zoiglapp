package view;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.Date;

import adapter.InterfaceCommunicator;
import adapter.RatingChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.DetailedTavernActivity;
import main.zoiglKalender.R;
import model.DatabaseHandler;
import model.Review;
import model.Tavern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Daniel on 06.01.2017.
 */

public class ReviewDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialog_text_input_layout)TextInputLayout textInputLayout;
    @BindView(R.id.dialog_button_info)      ImageButton button_info;
    @BindView(R.id.dialog_button_positive)  Button button_positive;
    @BindView(R.id.dialog_button_negative)  Button button_negative;
    @BindView(R.id.dialog_edit_author)      EditText author;
    @BindView(R.id.dialog_edit_contentText) EditText content;
    @BindView(R.id.dialog_rating_bar)       RatingBar ratingBar;

    private InterfaceCommunicator interfaceCommunicator;
    private RatingChangedListener ratingChangedListener;
    private Tavern tavern;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_review,container, false);
        ButterKnife.bind(this, rootView);

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setTitle("Rezension verfassen");

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tavern = ((DetailedTavernActivity) getActivity()).getTavern();
        try {
            this.interfaceCommunicator = (InterfaceCommunicator) getActivity();
            this.ratingChangedListener = (RatingChangedListener) getActivity();
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement InterfaceCommunicator");
        }
    }


    @OnClick(R.id.dialog_button_positive)
    public void pressedButtonPositive(){

        if (author.getText().toString().trim().isEmpty()){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Bitte einen Namen angeben");
        }
        else if (ratingBar.getRating() == 0){
            Snackbar.make(rootView, "Bitte eine Punktzahl vergeben", Snackbar.LENGTH_LONG).show();
        }
        else {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("InstallSettings", MODE_PRIVATE);
            String user_id = sharedPreferences.getString("UUID", "no_user");
            //ToDo limit new lines?
            String message = content.getText().toString().trim();
            message = message.replaceAll("(?m)^[ \t]*\r?\n", "");
            Review review = new Review(tavern.getName(), user_id, author.getText().toString().trim(), message, ratingBar.getRating(), new Date());

            Log.e("REVIEW DATA", review.getTavernName() + " " + review.getUserID() + " " + review.getUserName() + " " + review.getMessage() + " " + String.valueOf(review.getRating()) + " " + review.getDate().toString());

            new SendReviewTask().execute(review);

        }


    }


    @OnClick(R.id.dialog_button_info)
    public void showReviewInfo(){
        //ToDo nachricht anpassen falls schon rezension abgegeben wurde?
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        builder.setTitle("Hinweis");
        builder.setMessage("Solltest du bereits eine Rezension für diese Zoiglstube abgegeben haben und erneut eine Rezension senden, so wird die alte Rezension überschrieben.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }


    @OnClick(R.id.dialog_button_negative)
    public void pressedButtonNegative(){
        dismiss();
    }





    class SendReviewTask extends AsyncTask<Review, Void, Void> {
        Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Review... reviews) {
            try {
                DatabaseHandler handler = new DatabaseHandler(getContext());
                handler.saveReview(reviews[0]);
                //ToDo first update Tavern and only if successful send review - otherwise show an error message
            }
            catch (Exception e){
                error = e;
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (error != null)
                interfaceCommunicator.sendRequestCode(400);
            else {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("InstallSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(tavern.getName(), ratingBar.getRating());
                editor.apply();

                interfaceCommunicator.sendRequestCode(1);
                ratingChangedListener.sendRating(tavern.getRating());
                dismiss();
            }
        }
    }



}
