package br.com.calderani.rafael.tetoedc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.calderani.rafael.tetoedc.R;

/**
 * Created by Rafael on 22/08/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvDescription;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
    }
}
