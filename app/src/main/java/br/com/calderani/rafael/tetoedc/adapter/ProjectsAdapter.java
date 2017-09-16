package br.com.calderani.rafael.tetoedc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.calderani.rafael.tetoedc.R;
import br.com.calderani.rafael.tetoedc.dao.ProjectDAO;
import br.com.calderani.rafael.tetoedc.model.Project;

/**
 * Created by Rafael on 22/07/2017.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private List<Project> projects;
    private OnItemClickListener clickListener;
    //private OnItemSwipeListener swipeListener;

    public ProjectsAdapter(List<Project> p, OnItemClickListener cl){ //, OnItemSwipeListener sl
        this.projects = p;
        this.clickListener = cl;
        //this.swipeListener = sl;
    }

    public void update(List<Project> projectList) {
        this.projects = projectList;
        notifyDataSetChanged();
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customLayout = inflater.inflate(R.layout.item_recycler, parent, false);

        return new RecyclerViewHolder(customLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.tvTitle.setText(projects.get(position).getName());
        holder.tvDescription.setText(projects.get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(projects.get(position));
            }
        });

        /*
        holder.itemView.setOnTouchListener(new OnSwipeTouchListener(holder.itemView.getContext()) {
            public void onSwipeRight() {}
            public void onSwipeLeft() {
                swipeListener.onSwipeLeft(projects.get(position));
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
