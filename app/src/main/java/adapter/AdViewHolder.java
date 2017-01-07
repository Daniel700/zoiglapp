package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.zoiglKalender.R;

/**
 * Created by Daniel on 07.01.2017.
 */

public class AdViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.adView_banner) AdView adView;

    public AdViewHolder(View itemView) {
        super (itemView);
        ButterKnife.bind(this, itemView);
    }

}
