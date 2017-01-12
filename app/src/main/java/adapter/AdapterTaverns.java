package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

import dbm.zoigl_kalender.R;
import model.Tavern;

import static misc.Constants.AD_POSITION;
import static misc.Constants.ITEM_TYPE_AD;
import static misc.Constants.ITEM_TYPE_NORMAL;

/**
 * Created by Daniel on 06.01.2017.
 */

public class AdapterTaverns extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static ArrayList<Tavern> tavernList;
    private Context context;

    public AdapterTaverns(ArrayList<Tavern> taverns){
        this.tavernList = taverns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_adview, parent, false);
            this.context = parent.getContext();
            return new AdViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_taverns, parent, false);
            this.context = parent.getContext();
            return new TavernsViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {

            Tavern currentTavern = tavernList.get(getRealPosition(position));
            ((TavernsViewHolder)holder).name.setText(currentTavern.getName());
            ((TavernsViewHolder)holder).location.setText(currentTavern.getStreet() + ", " + String.valueOf(currentTavern.getPostalCode()) + " " + currentTavern.getCity());
            ((TavernsViewHolder)holder).ratingBar.setRating(currentTavern.getRating());
            ((TavernsViewHolder)holder).ratingBarText.setText("(" + String.valueOf(Math.round(currentTavern.getRatingCount())) + ")");
            if (currentTavern.isRealZoigl())
                ((TavernsViewHolder)holder).realZoigl.setText(context.getString(R.string.echterZoigl));
            else
                ((TavernsViewHolder)holder).realZoigl.setText("");

        }
        else if (getItemViewType(position) == ITEM_TYPE_AD){
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("2D18A580DC26C325F086D6FB9D84F765").build();
            ((AdViewHolder)holder).adView.loadAd(adRequest);
            Log.e("test", ((AdViewHolder)holder).adView.toString());
        }

    }


    @Override
    public int getItemCount() {
        int additionalContent = 0;
        if (tavernList.size() > 0 && tavernList.size() > AD_POSITION) {
            additionalContent = tavernList.size() / AD_POSITION;
        }
        return tavernList.size() + additionalContent;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % AD_POSITION == 0 && position > 0){
            return ITEM_TYPE_AD;
        }
        return ITEM_TYPE_NORMAL;
    }

    protected static int getRealPosition(int position) {
        if (position % AD_POSITION != 0 && position > AD_POSITION){
            return position - (position/AD_POSITION);
        }
        else
            return position;
    }

}
