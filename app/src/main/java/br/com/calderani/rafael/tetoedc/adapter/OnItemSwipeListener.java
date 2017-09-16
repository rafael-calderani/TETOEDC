package br.com.calderani.rafael.tetoedc.adapter;

import br.com.calderani.rafael.tetoedc.model.Community;
import br.com.calderani.rafael.tetoedc.model.Project;

public interface OnItemSwipeListener {
    void onSwipeLeft(Community item);
    void onSwipeLeft(Project item);
}
