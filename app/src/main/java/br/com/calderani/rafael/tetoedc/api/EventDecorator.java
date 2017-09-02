package br.com.calderani.rafael.tetoedc.api;

import com.google.api.services.calendar.model.Event;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Rafael on 16/08/2017.
 */
public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final List<Event> events;

    public EventDecorator(int color, List<Event> eventList) {
        this.color = color;
        this.events = eventList;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        boolean result = false;
        for (Event e:events) {
            Date dt = new Date(e.getStart().getDate().getValue());
            CalendarDay check = CalendarDay.from(dt);
            if (check == day) {
                result = true;
                break;
            }

        }
        return result;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
