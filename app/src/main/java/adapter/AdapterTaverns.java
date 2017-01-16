package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

import dbm.zoigl_kalender.R;
import misc.Settings;
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
        tavernList = new ArrayList<>();
        for (int i = 0; i < taverns.size(); i++){
            if (i % AD_POSITION == 0)
                tavernList.add(null);

            tavernList.add(taverns.get(i));
        }
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

            Tavern currentTavern = tavernList.get(position);
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
            if (Settings.AD_MOB_PRODUCTION_MODE){
                AdRequest adRequest = new AdRequest.Builder().build();
                ((AdViewHolder)holder).adView.loadAd(adRequest);
            }
            else {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice("8CF4B5B17F64F9A1465E73166A097A93").build();
                ((AdViewHolder)holder).adView.loadAd(adRequest);
            }
        }

    }


    @Override
    public int getItemCount() {
        return tavernList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (tavernList.get(position) == null)
            return ITEM_TYPE_AD;
        else
            return ITEM_TYPE_NORMAL;
    }

}
