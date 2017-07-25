package br.com.calderani.rafael.tetoedc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.calderani.rafael.tetoedc.R;
import br.com.calderani.rafael.tetoedc.model.Community;

/**
 * Created by Rafael on 22/07/2017.
 */

public class CommunitiesAdapter extends RecyclerView.Adapter<CommunitiesAdapter.CommunityViewHolder>{
    private List<Community> communities;
    private OnItemClickListener listener;

    public CommunitiesAdapter(List<Community> c, OnItemClickListener l){
        this.communities = c;
        this.listener = l;
    }

    public void update(List<Community> communityList) {
        this.communities = communityList;
        notifyDataSetChanged();
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customLayout = inflater.inflate(R.layout.item_community,
                parent, false);

        return new CommunityViewHolder(customLayout);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, final int position) {
        holder.tvName.setText(communities.get(position).getName());
        holder.tvZone.setText(communities.get(position).getZone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(communities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return communities.size();
    }


    class CommunityViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvName;
        TextView tvZone;

        CommunityViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvCommunityName);
            tvZone = (TextView) itemView.findViewById(R.id.tvCommunityZone);
        }


    }
}
