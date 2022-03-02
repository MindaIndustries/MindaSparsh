package com.minda.sparsh.decorators;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

/**
 * Highlight Saturdays and Sundays with a background
 */
public class HighlightWeekendsDecorator implements DayViewDecorator {

  private final Drawable highlightDrawable;
  private static final int color = Color.parseColor("#00000000");

  public HighlightWeekendsDecorator() {
    highlightDrawable = new ColorDrawable(color);
  }

  @Override public boolean shouldDecorate(final CalendarDay day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH,day.getDay());
    calendar.set(Calendar.MONTH, day.getMonth());
    calendar.set(Calendar.YEAR, day.getYear());
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    final DayOfWeek weekDay = DayOfWeek.of(dayOfWeek);
    return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY;
  }

  @Override public void decorate(final DayViewFacade view) {
    view.setBackgroundDrawable(highlightDrawable);
  }
}
