package br.com.calderani.rafael.tetoedc.adapter;

import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.Project;

/**
 * Created by Rafael on 22/07/2017.
 */
public interface OnItemClickListener {

    void onItemClick(Community item);
    void onItemClick(Project item);
    void onPressAndHold(Project item);

}
