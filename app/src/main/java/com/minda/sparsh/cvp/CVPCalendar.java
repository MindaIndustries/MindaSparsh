package com.minda.sparsh.cvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel;
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarListener;
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView;

import java.util.Calendar;



public class CVPCalendar extends AppCompatActivity implements HorizontalCalendarListener {
    HorizontalCalendarView hcv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cvp_calendar);
        hcv = findViewById(R.id.horizontalcalendarview);
        hcv.setContext(this);
        hcv.showControls(true);

        RecyclerView recyclerView = hcv.findViewById(R.id.recycler_view);


        ((ImageView) ((LinearLayout)((LinearLayout)hcv.getChildAt(0)).getChildAt(0)).getChildAt(0)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ((RecyclerView)((LinearLayout)hcv.getChildAt(0)).getChildAt(1)).smoothScrollToPosition(((LinearLayoutManager)(((RecyclerView)hcv.getParent()).getLayoutManager())).findFirstVisibleItemPosition()-7);
                RecyclerView recyclerView = hcv.findViewById(R.id.recycler_view);
                recyclerView.smoothScrollToPosition(((LinearLayoutManager)(recyclerView.getLayoutManager())).findFirstVisibleItemPosition()-7);


            }

        });
        ((ImageView) ((LinearLayout)((LinearLayout)hcv.getChildAt(0)).getChildAt(2)).getChildAt(0)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ((RecyclerView)((LinearLayout)hcv.getChildAt(0)).getChildAt(1)).smoothScrollToPosition(((LinearLayoutManager)(((RecyclerView)hcv.getParent()).getLayoutManager())).findFirstVisibleItemPosition()-7);
                RecyclerView recyclerView = hcv.findViewById(R.id.recycler_view);
                recyclerView.smoothScrollToPosition(((LinearLayoutManager)(recyclerView.getLayoutManager())).findLastVisibleItemPosition()+7);


            }

        });


        /* starts before 1 month from now */

        Calendar startDate = Calendar.getInstance();
      //  startDate.add(Calendar.MONTH, -1);
        startDate.add(Calendar.MONTH, -2);
        startDate.set(Calendar.DAY_OF_MONTH, 1);



        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
    //    endDate.add(Calendar.MONTH, 1);
        endDate.add(Calendar.YEAR, 1);


        }

    @Override
    public void updateMonthOnScroll(DayDateMonthYearModel selectedDate) {

    }

    @Override
    public void newDateSelected(DayDateMonthYearModel selectedDate) {

    }
}

