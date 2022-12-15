package com.minda.sparsh;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.ALMSRecyclerViewAdapter;
import com.minda.sparsh.decorators.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;


public class ALMSCalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendar_view;
    Toolbar toolbar;
    TextView title;
    RelativeLayout calendarLayout, listLayout, main_container;
    RecyclerView attendance_rv;
    ALMSRecyclerViewAdapter almsRecyclerViewAdapter;
    ArrayList<String> almsList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alms_calendar_layout);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        calendarLayout = findViewById(R.id.calendar_layout);
        listLayout = findViewById(R.id.list_layout);
        main_container = findViewById(R.id.main_container);
        attendance_rv = findViewById(R.id.attendance_rv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Attendance");

        calendar_view = findViewById(R.id.calendar_view);
        LocalDate temp = LocalDate.now();
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final CalendarDay day = CalendarDay.from(temp);
            dates.add(day);
            temp = temp.plusDays(5);
        }

        calendar_view.addDecorator(new EventDecorator(Color.RED, dates));
        almsRecyclerViewAdapter = new ALMSRecyclerViewAdapter(ALMSCalendarActivity.this, almsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ALMSCalendarActivity.this, LinearLayoutManager.VERTICAL, false);
        attendance_rv.setLayoutManager(mLayoutManager);
        attendance_rv.setAdapter(almsRecyclerViewAdapter);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if(item.getItemId() == R.id.listview){
            if(calendarLayout.getVisibility()== View.VISIBLE){
                listLayout.setVisibility(View.VISIBLE);
                calendarLayout.setVisibility(View.GONE);
                item.setIcon(R.drawable.calendar_white);

                }
            else{
                listLayout.setVisibility(View.GONE);
                calendarLayout.setVisibility(View.VISIBLE);
                item.setIcon(R.drawable.listview_icon) ;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
