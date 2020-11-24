package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.connection.HttpConnection;
import com.minda.sparsh.model.Dwm_Model;
import com.minda.sparsh.parser.ParseData;
import com.minda.sparsh.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DWMActivity extends AppCompatActivity {
    private SharedPreferences myPref = null;
    TextView Parent_tv;
    private TextView sign_out, one_tv, two_tv, icon, three_tv, submit_btn, tv_total_hour, d_y_tv_total_hour,
            yes_tv_total_hour, d_y_submit_btn, yes_submit_btn, user_name, tv_1_a, tv_1_b, tv_1_c, tv_2_a, tv_2_b, tv_2_c,
            tv_3_a, tv_3_b, tv_4_a, tv_4_b, tv_5_a, tv_5_b, tv_p, tv_n, tv_10_a, tv_10_b, tv_10_c,
            tv_10_2_a, tv_10_2_b, tv_10_2_c,
            tv_10_3_a, tv_10_3_b, tv_10_3_c,
            tv_10_4_a, tv_10_4_b, tv_10_4_c,
            tv_10_5_a, tv_10_5_b, tv_10_5_c,
            tv_check_a, tv_check_b, tv_check_c, tv_1_1_1_1a, tv_1_1b, tv_2_1_1_1b, tv_2_1b, tv_2_cb, tv_2_1_1_1c, tv_2_1c, tv_2_cc, tv_8_a1_bestReview;


    private EditText tv_1_1, tv_1_2, tv_1_3, tv_1_3_a, tv_1_4, tv_1_14, tv_1_5, tv_1_6, tv_1_6a, tv_2_1_1b,
            tv_2_1, tv_2_2, tv_2_3, tv_2_6, tv_2_5, tv_2_4,
            tv_3_1, tv_3_2, tv_3_3, tv_3_6, tv_3_5, tv_3_4,
            tv_4_1, tv_4_2, tv_4_3, tv_4_6, tv_4_5, tv_4_4,
            tv_5_1, tv_5_2, tv_5_3, tv_5_6, tv_5_5, tv_5_4,
            et_6_1_1, et_6_1_2, et_6_1_3, et_6_1_4, et_6_1_5, et_8_1_1_best_review, et_6_1_6,
            et_6_2_1, et_6_2_2, et_6_2_3, et_6_2_4, et_6_2_5, et_8_2_1_bestReview, et_6_2_6,
            et_6_3_1, et_6_3_2, et_6_3_3, et_6_3_4, et_6_3_5, et_8_3_1_bestReview, et_6_3_6,
            cmt6_1_1, cmt6_1_2, cmt6_1_3, cmt6_1_4, cmt6_1_5, cmt6_1_6,
            cmt6_2_1, cmt6_2_2, cmt6_2_3, cmt6_2_4, cmt6_2_5, cmt8_2_1_bestReview, cmt6_2_6,
            cmt6_3_1, cmt6_3_2, cmt6_3_3, cmt6_3_4, cmt6_3_5, cmt8_3_1_bestReview, cmt6_3_6,
            et_7_1_1, et_7_1_2, et_7_1_3, et_8_1_2_unitReview, cmt8_1_2_unitReview, et_8_2_2_unitReview, cmt8_2_2_unitReview, et_7_1_4,
            et_7_2_1, et_7_2_2, et_7_2_3, et_7_2_4, et_8_3_2_unitReview, cmt8_3_2_unitReview,
            et_7_3_1, et_7_3_2, et_7_3_3, et_7_3_4,
            cmt7_1_1, cmt7_1_2, cmt7_1_3, cmt7_1_4,
            cmt7_2_1, cmt7_2_2, cmt7_2_3, cmt7_2_4,
            cmt7_3_1, cmt7_3_2, cmt7_3_3, cmt7_3_4,
            et_8_1_1, et_8_1_2, et_8_1_3, et_8_1_4, et_8_1_5, et_8_1_6, et_8_1_7, et_8_1_8,
            et_8_2_1, et_8_2_2, et_8_2_3, et_8_2_4, et_8_2_5, et_8_2_6, et_8_2_7, et_8_2_8,
            et_8_3_1, et_8_3_2, et_8_3_3, et_8_3_4, et_8_3_5, et_8_3_6, et_8_3_7, et_8_3_8,
            cmt8_1_1, cmt8_1_2, cmt8_1_3, cmt8_1_4, cmt8_1_5, cmt8_1_6, cmt8_1_7, cmt8_1_8,
            cmt8_2_1, cmt8_2_2, cmt8_2_3, cmt8_2_4, cmt8_2_5, cmt8_2_6, cmt8_2_7, cmt8_2_8,
            cmt8_3_1, cmt8_3_2, cmt8_3_3, cmt8_3_4, cmt8_3_5, cmt8_3_6, cmt8_3_7, cmt8_3_8,
            et_9_1_1, et_9_1_2, et_9_1_3,
            et_9_2_1, et_9_2_2, et_9_2_3,
            et_9_3_1, et_9_3_2, et_9_3_3,
            cmt9_1_1, cmt9_1_2, cmt9_1_3,
            cmt9_2_1, cmt9_2_2, cmt9_2_3,
            cmt9_3_1, cmt9_3_2, cmt9_3_3,
            tv_10_1, tv_10_2, tv_10_3, tv_10_6, tv_10_5, tv_10_4, cmt8_1_1_bestReview,
    //
    tv_10_2_1, tv_10_2_2, tv_10_2_3, tv_10_2_6, tv_10_2_5, tv_10_2_4,
            tv_10_3_1, tv_10_3_2, tv_10_3_3, tv_10_3_6, tv_10_3_5, tv_10_3_4,
            tv_10_4_1, tv_10_4_2, tv_10_4_3, tv_10_4_6, tv_10_4_5, tv_10_4_4,
            tv_10_5_1, tv_10_5_2, tv_10_5_3, tv_10_5_6, tv_10_5_5, tv_10_5_4,

    tv_check_1, tv_check_2, tv_check_3, tv_check_4, tv_check_5, tv_check_6, tv_2_6b, tv_1_1_1a, tv_1_1aE, tv_1_5a, tv_2_1b4, tv_2_1bE, tv_2_1b5, tv_2_3b, tv_2_1_1c, tv_2_1c4, tv_2_1cE, tv_2_1c5, tv_2_3c, tv_2_6c;


    private View two_view, three_view, tv_1_1_view, tv_1_2_view, tv_1_c_view, tv_1_4_view, tv_1_5_view,
            tv_2_1_view, tv_2_2_view, tv_2_c_view, tv_2_5_view, tv_2_4_view,
            tv_3_1_view, tv_3_2_view, tv_3_c_view, tv_3_5_view, tv_3_4_view,
            tv_4_1_view, tv_4_2_view, tv_4_c_view, tv_4_5_view, tv_4_4_view,
            tv_5_1_view, tv_5_2_view, tv_5_5_view, tv_5_4_view,
            layout_6_1_view, layout_6_2_view, layout_6_6_view, layout_6_8_view,
            layout_7_1_view, layout_7_2_view, layout_7_6_view, layout_7_8_view,
            layout_8_1_view, layout_8_2_view, layout_8_6_view, layout_8_8_view,
            layout_9_1_view, layout_9_2_view, layout_9_6_view, layout_9_8_view,
            d_y_submit_btn_view, yes_submit_btn_view, d_y_tv_total_hour_view, yes_tv_total_hour_view,
            tv_10_1_view, tv_10_2_view, tv_10_c_view, tv_10_5_view, tv_10_4_view, tv_2_1_viewb, tv_2_5_viewb, tv_2_c_viewb, tv_2_1c_view, tv_2_1c_viewa, tv_2_1_viewc, tv_2_c_viewc, tv_2_c_view2c,
    //
    tv_10_2_1_view, tv_10_2_2_view,
            tv_10_3_1_view, tv_10_3_2_view,
            tv_10_4_1_view, tv_10_4_2_view,
            tv_10_5_1_view, tv_10_5_2_view,
            tv_10_2_5_view, tv_10_2_4_view,
            tv_10_3_5_view, tv_10_3_4_view,
            tv_10_4_5_view, tv_10_4_4_view,
            tv_10_5_5_view, tv_10_5_4_view,
            tv_check_1_view, tv_check_4_view, tv_check_2_view, tv_check_5_view, tv_check_c_view, tv_1_1a_view, tv_1_1a_viewa, tv_1_1_viewa, tv_1_5_viewa, tv_1_c_viewa, tv_2_1b_view, tv_2_1b_viewa;
    //
    private LinearLayout layout_6_1, layout_6_2, layout_6_4, layout_6_5, layout_6_6, layout_6_7, layout_6_8,
            layout_7_1, layout_7_2, layout_7_4, layout_7_5, layout_7_6, layout_7_7, layout_7_8,
            layout_8_1, layout_8_2, layout_8_4, layout_8_5, layout_8_6, layout_8_7, layout_8_8,
            layout_9_1, layout_9_2, layout_9_4, layout_9_5, layout_9_6, layout_9_7, layout_9_8;

    private TimeSheetTask timeSheetTask = null;
    private yesterdayTimeSheetTask yesterdayTimeSheetTask = null;
    private d_y_TimeSheetTask d_y_timeSheetTask = null;
    private GetyesterdayTimeSheetTask getyesterdayTimeSheetTask = null;
    private Get_Current_TimeSheetTask getCurrentTimeSheetTask = null;
    private Getd_y_TimeSheetTask getdYTimeSheetTask = null;
    public int int_tv_1_3 = 0, int_tv_1_3a = 0, int_tv_1_1aE = 0, int_tv_1_1_1a = 0, int_tv_2_3 = 0, int_tv_3_3 = 0, int_tv_4_3 = 0, int_tv_5_3 = 0, int_tv_2_1_1b, int_tv_2_3b,
            int_et_6_3_1 = 0, int_et_6_3_2 = 0, int_et_6_3_3 = 0, int_et_6_3_4 = 0, int_et_6_3_5 = 0, int_et_8_3_1_bestReview = 0, int_et_6_3_6 = 0,
            int_et_7_3_1 = 0, int_et_7_3_2 = 0, int_et_7_3_3 = 0, int_et_8_3_2_unitReview = 0, int_et_7_3_4 = 0,
            int_et_8_3_1 = 0, int_et_8_3_2 = 0, int_et_8_3_3 = 0, int_et_8_3_4 = 0, int_et_8_3_5 = 0, int_et_8_3_6 = 0, int_et_8_3_7 = 0, int_et_8_3_8 = 0,
            int_et_9_3_1 = 0, int_et_9_3_2 = 0, int_et_9_3_3 = 0,
            int_tv_10_1 = 0, int_tv_10_2 = 0, int_tv_10_3 = 0, int_tv_10_2_1 = 0, int_tv_10_2_2 = 0, int_tv_10_2_3 = 0, int_tv_10_3_1 = 0, int_tv_10_3_2 = 0, int_tv_10_3_3 = 0, int_tv_10_4_1 = 0,
            int_tv_10_4_2 = 0, int_tv_10_4_3 = 0, int_tv_2_1_1c,
            int_tv_10_5_1 = 0, int_tv_10_5_2 = 0, int_tv_10_5_3 = 0, int_tv_2_1bE = 0, int_tv_2_1cE = 0, int_tv_2_3c = 0;
    private int int_tv_1_2 = 0, int_tv_2_2 = 0, int_tv_3_2 = 0, int_tv_4_2 = 0, int_tv_5_2 = 0,
            int_et_6_2_1 = 0, int_et_6_2_2 = 0, int_et_6_2_3 = 0, int_et_6_2_4 = 0, int_et_6_2_5 = 0, int_et_8_2_1_bestReview = 0, int_et_6_2_6 = 0,
            int_et_7_2_1 = 0, int_et_7_2_2 = 0, int_et_7_2_3 = 0, int_et_8_2_2_unitReview = 0, int_et_7_2_4 = 0,
            int_et_8_2_1 = 0, int_et_8_2_2 = 0, int_et_8_2_3 = 0, int_et_8_2_4 = 0, int_et_8_2_5 = 0, int_et_8_2_6 = 0, int_et_8_2_7 = 0, int_et_8_2_8 = 0,
            int_et_9_2_1 = 0, int_et_9_2_2 = 0, int_et_9_2_3 = 0;

    private int int_tv_1_1 = 0, int_tv_2_1 = 0, int_tv_3_1 = 0, int_tv_4_1 = 0, int_tv_5_1 = 0,
            int_et_6_1_1 = 0, int_et_6_1_2 = 0, int_et_6_1_3 = 0, int_et_6_1_4 = 0, int_et_6_1_5 = 0, int_et_8_1_1_best_review = 0, int_et_6_1_6 = 0,
            int_et_7_1_1 = 0, int_et_7_1_2 = 0, int_et_7_1_3 = 0, int_et_8_1_2_unitReview = 0, int_et_7_1_4 = 0,
            int_et_8_1_1 = 0, int_et_8_1_2 = 0, int_et_8_1_3 = 0, int_et_8_1_4 = 0, int_et_8_1_5 = 0, int_et_8_1_6 = 0, int_et_8_1_7 = 0, int_et_8_1_8 = 0,
            int_et_9_1_1 = 0, int_et_9_1_2 = 0, int_et_9_1_3 = 0,
            int_check_1_1 = 0, int_check_1_2 = 0, int_check_1_3 = 0;

    private int Add = 0, YesterdayAdd = 0, D_Y_Add = 0;
    //    private int id;
//    private String username;
    private String CDateToStr;
    private String date2;
    private String date1;
    private ArrayList<Dwm_Model> dwmData;
    EditText editText;
    private String tv_1_4_String, tv_1_5_String, tv_1_5a_String, tv_1_14_String, tv_1_6_String, tv_1_6a_String, tv_2_4_String, tv_2_5_String, tv_2_6_String, tv_3_4_String, tv_3_5_String, tv_3_6_String, tv_4_4_String, tv_4_5_String, tv_4_6_String, tv_5_4_String, tv_5_5_String, tv_5_6_String, tv_2_1b4_String, tv_2_1b5_String, tv_2_6b_String,
            tv_2_1c4_String, tv_2_1c5_String, tv_2_6c_String,
            cmt6_1_1_String, cmt6_1_2_String, cmt6_1_3_String, cmt6_1_4_String, cmt6_1_5_String, cmt8_1_1_bestReview_String, cmt6_1_6_String,
            cmt6_2_1_String, cmt6_2_2_String, cmt6_2_3_String, cmt6_2_4_String, cmt6_2_5_String, cmt8_2_1_bestReview_String, cmt6_2_6_String,
            cmt6_3_1_String, cmt6_3_2_String, cmt6_3_3_String, cmt6_3_4_String, cmt6_3_5_String, cmt8_3_1_bestReview_String, cmt6_3_6_String,
            cmt7_1_1_String, cmt7_1_2_String, cmt7_1_3_String, cmt8_1_2_unitReview_String, cmt7_1_4_String,
            cmt7_2_1_String, cmt7_2_2_String, cmt7_2_3_String, cmt8_2_2_unitReview_String, cmt7_2_4_String,
            cmt7_3_1_String, cmt7_3_2_String, cmt7_3_3_String, cmt8_3_2_unitReview_String, cmt7_3_4_String,
            cmt8_1_1_String, cmt8_1_2_String, cmt8_1_3_String, cmt8_1_4_String, cmt8_1_5_String, cmt8_1_6_String, cmt8_1_7_String, cmt8_1_8_String,
            cmt8_2_1_String, cmt8_2_2_String, cmt8_2_3_String, cmt8_2_4_String, cmt8_2_5_String, cmt8_2_6_String, cmt8_2_7_String, cmt8_2_8_String,
            cmt8_3_1_String, cmt8_3_2_String, cmt8_3_3_String, cmt8_3_4_String, cmt8_3_5_String, cmt8_3_6_String, cmt8_3_7_String, cmt8_3_8_String,
            cmt9_1_1_String, cmt9_1_2_String, cmt9_1_3_String,
            cmt9_2_1_String, cmt9_2_2_String, cmt9_2_3_String,
            cmt9_3_1_String, cmt9_3_2_String, cmt9_3_3_String,
            cmt_tv_10_4_String, cmt_tv_10_5_String, cmt_10_6_String,
    //
    tv_10_2_4_String, tv_10_2_5_String, tv_10_2_6_String, tv_10_3_4_String, tv_10_3_5_String, tv_10_3_6_String, tv_10_4_6_String, tv_10_4_5_String,
            tv_10_4_4_String, tv_10_5_6_String, tv_10_5_5_String, tv_10_5_4_String, check_6_comment, check_5_comment, check_4_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwm);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

//        Intent intent = getIntent();
//        id = intent.getIntExtra("id", 0);
//        username = intent.getStringExtra("username");
//        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        String DateToStr = (String) DateFormat.format(
                "dd/MM/yyyy", calendar.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(DateToStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(myDate);
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate = cal1.getTime();
        cal1.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate2 = cal1.getTime();
        String reportDate = dateFormat.format(previousDate);
        String reportDate2 = dateFormat.format(previousDate2);


        Calendar c_calendar = Calendar.getInstance();
        CDateToStr = (String) DateFormat.format(
                "dd/MM/yyyy", c_calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date md = null;
        try {
            md = df.parse(CDateToStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(md);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date pDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date pDate2 = cal.getTime();
        date2 = df.format(pDate);
        date1 = df.format(pDate2);

//        Toast.makeText(getApplicationContext(), CDateToStr, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), date2, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), date1, Toast.LENGTH_SHORT).show();

        d_y_submit_btn = (TextView) findViewById(R.id.d_y_submit_btn);
        yes_submit_btn = (TextView) findViewById(R.id.yes_submit_btn);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        icon = (TextView) findViewById(R.id.icon);
        icon.setText(DateToStr);
        three_tv = (TextView) findViewById(R.id.three_tv);
        three_tv.setText(reportDate);
        tv_total_hour = (TextView) findViewById(R.id.tv_total_hour);
        d_y_tv_total_hour = (TextView) findViewById(R.id.d_y_tv_total_hour);
        yes_tv_total_hour = (TextView) findViewById(R.id.yes_tv_total_hour);
        user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(myPref.getString("username", "username"));
//        one_tv=(TextView)findViewById(R.id.one_tv);
        sign_out = (TextView) findViewById(R.id.sign_out);
        if (Utility.isOnline(getApplicationContext())) {
            getCurrentTimeSheetTask = new Get_Current_TimeSheetTask();
            getCurrentTimeSheetTask.execute();
        } else Utility.showToast(getApplicationContext(), "Network failed. Please try later.");

        if (Utility.isOnline(getApplicationContext())) {
            getyesterdayTimeSheetTask = new GetyesterdayTimeSheetTask();
            getyesterdayTimeSheetTask.execute();
        } else
            Utility.showToast(getApplicationContext(), "Network failed. Please try later.");

        if (Utility.isOnline(getApplicationContext())) {
            getdYTimeSheetTask = new Getd_y_TimeSheetTask();
            getdYTimeSheetTask.execute();
        } else Utility.showToast(getApplicationContext(), "Network failed. Please try later.");

        two_tv = (TextView) findViewById(R.id.two_tv);
        two_tv.setText(reportDate2);
//        tv_p=(TextView)findViewById(R.id.tv_p);
//        tv_n=(TextView)findViewById(R.id.tv_n);


        tv_1_1 = (EditText) findViewById(R.id.tv_1_1);
        tv_1_2 = (EditText) findViewById(R.id.tv_1_2);
        tv_1_3 = (EditText) findViewById(R.id.tv_1_3);
        tv_1_3_a = (EditText) findViewById(R.id.tv_1_3a);
        tv_2_1_1b = (EditText) findViewById(R.id.tv_2_1_1b);
        tv_1_4 = (EditText) findViewById(R.id.tv_1_4);
        tv_1_14 = (EditText) findViewById(R.id.tv_1_14);
        tv_1_1aE = (EditText) findViewById(R.id.tv_1_1aE);
        tv_1_5a = (EditText) findViewById(R.id.tv_1_5a);
        tv_1_1_viewa = (View) findViewById(R.id.tv_1_1_viewa);
        tv_1_5_viewa = (View) findViewById(R.id.tv_1_5_viewa);
        tv_1_c_viewa = (View) findViewById(R.id.tv_1_c_viewa);
        tv_1_1b = (TextView) findViewById(R.id.tv_1_1b);

        tv_1_5 = (EditText) findViewById(R.id.tv_1_5);
        tv_1_6 = (EditText) findViewById(R.id.tv_1_6);
        tv_1_6a = (EditText) findViewById(R.id.tv_1_6a);
        tv_2_1bE = (EditText) findViewById(R.id.tv_2_1bE);
        tv_2_1b5 = (EditText) findViewById(R.id.tv_2_1b5);
        tv_2_3b = (EditText) findViewById(R.id.tv_2_3b);
        tv_2_1_1c = (EditText) findViewById(R.id.tv_2_1_1c);
        tv_2_1c4 = (EditText) findViewById(R.id.tv_2_1c4);
        tv_2_1cE = (EditText) findViewById(R.id.tv_2_1cE);
        tv_2_1c5 = (EditText) findViewById(R.id.tv_2_1c5);
        tv_2_3c = (EditText) findViewById(R.id.tv_2_3c);
        tv_2_6c = (EditText) findViewById(R.id.tv_2_6c);


        tv_1_1_view = (View) findViewById(R.id.tv_1_1_view);
        tv_1_2_view = (View) findViewById(R.id.tv_1_2_view);
        tv_1_c_view = (View) findViewById(R.id.tv_1_c_view);
        tv_1_a = (TextView) findViewById(R.id.tv_1_a);
        tv_1_b = (TextView) findViewById(R.id.tv_1_b);
        tv_1_4_view = (View) findViewById(R.id.tv_1_4_view);
        tv_1_5_view = (View) findViewById(R.id.tv_1_5_view);

        tv_2_1 = (EditText) findViewById(R.id.tv_2_1);
        tv_2_1_view = (View) findViewById(R.id.tv_2_1_view);
        tv_2_2 = (EditText) findViewById(R.id.tv_2_2);
        tv_2_2_view = (View) findViewById(R.id.tv_2_2_view);
        tv_2_c_view = (View) findViewById(R.id.tv_2_c_view);
        tv_2_3 = (EditText) findViewById(R.id.tv_2_3);
        tv_2_6 = (EditText) findViewById(R.id.tv_2_6);
        tv_2_a = (TextView) findViewById(R.id.tv_2_a);
        tv_2_b = (TextView) findViewById(R.id.tv_2_b);
        tv_2_5 = (EditText) findViewById(R.id.tv_2_5);
        tv_2_5_view = (View) findViewById(R.id.tv_2_5_view);
        tv_2_4 = (EditText) findViewById(R.id.tv_2_4);
        tv_2_4_view = (View) findViewById(R.id.tv_2_4_view);
        tv_2_1b4 = (EditText) findViewById(R.id.tv_2_1b4);

        tv_3_1 = (EditText) findViewById(R.id.tv_3_1);
        tv_3_2 = (EditText) findViewById(R.id.tv_3_2);
        tv_3_3 = (EditText) findViewById(R.id.tv_3_3);
        tv_3_6 = (EditText) findViewById(R.id.tv_3_6);
        tv_3_5 = (EditText) findViewById(R.id.tv_3_5);
        tv_3_1_view = (View) findViewById(R.id.tv_3_1_view);
        tv_3_2_view = (View) findViewById(R.id.tv_3_2_view);
        tv_3_c_view = (View) findViewById(R.id.tv_3_c_view);
        tv_3_a = (TextView) findViewById(R.id.tv_3_a);
        tv_3_b = (TextView) findViewById(R.id.tv_3_b);
        tv_3_5_view = (View) findViewById(R.id.tv_3_5_view);
        tv_3_4 = (EditText) findViewById(R.id.tv_3_4);
        tv_3_4_view = (View) findViewById(R.id.tv_3_4_view);

        tv_4_1 = (EditText) findViewById(R.id.tv_4_1);
        tv_4_2 = (EditText) findViewById(R.id.tv_4_2);
        tv_4_3 = (EditText) findViewById(R.id.tv_4_3);
        tv_4_6 = (EditText) findViewById(R.id.tv_4_6);
        tv_4_5 = (EditText) findViewById(R.id.tv_4_5);
        tv_4_1_view = (View) findViewById(R.id.tv_4_1_view);
        tv_4_2_view = (View) findViewById(R.id.tv_4_2_view);
        tv_4_c_view = (View) findViewById(R.id.tv_4_c_view);
        tv_4_5_view = (View) findViewById(R.id.tv_4_5_view);
        tv_4_a = (TextView) findViewById(R.id.tv_4_a);
        tv_4_b = (TextView) findViewById(R.id.tv_4_b);
        tv_4_4 = (EditText) findViewById(R.id.tv_4_4);
        tv_4_4_view = (View) findViewById(R.id.tv_4_4_view);

        tv_5_1 = (EditText) findViewById(R.id.tv_5_1);
        tv_5_2 = (EditText) findViewById(R.id.tv_5_2);
        tv_5_3 = (EditText) findViewById(R.id.tv_5_3);
        tv_5_6 = (EditText) findViewById(R.id.tv_5_6);
        tv_5_5 = (EditText) findViewById(R.id.tv_5_5);
        tv_5_1_view = (View) findViewById(R.id.tv_5_1_view);
        tv_5_2_view = (View) findViewById(R.id.tv_5_2_view);
        tv_5_a = (TextView) findViewById(R.id.tv_5_a);
        tv_5_b = (TextView) findViewById(R.id.tv_5_b);
        tv_5_5_view = (View) findViewById(R.id.tv_5_5_view);
        tv_5_4 = (EditText) findViewById(R.id.tv_5_4);
        tv_5_4_view = (View) findViewById(R.id.tv_5_4_view);
        two_view = (View) findViewById(R.id.two_view);
        three_view = (View) findViewById(R.id.three_view);

        layout_6_1 = (LinearLayout) findViewById(R.id.layout_6_1);
        layout_6_1_view = (View) findViewById(R.id.layout_6_1_view);
        layout_6_2 = (LinearLayout) findViewById(R.id.layout_6_2);
        layout_6_4 = (LinearLayout) findViewById(R.id.layout_6_4);
        layout_6_6 = (LinearLayout) findViewById(R.id.layout_6_6);
        layout_6_7 = (LinearLayout) findViewById(R.id.layout_6_7);
        layout_6_5 = (LinearLayout) findViewById(R.id.layout_6_5);
        layout_6_2_view = (View) findViewById(R.id.layout_6_2_view);
        layout_6_6_view = (View) findViewById(R.id.layout_6_6_view);
        layout_6_8 = (LinearLayout) findViewById(R.id.layout_6_8);
        layout_6_8_view = (View) findViewById(R.id.layout_6_8_view);
        et_6_1_1 = (EditText) findViewById(R.id.et_6_1_1);
        et_6_1_2 = (EditText) findViewById(R.id.et_6_1_2);
        et_6_1_3 = (EditText) findViewById(R.id.et_6_1_3);
        et_6_1_4 = (EditText) findViewById(R.id.et_6_1_4);
        et_6_1_5 = (EditText) findViewById(R.id.et_6_1_5);
        et_8_1_1_best_review = findViewById(R.id.et_8_1_1_best_review);
        et_6_1_6 = (EditText) findViewById(R.id.et_6_1_6);
        cmt6_1_1 = (EditText) findViewById(R.id.cmt6_1_1);
        cmt6_1_2 = (EditText) findViewById(R.id.cmt6_1_2);
        cmt6_1_3 = (EditText) findViewById(R.id.cmt6_1_3);
        cmt6_1_4 = (EditText) findViewById(R.id.cmt6_1_4);
       // cmt6_1_5 = (EditText) findViewById(R.id.cmt6_1_5);
        cmt6_1_6 = (EditText) findViewById(R.id.cmt6_1_6);
        cmt6_2_1 = (EditText) findViewById(R.id.cmt6_2_1);
        cmt6_2_2 = (EditText) findViewById(R.id.cmt6_2_2);
        cmt6_2_3 = (EditText) findViewById(R.id.cmt6_2_3);
        cmt6_2_4 = (EditText) findViewById(R.id.cmt6_2_4);
        //cmt6_2_5 = (EditText) findViewById(R.id.cmt6_2_5);
        cmt6_2_6 = (EditText) findViewById(R.id.cmt6_2_6);
        cmt6_3_1 = (EditText) findViewById(R.id.cmt6_3_1);
        cmt6_3_2 = (EditText) findViewById(R.id.cmt6_3_2);
        cmt6_3_3 = (EditText) findViewById(R.id.cmt6_3_3);
        cmt6_3_4 = (EditText) findViewById(R.id.cmt6_3_4);
      //  cmt6_3_5 = (EditText) findViewById(R.id.cmt6_3_5);
        cmt8_3_1_bestReview = (EditText) findViewById(R.id.cmt8_3_1_bestReview);
        cmt6_3_6 = (EditText) findViewById(R.id.cmt6_3_6);

        et_6_2_1 = (EditText) findViewById(R.id.et_6_2_1);
        et_6_2_2 = (EditText) findViewById(R.id.et_6_2_2);
        et_6_2_3 = (EditText) findViewById(R.id.et_6_2_3);
        et_6_2_4 = (EditText) findViewById(R.id.et_6_2_4);
        et_6_2_5 = (EditText) findViewById(R.id.et_6_2_5);
        et_8_2_1_bestReview = (EditText) findViewById(R.id.et_8_2_1_bestReview);
        et_6_2_6 = (EditText) findViewById(R.id.et_6_2_6);

        et_6_3_1 = (EditText) findViewById(R.id.et_6_3_1);
        et_6_3_2 = (EditText) findViewById(R.id.et_6_3_2);
        et_6_3_3 = (EditText) findViewById(R.id.et_6_3_3);
        et_6_3_4 = (EditText) findViewById(R.id.et_6_3_4);
        et_6_3_5 = (EditText) findViewById(R.id.et_6_3_5);
        et_8_3_1_bestReview = (EditText) findViewById(R.id.et_8_3_1_bestReview);
        cmt8_2_1_bestReview = (EditText) findViewById(R.id.cmt8_2_1_bestReview);
        et_6_3_6 = (EditText) findViewById(R.id.et_6_3_6);

        tv_1_1_1a = (EditText) findViewById(R.id.tv_1_1_1a);


        layout_7_1 = (LinearLayout) findViewById(R.id.layout_7_1);
        layout_7_1_view = (View) findViewById(R.id.layout_7_1_view);
        layout_7_2 = (LinearLayout) findViewById(R.id.layout_7_2);
        layout_7_4 = (LinearLayout) findViewById(R.id.layout_7_4);
        layout_7_5 = (LinearLayout) findViewById(R.id.layout_7_5);
        layout_7_6 = (LinearLayout) findViewById(R.id.layout_7_6);
        layout_7_7 = (LinearLayout) findViewById(R.id.layout_7_7);
        layout_7_2_view = (View) findViewById(R.id.layout_7_2_view);
        layout_7_6_view = (View) findViewById(R.id.layout_7_6_view);
        layout_7_8 = (LinearLayout) findViewById(R.id.layout_7_8);
        layout_7_8_view = (View) findViewById(R.id.layout_7_8_view);
        et_7_1_1 = (EditText) findViewById(R.id.et_7_1_1);
        et_7_1_2 = (EditText) findViewById(R.id.et_7_1_2);
        et_7_1_3 = (EditText) findViewById(R.id.et_7_1_3);
        et_8_1_2_unitReview = (EditText) findViewById(R.id.et_8_1_2_unitReview);
        et_7_1_4 = (EditText) findViewById(R.id.et_7_1_4);

        et_7_2_1 = (EditText) findViewById(R.id.et_7_2_1);
        et_7_2_2 = (EditText) findViewById(R.id.et_7_2_2);
    //    et_7_2_3 = (EditText) findViewById(R.id.et_7_2_3);
        et_8_2_2_unitReview = (EditText) findViewById(R.id.et_8_2_2_unitReview);
        et_7_2_4 = (EditText) findViewById(R.id.et_7_2_4);

        et_7_3_1 = (EditText) findViewById(R.id.et_7_3_1);
        et_7_3_2 = (EditText) findViewById(R.id.et_7_3_2);
        et_7_3_3 = (EditText) findViewById(R.id.et_7_3_3);
        et_8_3_2_unitReview = (EditText) findViewById(R.id.et_8_3_2_unitReview);
        et_7_3_4 = (EditText) findViewById(R.id.et_7_3_4);
        cmt7_1_1 = (EditText) findViewById(R.id.cmt7_1_1);
        cmt7_1_2 = (EditText) findViewById(R.id.cmt7_1_2);
      //  cmt7_1_3 = (EditText) findViewById(R.id.cmt7_1_3);
        cmt8_1_2_unitReview = findViewById(R.id.cmt8_1_2_unitReview);
        cmt7_1_4 = (EditText) findViewById(R.id.cmt7_1_4);
        cmt7_2_1 = (EditText) findViewById(R.id.cmt7_2_1);
        cmt7_2_2 = (EditText) findViewById(R.id.cmt7_2_2);
        //cmt7_2_3 = (EditText) findViewById(R.id.cmt7_2_3);
        cmt8_2_2_unitReview = (EditText) findViewById(R.id.cmt8_2_2_unitReview);
        cmt7_2_4 = (EditText) findViewById(R.id.cmt7_2_4);
        cmt7_3_1 = (EditText) findViewById(R.id.cmt7_3_1);
        cmt7_3_2 = (EditText) findViewById(R.id.cmt7_3_2);
        cmt7_3_3 = (EditText) findViewById(R.id.cmt7_3_3);
        cmt8_3_2_unitReview = (EditText) findViewById(R.id.cmt8_3_2_unitReview);
        cmt7_3_4 = (EditText) findViewById(R.id.cmt7_3_4);

        layout_8_1 = (LinearLayout) findViewById(R.id.layout_8_1);
        layout_8_1_view = (View) findViewById(R.id.layout_8_1_view);
        layout_8_2 = (LinearLayout) findViewById(R.id.layout_8_2);
        layout_8_4 = (LinearLayout) findViewById(R.id.layout_8_4);
        layout_8_2_view = (View) findViewById(R.id.layout_8_2_view);
        layout_8_5 = (LinearLayout) findViewById(R.id.layout_8_5);
        layout_8_6 = (LinearLayout) findViewById(R.id.layout_8_6);
        layout_8_7 = (LinearLayout) findViewById(R.id.layout_8_7);
        layout_8_6_view = (View) findViewById(R.id.layout_8_6_view);
        layout_8_8 = (LinearLayout) findViewById(R.id.layout_8_8);
        layout_8_8_view = (View) findViewById(R.id.layout_8_8_view);

        et_8_1_1 = (EditText) findViewById(R.id.et_8_1_1);
        et_8_1_2 = (EditText) findViewById(R.id.et_8_1_2);
        et_8_1_3 = (EditText) findViewById(R.id.et_8_1_3);
        et_8_1_4 = (EditText) findViewById(R.id.et_8_1_4);
        et_8_1_5 = (EditText) findViewById(R.id.et_8_1_5);
        et_8_1_6 = (EditText) findViewById(R.id.et_8_1_6);
        et_8_1_7 = (EditText) findViewById(R.id.et_8_1_7);
        et_8_1_8 = (EditText) findViewById(R.id.et_8_1_8);

        et_8_2_1 = (EditText) findViewById(R.id.et_8_2_1);
        et_8_2_2 = (EditText) findViewById(R.id.et_8_2_2);
        et_8_2_3 = (EditText) findViewById(R.id.et_8_2_3);
        et_8_2_4 = (EditText) findViewById(R.id.et_8_2_4);
        et_8_2_5 = (EditText) findViewById(R.id.et_8_2_5);
        et_8_2_6 = (EditText) findViewById(R.id.et_8_2_6);
        et_8_2_7 = (EditText) findViewById(R.id.et_8_2_7);
        et_8_2_8 = (EditText) findViewById(R.id.et_8_2_8);

        et_8_3_1 = (EditText) findViewById(R.id.et_8_3_1);
        et_8_3_2 = (EditText) findViewById(R.id.et_8_3_2);
        et_8_3_3 = (EditText) findViewById(R.id.et_8_3_3);
        et_8_3_4 = (EditText) findViewById(R.id.et_8_3_4);
        et_8_3_5 = (EditText) findViewById(R.id.et_8_3_5);
        et_8_3_6 = (EditText) findViewById(R.id.et_8_3_6);
        et_8_3_7 = (EditText) findViewById(R.id.et_8_3_7);
        et_8_3_8 = (EditText) findViewById(R.id.et_8_3_8);
        cmt8_1_1 = (EditText) findViewById(R.id.cmt8_1_1);
        cmt8_1_2 = (EditText) findViewById(R.id.cmt8_1_2);
        cmt8_1_3 = (EditText) findViewById(R.id.cmt8_1_3);
        cmt8_1_4 = (EditText) findViewById(R.id.cmt8_1_4);
        cmt8_1_5 = (EditText) findViewById(R.id.cmt8_1_5);
        cmt8_1_6 = (EditText) findViewById(R.id.cmt8_1_6);
        cmt8_1_7 = (EditText) findViewById(R.id.cmt8_1_7);
        cmt8_1_8 = (EditText) findViewById(R.id.cmt8_1_8);
        cmt8_2_1 = (EditText) findViewById(R.id.cmt8_2_1);
        cmt8_2_2 = (EditText) findViewById(R.id.cmt8_2_2);
        cmt8_2_3 = (EditText) findViewById(R.id.cmt8_2_3);
        cmt8_2_4 = (EditText) findViewById(R.id.cmt8_2_4);
        cmt8_2_5 = (EditText) findViewById(R.id.cmt8_2_5);
        cmt8_2_6 = (EditText) findViewById(R.id.cmt8_2_6);
        cmt8_2_7 = (EditText) findViewById(R.id.cmt8_2_7);
        cmt8_2_8 = (EditText) findViewById(R.id.cmt8_2_8);
        cmt8_3_1 = (EditText) findViewById(R.id.cmt8_3_1);
        cmt8_3_2 = (EditText) findViewById(R.id.cmt8_3_2);
        cmt8_3_3 = (EditText) findViewById(R.id.cmt8_3_3);
        cmt8_3_4 = (EditText) findViewById(R.id.cmt8_3_4);
        cmt8_3_5 = (EditText) findViewById(R.id.cmt8_3_5);
        cmt8_3_6 = (EditText) findViewById(R.id.cmt8_3_6);
        cmt8_3_7 = (EditText) findViewById(R.id.cmt8_3_7);
        cmt8_3_8 = (EditText) findViewById(R.id.cmt8_3_8);


        layout_9_1 = (LinearLayout) findViewById(R.id.layout_9_1);
        layout_9_1_view = (View) findViewById(R.id.layout_9_1_view);
        layout_9_2 = (LinearLayout) findViewById(R.id.layout_9_2);
        layout_9_4 = (LinearLayout) findViewById(R.id.layout_9_4);
        layout_9_2_view = (View) findViewById(R.id.layout_9_2_view);
        layout_9_6_view = (View) findViewById(R.id.layout_9_6_view);
        layout_9_5 = (LinearLayout) findViewById(R.id.layout_9_5);
        layout_9_6 = (LinearLayout) findViewById(R.id.layout_9_6);
        layout_9_7 = (LinearLayout) findViewById(R.id.layout_9_7);
        layout_9_8 = (LinearLayout) findViewById(R.id.layout_9_8);
        layout_9_8_view = (View) findViewById(R.id.layout_9_8_view);

        et_9_1_1 = (EditText) findViewById(R.id.et_9_1_1);
        et_9_1_2 = (EditText) findViewById(R.id.et_9_1_2);
        et_9_1_3 = (EditText) findViewById(R.id.et_9_1_3);

        et_9_2_1 = (EditText) findViewById(R.id.et_9_2_1);
        et_9_2_2 = (EditText) findViewById(R.id.et_9_2_2);
        et_9_2_3 = (EditText) findViewById(R.id.et_9_2_3);

        et_9_3_1 = (EditText) findViewById(R.id.et_9_3_1);
        et_9_3_2 = (EditText) findViewById(R.id.et_9_3_2);
        et_9_3_3 = (EditText) findViewById(R.id.et_9_3_3);
        cmt9_1_1 = (EditText) findViewById(R.id.cmt9_1_1);
        cmt9_1_2 = (EditText) findViewById(R.id.cmt9_1_2);
        cmt9_1_3 = (EditText) findViewById(R.id.cmt9_1_3);
        cmt9_2_1 = (EditText) findViewById(R.id.cmt9_2_1);
        cmt9_2_2 = (EditText) findViewById(R.id.cmt9_2_2);
        cmt9_2_3 = (EditText) findViewById(R.id.cmt9_2_3);
        cmt9_3_1 = (EditText) findViewById(R.id.cmt9_3_1);
        cmt9_3_2 = (EditText) findViewById(R.id.cmt9_3_2);
        cmt9_3_3 = (EditText) findViewById(R.id.cmt9_3_3);

        tv_10_1 = (EditText) findViewById(R.id.tv_10_1);
        tv_10_1_view = (View) findViewById(R.id.tv_10_1_view);
        tv_10_2 = (EditText) findViewById(R.id.tv_10_2);
        tv_10_2_view = (View) findViewById(R.id.tv_10_2_view);
        tv_10_c_view = (View) findViewById(R.id.tv_10_c_view);
        tv_10_3 = (EditText) findViewById(R.id.tv_10_3);
        tv_10_6 = (EditText) findViewById(R.id.tv_10_6);
        tv_10_a = (TextView) findViewById(R.id.tv_10_a);
        tv_10_b = (TextView) findViewById(R.id.tv_10_b);
        tv_10_5 = (EditText) findViewById(R.id.tv_10_5);
        tv_10_5_view = (View) findViewById(R.id.tv_10_5_view);
        tv_10_4 = (EditText) findViewById(R.id.tv_10_4);
        tv_10_4_view = (View) findViewById(R.id.tv_10_4_view);


        //Gourav Row 10 Other activity Edit Text
        tv_10_2_1 = (EditText) findViewById(R.id.tv_10_2_1);
        tv_10_2_2 = (EditText) findViewById(R.id.tv_10_2_2);
        tv_10_2_3 = (EditText) findViewById(R.id.tv_10_2_3);
        tv_10_2_6 = (EditText) findViewById(R.id.tv_10_2_6);
        tv_10_2_5 = (EditText) findViewById(R.id.tv_10_2_5);
        tv_10_2_4 = (EditText) findViewById(R.id.tv_10_2_4);
        tv_10_3_1 = (EditText) findViewById(R.id.tv_10_3_1);
        tv_10_3_2 = (EditText) findViewById(R.id.tv_10_3_2);
        tv_10_3_3 = (EditText) findViewById(R.id.tv_10_3_3);
        tv_10_3_6 = (EditText) findViewById(R.id.tv_10_3_6);
        tv_10_3_5 = (EditText) findViewById(R.id.tv_10_3_5);
        tv_10_3_4 = (EditText) findViewById(R.id.tv_10_3_4);

        tv_10_4_1 = (EditText) findViewById(R.id.tv_10_4_1);
        tv_10_4_2 = (EditText) findViewById(R.id.tv_10_4_2);
        tv_10_4_3 = (EditText) findViewById(R.id.tv_10_4_3);
        tv_10_4_6 = (EditText) findViewById(R.id.tv_10_4_6);
        tv_10_4_5 = (EditText) findViewById(R.id.tv_10_4_5);
        tv_10_4_4 = (EditText) findViewById(R.id.tv_10_4_4);
        tv_10_5_1 = (EditText) findViewById(R.id.tv_10_5_1);
        tv_10_5_2 = (EditText) findViewById(R.id.tv_10_5_2);
        tv_10_5_3 = (EditText) findViewById(R.id.tv_10_5_3);
        tv_10_5_6 = (EditText) findViewById(R.id.tv_10_5_6);
        tv_10_5_5 = (EditText) findViewById(R.id.tv_10_5_5);
        tv_10_5_4 = (EditText) findViewById(R.id.tv_10_5_4);
        tv_2_6b = (EditText) findViewById(R.id.tv_2_6b);

        tv_check_1 = (EditText) findViewById(R.id.tv_check_1);
        tv_check_2 = (EditText) findViewById(R.id.tv_check_2);
        tv_check_3 = (EditText) findViewById(R.id.tv_check_3);
        tv_check_4 = (EditText) findViewById(R.id.tv_check_4);
        tv_check_5 = (EditText) findViewById(R.id.tv_check_5);
        tv_check_6 = (EditText) findViewById(R.id.tv_check_6);
        cmt8_1_1_bestReview = findViewById(R.id.cmt8_1_1_bestReview);


        // textview refrence
        tv_10_2_a = (TextView) findViewById(R.id.tv_10_2_a);
        tv_10_2_b = (TextView) findViewById(R.id.tv_10_2_b);
        tv_10_2_c = (TextView) findViewById(R.id.tv_10_2_c);
        tv_10_3_a = (TextView) findViewById(R.id.tv_10_3_a);
        tv_10_3_b = (TextView) findViewById(R.id.tv_10_3_b);
        tv_10_3_c = (TextView) findViewById(R.id.tv_10_3_c);
        tv_10_4_a = (TextView) findViewById(R.id.tv_10_4_a);
        tv_10_4_b = (TextView) findViewById(R.id.tv_10_4_b);
        tv_10_4_c = (TextView) findViewById(R.id.tv_10_4_c);
        tv_10_5_a = (TextView) findViewById(R.id.tv_10_5_a);
        tv_10_5_b = (TextView) findViewById(R.id.tv_10_5_b);
        tv_10_5_c = (TextView) findViewById(R.id.tv_10_5_c);
        tv_check_a = (TextView) findViewById(R.id.tv_check_a);
        tv_check_b = (TextView) findViewById(R.id.tv_check_b);
        tv_check_c = (TextView) findViewById(R.id.tv_check_c);
        tv_1_1_1_1a = (TextView) findViewById(R.id.tv_1_1_1_1a);
        tv_2_1_1_1b = (TextView) findViewById(R.id.tv_2_1_1_1b);
        tv_2_1b = (TextView) findViewById(R.id.tv_2_1b);
        tv_2_cb = (TextView) findViewById(R.id.tv_2_cb);
        tv_2_1_1_1c = (TextView) findViewById(R.id.tv_2_1_1_1c);
        tv_2_1c = (TextView) findViewById(R.id.tv_2_1c);
        tv_2_cc = (TextView) findViewById(R.id.tv_2_cc);
        tv_8_a1_bestReview = (TextView) findViewById(R.id.tv_8_a1_bestReview);

        // view refrence
        tv_10_2_1_view = (View) findViewById(R.id.tv_10_2_1_view);
        tv_10_2_2_view = (View) findViewById(R.id.tv_10_2_2_view);
        tv_10_3_1_view = (View) findViewById(R.id.tv_10_3_1_view);
        tv_10_3_2_view = (View) findViewById(R.id.tv_10_3_2_view);
        tv_10_4_1_view = (View) findViewById(R.id.tv_10_4_1_view);
        tv_10_4_2_view = (View) findViewById(R.id.tv_10_4_2_view);
        tv_10_5_1_view = (View) findViewById(R.id.tv_10_5_1_view);
        tv_10_5_2_view = (View) findViewById(R.id.tv_10_5_2_view);
        tv_2_c_viewb = (View) findViewById(R.id.tv_2_c_viewb);
        tv_2_1c_view = (View) findViewById(R.id.tv_2_1c_view);
        tv_2_1c_viewa = (View) findViewById(R.id.tv_2_1c_viewa);
        tv_2_1_viewc = (View) findViewById(R.id.tv_2_1_viewc);
        tv_2_c_viewc = (View) findViewById(R.id.tv_2_c_viewc);
        tv_2_c_view2c = (View) findViewById(R.id.tv_2_c_view2c);

        tv_10_2_5_view = (View) findViewById(R.id.tv_10_2_5_view);
        tv_10_2_4_view = (View) findViewById(R.id.tv_10_2_4_view);
        tv_10_3_5_view = (View) findViewById(R.id.tv_10_3_5_view);
        tv_10_3_4_view = (View) findViewById(R.id.tv_10_3_4_view);
        tv_10_4_5_view = (View) findViewById(R.id.tv_10_4_5_view);
        tv_10_4_4_view = (View) findViewById(R.id.tv_10_4_4_view);
        tv_10_5_5_view = (View) findViewById(R.id.tv_10_5_5_view);
        tv_10_5_4_view = (View) findViewById(R.id.tv_10_5_4_view);
        tv_check_1_view = (View) findViewById(R.id.tv_check_1_view);
        tv_check_4_view = (View) findViewById(R.id.tv_check_4_view);
        tv_check_2_view = (View) findViewById(R.id.tv_check_2_view);
        tv_check_5_view = (View) findViewById(R.id.tv_check_5_view);
        tv_check_c_view = (View) findViewById(R.id.tv_check_c_view);
        tv_2_1b_view = (View) findViewById(R.id.tv_2_1b_view);
        tv_2_1b_viewa = (View) findViewById(R.id.tv_2_1b_viewa);
        tv_2_1_viewb = (View) findViewById(R.id.tv_2_1_viewb);
        tv_2_5_viewb = findViewById(R.id.tv_2_5_viewb);


        d_y_tv_total_hour_view = (View) findViewById(R.id.d_y_tv_total_hour_view);
        yes_tv_total_hour_view = (View) findViewById(R.id.yes_tv_total_hour_view);
        d_y_submit_btn_view = (View) findViewById(R.id.d_y_submit_btn_view);
        yes_submit_btn_view = (View) findViewById(R.id.yes_submit_btn_view);
        tv_1_1a_view = (View) findViewById(R.id.tv_1_1a_view);
        tv_1_1a_viewa = (View) findViewById(R.id.tv_1_1a_viewa);

//        tv_1_6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//        tv_1_6.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (v.getId() == R.id.tv_1_6) {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                        case MotionEvent.ACTION_UP:
//                            v.getParent().requestDisallowInterceptTouchEvent(false);
//                            break;
//                    }
//                }
//                return false;
//            }
//        });
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = myPref.edit();
                mEditor.putBoolean("IsLogin", false);
                mEditor.putBoolean("IsLoginNew",false);

                mEditor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //visibility
                tv_10_2_2.setVisibility(View.VISIBLE);
                tv_10_2_3.setVisibility(View.VISIBLE);
                tv_10_3_2.setVisibility(View.VISIBLE);
                tv_10_3_3.setVisibility(View.VISIBLE);
                tv_10_4_2.setVisibility(View.VISIBLE);
                tv_10_4_3.setVisibility(View.VISIBLE);
                tv_10_5_2.setVisibility(View.VISIBLE);
                tv_10_5_3.setVisibility(View.VISIBLE);
                tv_10_2_1.setVisibility(View.VISIBLE);
                tv_10_3_1.setVisibility(View.VISIBLE);
                tv_10_4_1.setVisibility(View.VISIBLE);
                tv_10_5_1.setVisibility(View.VISIBLE);
                tv_10_5_3.setVisibility(View.VISIBLE);
                tv_10_2_1.setVisibility(View.VISIBLE);
                tv_10_3_1.setVisibility(View.VISIBLE);
                tv_10_4_1.setVisibility(View.VISIBLE);
                tv_10_5_1.setVisibility(View.VISIBLE);
                tv_2_cc.setVisibility(View.VISIBLE);
                tv_2_1cE.setVisibility(View.VISIBLE);
                tv_10_2_a.setVisibility(View.VISIBLE);
                tv_10_2_b.setVisibility(View.VISIBLE);
                tv_10_3_a.setVisibility(View.VISIBLE);
                tv_10_3_b.setVisibility(View.VISIBLE);
                tv_10_4_a.setVisibility(View.VISIBLE);
                tv_10_4_b.setVisibility(View.VISIBLE);
                tv_10_5_a.setVisibility(View.VISIBLE);
                tv_10_5_b.setVisibility(View.VISIBLE);
                //tv_2_1c_viewa.setVisibility(View.VISIBLE);
                tv_2_1_viewc.setVisibility(View.VISIBLE);
                tv_2_1c.setVisibility(View.VISIBLE);

                tv_10_2_1_view.setVisibility(View.VISIBLE);
                tv_10_2_2_view.setVisibility(View.VISIBLE);
                tv_10_3_1_view.setVisibility(View.VISIBLE);
                tv_10_3_2_view.setVisibility(View.VISIBLE);
                tv_10_4_1_view.setVisibility(View.VISIBLE);
                tv_10_4_2_view.setVisibility(View.VISIBLE);
                tv_10_5_1_view.setVisibility(View.VISIBLE);
                tv_10_5_2_view.setVisibility(View.VISIBLE);
                tv_2_1_1c.setVisibility(View.VISIBLE);
                tv_2_1_1_1c.setVisibility(View.VISIBLE);
                tv_2_1c_view.setVisibility(View.VISIBLE);
                tv_2_c_viewc.setVisibility(View.VISIBLE);
                //

                tv_1_1.setVisibility(View.VISIBLE);
                tv_1_1_view.setVisibility(View.VISIBLE);
                tv_1_1_1a.setVisibility(View.VISIBLE);
                tv_1_1_1_1a.setVisibility(View.VISIBLE);
                tv_1_1a_view.setVisibility(View.VISIBLE);
                tv_1_1aE.setVisibility(View.VISIBLE);
                tv_1_1b.setVisibility(View.VISIBLE);
                tv_1_1_viewa.setVisibility(View.VISIBLE);

                tv_1_2.setVisibility(View.VISIBLE);
                tv_1_2_view.setVisibility(View.VISIBLE);
                tv_1_a.setVisibility(View.VISIBLE);
                tv_1_b.setVisibility(View.VISIBLE);

                tv_2_1.setVisibility(View.VISIBLE);
                tv_2_1_view.setVisibility(View.VISIBLE);
                tv_2_2.setVisibility(View.VISIBLE);
                tv_2_2_view.setVisibility(View.VISIBLE);
                tv_2_a.setVisibility(View.VISIBLE);
                tv_2_b.setVisibility(View.VISIBLE);

                tv_check_1.setVisibility(View.VISIBLE);
                tv_check_1_view.setVisibility(View.VISIBLE);
                tv_check_a.setVisibility(View.VISIBLE);
                tv_check_2.setVisibility(View.VISIBLE);
                tv_check_2_view.setVisibility(View.VISIBLE);
                tv_check_b.setVisibility(View.VISIBLE);

                tv_3_1.setVisibility(View.VISIBLE);
                tv_3_1_view.setVisibility(View.VISIBLE);
                tv_3_2.setVisibility(View.VISIBLE);
                tv_3_2_view.setVisibility(View.VISIBLE);
                tv_3_a.setVisibility(View.VISIBLE);
                tv_3_b.setVisibility(View.VISIBLE);

                tv_2_1_1b.setVisibility(View.VISIBLE);
                tv_2_1_1_1b.setVisibility(View.VISIBLE);
                tv_2_1b_view.setVisibility(View.VISIBLE);
                tv_2_1bE.setVisibility(View.VISIBLE);
                tv_2_1b.setVisibility(View.VISIBLE);
                tv_2_1_viewb.setVisibility(View.VISIBLE);


                tv_4_1.setVisibility(View.VISIBLE);
                tv_4_1_view.setVisibility(View.VISIBLE);
                tv_4_2.setVisibility(View.VISIBLE);
                tv_4_2_view.setVisibility(View.VISIBLE);
                tv_4_a.setVisibility(View.VISIBLE);
                tv_4_b.setVisibility(View.VISIBLE);


                tv_5_1.setVisibility(View.VISIBLE);
                tv_5_1_view.setVisibility(View.VISIBLE);
                tv_5_2.setVisibility(View.VISIBLE);
                tv_5_2_view.setVisibility(View.VISIBLE);
                tv_5_a.setVisibility(View.VISIBLE);
                tv_5_b.setVisibility(View.VISIBLE);
                tv_2_3c.setVisibility(View.VISIBLE);


                layout_6_1.setVisibility(View.VISIBLE);
                layout_6_1_view.setVisibility(View.VISIBLE);
                layout_6_2.setVisibility(View.VISIBLE);
                layout_6_2_view.setVisibility(View.VISIBLE);
                layout_6_5.setVisibility(View.VISIBLE);
                layout_6_7.setVisibility(View.VISIBLE);

                layout_7_1.setVisibility(View.VISIBLE);
                layout_7_1_view.setVisibility(View.VISIBLE);
                layout_7_2.setVisibility(View.VISIBLE);
                layout_7_2_view.setVisibility(View.VISIBLE);
                layout_7_5.setVisibility(View.VISIBLE);
                layout_7_7.setVisibility(View.VISIBLE);

                layout_8_1.setVisibility(View.VISIBLE);
                layout_8_1_view.setVisibility(View.VISIBLE);
                layout_8_2.setVisibility(View.VISIBLE);
                layout_8_2_view.setVisibility(View.VISIBLE);
                layout_8_5.setVisibility(View.VISIBLE);
                layout_8_7.setVisibility(View.VISIBLE);

                layout_9_1.setVisibility(View.VISIBLE);
                layout_9_1_view.setVisibility(View.VISIBLE);
                layout_9_2.setVisibility(View.VISIBLE);
                layout_9_2_view.setVisibility(View.VISIBLE);
                layout_9_5.setVisibility(View.VISIBLE);
                layout_9_7.setVisibility(View.VISIBLE);

                tv_10_1.setVisibility(View.VISIBLE);
                tv_10_1_view.setVisibility(View.VISIBLE);
                tv_10_2.setVisibility(View.VISIBLE);
                tv_10_2_view.setVisibility(View.VISIBLE);
                tv_10_a.setVisibility(View.VISIBLE);
                tv_10_b.setVisibility(View.VISIBLE);

                two_tv.setVisibility(View.VISIBLE);
                two_view.setVisibility(View.VISIBLE);
                three_tv.setVisibility(View.VISIBLE);
                three_view.setVisibility(View.VISIBLE);
                d_y_submit_btn.setVisibility(View.VISIBLE);
                yes_submit_btn.setVisibility(View.VISIBLE);
                d_y_submit_btn_view.setVisibility(View.VISIBLE);
                yes_submit_btn_view.setVisibility(View.VISIBLE);

                d_y_tv_total_hour.setVisibility(View.VISIBLE);
                yes_tv_total_hour.setVisibility(View.VISIBLE);
                d_y_tv_total_hour_view.setVisibility(View.VISIBLE);
                yes_tv_total_hour_view.setVisibility(View.VISIBLE);

            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tv_1_3.getText().toString() != null) && (!tv_1_3.getText().toString().isEmpty()) && !tv_1_3.getText().toString().contains("nul")) {
                    int_tv_1_3 = Integer.parseInt(tv_1_3.getText().toString());
                } else {
                    int_tv_1_3 = 0;
                }

                if ((tv_1_3_a.getText().toString() != null) && (!tv_1_3_a.getText().toString().isEmpty()) && !tv_1_3_a.getText().toString().contains("nul")) {
                    int_tv_1_3a = Integer.parseInt(tv_1_3_a.getText().toString());
                } else {
                    int_tv_1_3a = 0;
                }

                if ((tv_2_3.getText().toString() != null) && (!tv_2_3.getText().toString().isEmpty()) && !tv_2_3.getText().toString().contains("nul")) {
                    int_tv_2_3 = Integer.parseInt(tv_2_3.getText().toString());
                } else {
                    int_tv_2_3 = 0;
                }

                if ((tv_check_3.getText().toString() != null) && (!tv_check_3.getText().toString().isEmpty()) && !tv_check_3.getText().toString().contains("nul")) {
                    int_check_1_3 = Integer.parseInt(tv_check_3.getText().toString());
                } else {
                    int_check_1_3 = 0;
                }

                if ((tv_3_3.getText().toString() != null) && (!tv_3_3.getText().toString().isEmpty()) && !tv_3_3.getText().toString().contains("nul")) {
                    int_tv_3_3 = Integer.parseInt(tv_3_3.getText().toString());
                } else {
                    int_tv_3_3 = 0;
                }

                if ((tv_2_3c.getText().toString() != null) && (!tv_2_3c.getText().toString().isEmpty()) && !tv_2_3c.getText().toString().contains("nul")) {
                    int_tv_2_3c = Integer.parseInt(tv_2_3c.getText().toString());
                } else {
                    int_tv_2_3c = 0;
                }


                if ((tv_4_3.getText().toString() != null) && (!tv_4_3.getText().toString().isEmpty()) && !tv_4_3.getText().toString().contains("nul")) {
                    int_tv_4_3 = Integer.parseInt(tv_4_3.getText().toString());
                } else {
                    int_tv_4_3 = 0;
                }

                if ((tv_2_3b.getText().toString() != null) && (!tv_2_3b.getText().toString().isEmpty()) && !tv_2_3b.getText().toString().contains("nul")) {
                    int_tv_2_3b = Integer.parseInt(tv_2_3b.getText().toString());
                } else {
                    int_tv_2_3b = 0;
                }

                if ((tv_5_3.getText().toString() != null) && (!tv_5_3.getText().toString().isEmpty()) && !tv_5_3.getText().toString().contains("nul")) {
                    int_tv_5_3 = Integer.parseInt(tv_5_3.getText().toString());
                } else {
                    int_tv_5_3 = 0;
                }
                if ((et_6_3_1.getText().toString() != null) && (!et_6_3_1.getText().toString().isEmpty()) && !et_6_3_1.getText().toString().contains("nul")) {
                    int_et_6_3_1 = Integer.parseInt(et_6_3_1.getText().toString());
                } else {
                    int_et_6_3_1 = 0;
                }
                if ((et_6_3_2.getText().toString() != null) && (!et_6_3_2.getText().toString().isEmpty()) && !et_6_3_2.getText().toString().contains("nul")) {
                    int_et_6_3_2 = Integer.parseInt(et_6_3_2.getText().toString());
                } else {
                    int_et_6_3_2 = 0;
                }
                if ((et_6_3_3.getText().toString() != null) && (!et_6_3_3.getText().toString().isEmpty()) && !et_6_3_3.getText().toString().contains("nul")) {
                    int_et_6_3_3 = Integer.parseInt(et_6_3_3.getText().toString());
                } else {
                    int_et_6_3_3 = 0;
                }
                if ((et_6_3_4.getText().toString() != null) && (!et_6_3_4.getText().toString().isEmpty()) && !et_6_3_4.getText().toString().contains("nul")) {
                    int_et_6_3_4 = Integer.parseInt(et_6_3_4.getText().toString());
                } else {
                    int_et_6_3_4 = 0;
                }
                if ((et_6_3_5.getText().toString() != null) && (!et_6_3_5.getText().toString().isEmpty()) && !et_6_3_5.getText().toString().contains("nul")) {
                    int_et_6_3_5 = Integer.parseInt(et_6_3_5.getText().toString());
                } else {
                    int_et_6_3_5 = 0;
                }
                if ((et_8_3_1_bestReview.getText().toString() != null) && (!et_8_3_1_bestReview.getText().toString().isEmpty()) && !et_8_3_1_bestReview.getText().toString().contains("nul")) {
                    int_et_8_3_1_bestReview = Integer.parseInt(et_8_3_1_bestReview.getText().toString());
                } else {
                    int_et_8_3_1_bestReview = 0;
                }


                if ((et_6_3_6.getText().toString() != null) && (!et_6_3_6.getText().toString().isEmpty()) && !et_6_3_6.getText().toString().contains("nul")) {
                    int_et_6_3_6 = Integer.parseInt(et_6_3_6.getText().toString());
                } else {
                    int_et_6_3_6 = 0;
                }
                if ((et_7_3_1.getText().toString() != null) && (!et_7_3_1.getText().toString().isEmpty()) && !et_7_3_1.getText().toString().contains("nul")) {
                    int_et_7_3_1 = Integer.parseInt(et_7_3_1.getText().toString());
                } else {
                    int_et_7_3_1 = 0;
                }

                if ((et_7_3_2.getText().toString() != null) && (!et_7_3_2.getText().toString().isEmpty()) && !et_7_3_2.getText().toString().contains("nul")) {
                    int_et_7_3_2 = Integer.parseInt(et_7_3_2.getText().toString());
                } else {
                    int_et_7_3_2 = 0;
                }
                if ((et_7_3_3.getText().toString() != null) && (!et_7_3_3.getText().toString().isEmpty()) && !et_7_3_3.getText().toString().contains("nul")) {
                    int_et_7_3_3 = Integer.parseInt(et_7_3_3.getText().toString());
                } else {
                    int_et_7_3_3 = 0;
                }

                if ((et_8_3_2_unitReview.getText().toString() != null) && (!et_8_3_2_unitReview.getText().toString().isEmpty()) && !et_8_3_2_unitReview.getText().toString().contains("nul")) {
                    int_et_8_3_2_unitReview = Integer.parseInt(et_8_3_2_unitReview.getText().toString());
                } else {
                    int_et_8_3_2_unitReview = 0;
                }


                if ((et_7_3_4.getText().toString() != null) && (!et_7_3_4.getText().toString().isEmpty()) && !et_7_3_4.getText().toString().contains("nul")) {
                    int_et_7_3_4 = Integer.parseInt(et_7_3_4.getText().toString());
                } else {
                    int_et_7_3_4 = 0;
                }
                if ((et_8_3_1.getText().toString() != null) && (!et_8_3_1.getText().toString().isEmpty()) && !et_8_3_1.getText().toString().contains("nul")) {
                    int_et_8_3_1 = Integer.parseInt(et_8_3_1.getText().toString());
                } else {
                    int_et_8_3_1 = 0;
                }
                if ((et_8_3_2.getText().toString() != null) && (!et_8_3_2.getText().toString().isEmpty()) && !et_8_3_2.getText().toString().contains("nul")) {
                    int_et_8_3_2 = Integer.parseInt(et_8_3_2.getText().toString());
                } else {
                    int_et_8_3_2 = 0;
                }
                if ((et_8_3_3.getText().toString() != null) && (!et_8_3_3.getText().toString().isEmpty()) && !et_8_3_3.getText().toString().contains("nul")) {
                    int_et_8_3_3 = Integer.parseInt(et_8_3_3.getText().toString());
                } else {
                    int_et_8_3_3 = 0;
                }
                if ((et_8_3_4.getText().toString() != null) && (!et_8_3_4.getText().toString().isEmpty()) && !et_8_3_4.getText().toString().contains("nul")) {
                    int_et_8_3_4 = Integer.parseInt(et_8_3_4.getText().toString());
                } else {
                    int_et_8_3_4 = 0;
                }
                if ((et_8_3_5.getText().toString() != null) && (!et_8_3_5.getText().toString().isEmpty()) && !et_8_3_5.getText().toString().contains("nul")) {
                    int_et_8_3_5 = Integer.parseInt(et_8_3_5.getText().toString());
                } else {
                    int_et_8_3_5 = 0;
                }
                if ((et_8_3_6.getText().toString() != null) && (!et_8_3_6.getText().toString().isEmpty()) && !et_8_3_6.getText().toString().contains("nul")) {
                    int_et_8_3_6 = Integer.parseInt(et_8_3_6.getText().toString());
                } else {
                    int_et_8_3_6 = 0;
                }
                if ((et_8_3_7.getText().toString() != null) && (!et_8_3_7.getText().toString().isEmpty()) && !et_8_3_7.getText().toString().contains("nul")) {

                    int_et_8_3_7 = Integer.parseInt(et_8_3_7.getText().toString());
                } else {
                    int_et_8_3_7 = 0;
                }
                if ((et_8_3_8.getText().toString() != null) && (!et_8_3_8.getText().toString().isEmpty()) && !et_8_3_8.getText().toString().contains("nul")) {
                    int_et_8_3_8 = Integer.parseInt(et_8_3_8.getText().toString());
                } else {
                    int_et_8_3_8 = 0;
                }
                if ((et_9_3_1.getText().toString() != null) && (!et_9_3_1.getText().toString().isEmpty()) && !et_9_3_1.getText().toString().contains("nul")) {
                    int_et_9_3_1 = Integer.parseInt(et_9_3_1.getText().toString());
                }
                if ((et_9_3_2.getText().toString() != null) && (!et_9_3_2.getText().toString().isEmpty()) && !et_9_3_2.getText().toString().contains("nul")) {
                    int_et_9_3_2 = Integer.parseInt(et_9_3_2.getText().toString());
                } else {
                    int_et_9_3_2 = 0;
                }
                if ((et_9_3_3.getText().toString() != null) && (!et_9_3_3.getText().toString().isEmpty()) && !et_9_3_3.getText().toString().contains("nul")) {
                    int_et_9_3_3 = Integer.parseInt(et_9_3_3.getText().toString());
                } else {
                    int_et_9_3_3 = 0;
                }
                if ((tv_10_3.getText().toString() != null) && (!tv_10_3.getText().toString().isEmpty()) && !tv_10_3.getText().toString().contains("nul")) {
                    int_tv_10_3 = Integer.parseInt(tv_10_3.getText().toString());
                } else {
                    int_tv_10_3 = 0;
                }
                if ((tv_10_2_3.getText().toString() != null) && (!tv_10_2_3.getText().toString().isEmpty()) && !tv_10_2_3.getText().toString().contains("nul")) {
                    int_tv_10_2_3 = Integer.parseInt(tv_10_2_3.getText().toString());
                } else {
                    int_tv_10_2_3 = 0;
                }

                if ((tv_10_3_3.getText().toString() != null) && (!tv_10_3_3.getText().toString().isEmpty()) && !tv_10_3_3.getText().toString().contains("nul")) {
                    int_tv_10_3_3 = Integer.parseInt(tv_10_3_3.getText().toString());
                } else {
                    int_tv_10_3_3 = 0;
                }
                if ((tv_10_4_3.getText().toString() != null) && (!tv_10_4_3.getText().toString().isEmpty()) && !tv_10_4_3.getText().toString().contains("nul")) {
                    int_tv_10_4_3 = Integer.parseInt(tv_10_4_3.getText().toString());
                } else {
                    int_tv_10_4_3 = 0;
                }
                if ((tv_10_5_3.getText().toString() != null) && (!tv_10_5_3.getText().toString().isEmpty()) && !tv_10_5_3.getText().toString().contains("nul")) {
                    int_tv_10_5_3 = Integer.parseInt(tv_10_5_3.getText().toString());
                } else {
                    int_tv_10_5_3 = 0;
                }


                Add = int_tv_1_3 + int_tv_1_3a + int_tv_2_3b + int_tv_2_3 + int_tv_2_3c + int_tv_3_3 + int_tv_4_3 + int_tv_5_3 +
                        int_et_6_3_1 + int_et_6_3_2 + int_et_6_3_3 + int_et_6_3_4 +
                        /*int_et_6_3_5*/int_et_8_3_1_bestReview + int_et_6_3_6 + int_et_7_3_1 + int_et_7_3_2 +
                        /*int_et_7_3_3*/int_et_8_3_2_unitReview + int_et_7_3_4 + int_et_8_3_1 + int_et_8_3_2 +
                        int_et_8_3_3 + int_et_8_3_4 + int_et_8_3_5 + int_et_8_3_6 +
                        int_et_8_3_7 + int_et_8_3_8 + int_et_9_3_1 + int_et_9_3_2 +
                        int_et_9_3_3 + int_tv_10_3 + int_tv_10_2_3 + int_tv_10_3_3 + int_tv_10_4_3 + int_tv_10_5_3 +
                        int_check_1_3;
                tv_total_hour.setText(String.valueOf(Add));
                tv_1_6_String = tv_1_6.getText().toString();
                tv_1_6a_String = tv_1_6a.getText().toString();
                tv_2_6_String = tv_2_6.getText().toString();
                tv_3_6_String = tv_3_6.getText().toString();
                tv_4_6_String = tv_4_6.getText().toString();
                tv_5_6_String = tv_5_6.getText().toString();
                tv_2_6b_String = tv_2_6b.getText().toString();
                tv_2_6c_String = tv_2_6c.getText().toString();

                cmt6_3_1_String = cmt6_3_1.getText().toString();
                cmt6_3_2_String = cmt6_3_2.getText().toString();
                cmt6_3_3_String = cmt6_3_3.getText().toString();
                cmt6_3_4_String = cmt6_3_4.getText().toString();
             //   cmt6_3_5_String = cmt6_3_5.getText().toString();
                cmt8_3_1_bestReview_String = cmt8_3_1_bestReview.getText().toString();
                cmt6_3_6_String = cmt6_3_6.getText().toString();

                cmt7_3_1_String = cmt7_3_1.getText().toString();
                cmt7_3_2_String = cmt7_3_2.getText().toString();
                cmt7_3_3_String = cmt7_3_3.getText().toString();
                cmt8_3_2_unitReview_String = cmt8_3_2_unitReview.getText().toString();
                cmt7_3_4_String = cmt7_3_4.getText().toString();

                cmt8_3_1_String = cmt8_3_1.getText().toString();
                cmt8_3_2_String = cmt8_3_2.getText().toString();
                cmt8_3_3_String = cmt8_3_3.getText().toString();
                cmt8_3_4_String = cmt8_3_4.getText().toString();
                cmt8_3_5_String = cmt8_3_5.getText().toString();
                cmt8_3_6_String = cmt8_3_6.getText().toString();
                cmt8_3_7_String = cmt8_3_7.getText().toString();
                cmt8_3_8_String = cmt8_3_8.getText().toString();

                cmt9_3_1_String = cmt9_3_1.getText().toString();
                cmt9_3_2_String = cmt9_3_2.getText().toString();
                cmt9_3_3_String = cmt9_3_3.getText().toString();

                cmt_10_6_String = tv_10_6.getText().toString();
                tv_10_2_6_String = tv_10_2_6.getText().toString();
                tv_10_3_6_String = tv_10_3_6.getText().toString();
                tv_10_4_6_String = tv_10_4_6.getText().toString();
                tv_10_5_6_String = tv_10_5_6.getText().toString();
                check_6_comment = tv_check_6.getText().toString();


                if (Utility.isOnline(getApplicationContext())) {
                    timeSheetTask = new TimeSheetTask();
                    timeSheetTask.execute();
                } else
                    Utility.showToast(getApplicationContext(), "Network failed. Please try later.");

            }
        });

        // yesterday  button
        yes_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tv_1_2.getText().toString() != null) && (!tv_1_2.getText().toString().isEmpty()) && !tv_1_2.getText().toString().contains("nul")) {
                    int_tv_1_2 = Integer.parseInt(tv_1_2.getText().toString());
                } else {
                    int_tv_1_2 = 0;
                }
                if ((tv_2_2.getText().toString() != null) && (!tv_2_2.getText().toString().isEmpty()) && !tv_2_2.getText().toString().contains("nul")) {
                    int_tv_2_2 = Integer.parseInt(tv_2_2.getText().toString());
                } else {
                    int_tv_2_2 = 0;
                }
                if ((tv_2_1bE.getText().toString() != null) && (!tv_2_1bE.getText().toString().isEmpty()) && !tv_2_1bE.getText().toString().contains("nul")) {
                    int_tv_2_1bE = Integer.parseInt(tv_2_1bE.getText().toString());
                } else {
                    int_tv_2_1bE = 0;
                }

                if ((tv_2_1cE.getText().toString() != null) && (!tv_2_1cE.getText().toString().isEmpty()) && !tv_2_1cE.getText().toString().contains("nul")) {
                    int_tv_2_1cE = Integer.parseInt(tv_2_1cE.getText().toString());
                } else {
                    int_tv_2_1cE = 0;
                }
                if ((tv_check_2.getText().toString() != null) && (!tv_check_2.getText().toString().isEmpty()) && !tv_check_2.getText().toString().contains("nul")) {
                    int_check_1_2 = Integer.parseInt(tv_check_2.getText().toString());
                } else {
                    int_check_1_2 = 0;
                }
                if ((tv_1_1aE.getText().toString() != null) && (!tv_1_1aE.getText().toString().isEmpty()) && !tv_1_1aE.getText().toString().contains("nul")) {
                    int_tv_1_1aE = Integer.parseInt(tv_1_1aE.getText().toString());
                } else {
                    int_tv_1_1aE = 0;
                }

                if ((tv_3_2.getText().toString() != null) && (!tv_3_2.getText().toString().isEmpty()) && !tv_3_2.getText().toString().contains("nul")) {
                    int_tv_3_2 = Integer.parseInt(tv_3_2.getText().toString());
                } else {
                    int_tv_3_2 = 0;
                }
                if ((tv_4_2.getText().toString() != null) && (!tv_4_2.getText().toString().isEmpty()) && !tv_4_2.getText().toString().contains("nul")) {
                    int_tv_4_2 = Integer.parseInt(tv_4_2.getText().toString());
                } else {
                    int_tv_4_2 = 0;
                }

                if ((tv_5_2.getText().toString() != null) && (!tv_5_2.getText().toString().isEmpty()) && !tv_5_2.getText().toString().contains("nul")) {
                    int_tv_5_2 = Integer.parseInt(tv_5_2.getText().toString());
                } else {
                    int_tv_5_2 = 0;
                }
                if ((et_6_2_1.getText().toString() != null) && (!et_6_2_1.getText().toString().isEmpty()) && !et_6_2_1.getText().toString().contains("nul")) {
                    int_et_6_2_1 = Integer.parseInt(et_6_2_1.getText().toString());
                } else {
                    int_et_6_2_1 = 0;
                }
                if ((et_6_2_2.getText().toString() != null) && (!et_6_2_2.getText().toString().isEmpty()) && !et_6_2_2.getText().toString().contains("nul")) {
                    int_et_6_2_2 = Integer.parseInt(et_6_2_2.getText().toString());
                } else {
                    int_et_6_2_2 = 0;
                }
                if ((et_6_2_3.getText().toString() != null) && (!et_6_2_3.getText().toString().isEmpty()) && !et_6_2_3.getText().toString().contains("nul")) {
                    int_et_6_2_3 = Integer.parseInt(et_6_2_3.getText().toString());
                } else {
                    int_et_6_2_3 = 0;
                }
                if ((et_6_2_4.getText().toString() != null) && (!et_6_2_4.getText().toString().isEmpty()) && !et_6_2_4.getText().toString().contains("nul")) {
                    int_et_6_2_4 = Integer.parseInt(et_6_2_4.getText().toString());
                } else {
                    int_et_6_2_4 = 0;
                }
                if ((et_6_2_5.getText().toString() != null) && (!et_6_2_5.getText().toString().isEmpty()) && !et_6_2_5.getText().toString().contains("nul")) {
                    int_et_6_2_5 = Integer.parseInt(et_6_2_5.getText().toString());
                } else {
                    int_et_6_2_5 = 0;
                }

                if ((et_8_2_1_bestReview.getText().toString() != null) && (!et_8_2_1_bestReview.getText().toString().isEmpty()) && !et_8_2_1_bestReview.getText().toString().contains("nul")) {
                    int_et_8_2_1_bestReview = Integer.parseInt(et_8_2_1_bestReview.getText().toString());
                } else {
                    int_et_8_2_1_bestReview = 0;
                }
                if ((et_6_2_6.getText().toString() != null) && (!et_6_2_6.getText().toString().isEmpty()) && !et_6_2_6.getText().toString().contains("nul")) {
                    int_et_6_2_6 = Integer.parseInt(et_6_2_6.getText().toString());
                } else {
                    int_et_6_2_6 = 0;
                }
                if ((et_7_2_1.getText().toString() != null) && (!et_7_2_1.getText().toString().isEmpty()) && !et_7_2_1.getText().toString().contains("nul")) {
                    int_et_7_2_1 = Integer.parseInt(et_7_2_1.getText().toString());
                } else {
                    int_et_7_2_1 = 0;
                }
                if ((et_7_2_2.getText().toString() != null) && (!et_7_2_2.getText().toString().isEmpty()) && !et_7_2_2.getText().toString().contains("nul")) {
                    int_et_7_2_2 = Integer.parseInt(et_7_2_2.getText().toString());
                } else {
                    int_et_7_2_2 = 0;
                }
              /*  if ((et_7_2_3.getText().toString() != null) && (!et_7_2_3.getText().toString().isEmpty()) && !et_7_2_3.getText().toString().contains("nul")) {
                    int_et_7_2_3 = Integer.parseInt(et_7_2_3.getText().toString());
                } else {
                    int_et_7_2_3 = 0;
                }
*/
                if ((et_8_2_2_unitReview.getText().toString() != null) && (!et_8_2_2_unitReview.getText().toString().isEmpty()) && !et_8_2_2_unitReview.getText().toString().contains("nul")) {
                    int_et_8_2_2_unitReview = Integer.parseInt(et_8_2_2_unitReview.getText().toString());
                } else {
                    int_et_8_2_2_unitReview = 0;
                }

                if ((et_7_2_4.getText().toString() != null) && (!et_7_2_4.getText().toString().isEmpty()) && !et_7_2_4.getText().toString().contains("nul")) {
                    int_et_7_2_4 = Integer.parseInt(et_7_2_4.getText().toString());
                } else {
                    int_et_7_2_4 = 0;
                }
                if ((et_8_2_1.getText().toString() != null) && (!et_8_2_1.getText().toString().isEmpty()) && !et_8_2_1.getText().toString().contains("nul")) {
                    int_et_8_2_1 = Integer.parseInt(et_8_2_1.getText().toString());
                } else {
                    int_et_8_2_1 = 0;
                }
                if ((et_8_2_2.getText().toString() != null) && (!et_8_2_2.getText().toString().isEmpty()) && !et_8_2_2.getText().toString().contains("nul")) {
                    int_et_8_2_2 = Integer.parseInt(et_8_2_2.getText().toString());
                } else {
                    int_et_8_2_2 = 0;
                }
                if ((et_8_2_3.getText().toString() != null) && (!et_8_2_3.getText().toString().isEmpty()) && !et_8_2_3.getText().toString().contains("nul")) {
                    int_et_8_2_3 = Integer.parseInt(et_8_2_3.getText().toString());
                } else {
                    int_et_8_2_3 = 0;
                }
                if ((et_8_2_4.getText().toString() != null) && (!et_8_2_4.getText().toString().isEmpty()) && !et_8_2_4.getText().toString().contains("nul")) {
                    int_et_8_2_4 = Integer.parseInt(et_8_2_4.getText().toString());
                } else {
                    int_et_8_2_4 = 0;
                }
                if ((et_8_2_5.getText().toString() != null) && (!et_8_2_5.getText().toString().isEmpty()) && !et_8_2_5.getText().toString().contains("nul")) {
                    int_et_8_2_5 = Integer.parseInt(et_8_2_5.getText().toString());
                } else {
                    int_et_8_2_5 = 0;
                }
                if ((et_8_2_6.getText().toString() != null) && (!et_8_2_6.getText().toString().isEmpty()) && !et_8_2_6.getText().toString().contains("nul")) {
                    int_et_8_2_6 = Integer.parseInt(et_8_2_6.getText().toString());
                } else {
                    int_et_8_2_6 = 0;
                }
                if ((et_8_2_7.getText().toString() != null) && (!et_8_2_7.getText().toString().isEmpty()) && !et_8_2_7.getText().toString().contains("nul")) {

                    int_et_8_2_7 = Integer.parseInt(et_8_2_7.getText().toString());
                } else {
                    int_et_8_2_7 = 0;
                }
                if ((et_8_2_8.getText().toString() != null) && (!et_8_2_8.getText().toString().isEmpty()) && !et_8_2_8.getText().toString().contains("nul")) {
                    int_et_8_2_8 = Integer.parseInt(et_8_2_8.getText().toString());
                } else {
                    int_et_8_2_8 = 0;
                }
                if ((et_9_2_1.getText().toString() != null) && (!et_9_2_1.getText().toString().isEmpty()) && !et_9_2_1.getText().toString().contains("nul")) {
                    int_et_9_2_1 = Integer.parseInt(et_9_2_1.getText().toString());
                } else {
                    int_et_9_2_1 = 0;
                }
                if ((et_9_2_2.getText().toString() != null) && (!et_9_2_2.getText().toString().isEmpty()) && !et_9_2_2.getText().toString().contains("nul")) {
                    int_et_9_2_2 = Integer.parseInt(et_9_2_2.getText().toString());
                } else {
                    int_et_9_2_2 = 0;
                }
                if ((et_9_2_3.getText().toString() != null) && (!et_9_2_3.getText().toString().isEmpty()) && !et_9_2_3.getText().toString().contains("nul")) {
                    int_et_9_2_3 = Integer.parseInt(et_9_2_3.getText().toString());
                } else {
                    int_et_9_2_3 = 0;
                }
                if ((tv_10_2.getText().toString() != null) && (!tv_10_2.getText().toString().isEmpty()) && !tv_10_2.getText().toString().contains("nul")) {
                    int_tv_10_2 = Integer.parseInt(tv_10_2.getText().toString());
                } else {
                    int_tv_10_2 = 0;
                }
                if ((tv_10_2_2.getText().toString() != null) && (!tv_10_2_2.getText().toString().isEmpty()) && !tv_10_2_2.getText().toString().contains("nul")) {
                    int_tv_10_2_2 = Integer.parseInt(tv_10_2_2.getText().toString());
                } else {
                    int_tv_10_2_2 = 0;
                }
                if ((tv_10_3_2.getText().toString() != null) && (!tv_10_3_2.getText().toString().isEmpty()) && !tv_10_3_2.getText().toString().contains("nul")) {
                    int_tv_10_3_2 = Integer.parseInt(tv_10_3_2.getText().toString());
                } else {
                    int_tv_10_3_2 = 0;
                }
                if ((tv_10_4_2.getText().toString() != null) && (!tv_10_4_2.getText().toString().isEmpty()) && !tv_10_4_2.getText().toString().contains("nul")) {
                    int_tv_10_4_2 = Integer.parseInt(tv_10_4_2.getText().toString());
                } else {
                    int_tv_10_4_2 = 0;
                }
                if ((tv_10_5_2.getText().toString() != null) && (!tv_10_5_2.getText().toString().isEmpty()) && !tv_10_5_2.getText().toString().contains("nul")) {
                    int_tv_10_5_2 = Integer.parseInt(tv_10_5_2.getText().toString());
                } else {
                    int_tv_10_5_2 = 0;
                }
                YesterdayAdd = int_tv_1_2 + int_tv_2_1bE + int_tv_2_2 + int_tv_1_1aE + int_tv_2_1cE + int_tv_3_2 + int_tv_4_2 + int_tv_5_2 +
                        int_et_6_2_1 + int_et_6_2_2 + int_et_6_2_3 + int_et_6_2_4 + /*int_et_6_2_5*/int_et_8_2_1_bestReview + int_et_6_2_6 +
                        int_et_7_2_1 + int_et_7_2_2 + /*int_et_7_2_3*/int_et_8_2_2_unitReview + int_et_7_2_4 +
                        int_et_8_2_1 + int_et_8_2_2 + int_et_8_2_3 + int_et_8_2_4 + int_et_8_2_5 + int_et_8_2_6 + int_et_8_2_7 + int_et_8_2_8 +
                        int_et_9_2_1 + int_et_9_2_2 + int_et_9_2_3 + int_tv_10_2 + int_tv_10_2_2 + int_tv_10_3_2 + int_tv_10_4_2 + int_tv_10_5_2
                        + int_check_1_2;
                tv_2_1b5_String = tv_2_1b5.getText().toString();
                tv_2_1c5_String = tv_2_1c5.getText().toString();


                tv_1_5_String = tv_1_5.getText().toString();
                tv_2_5_String = tv_2_5.getText().toString();
                tv_3_5_String = tv_3_5.getText().toString();
                tv_4_5_String = tv_4_5.getText().toString();
                tv_5_5_String = tv_5_5.getText().toString();
                cmt6_2_1_String = cmt6_2_1.getText().toString();
                cmt6_2_2_String = cmt6_2_2.getText().toString();
                cmt6_2_3_String = cmt6_2_3.getText().toString();
                cmt6_2_4_String = cmt6_2_4.getText().toString();
               // cmt6_2_5_String = cmt6_2_5.getText().toString();
                cmt8_2_1_bestReview_String = cmt8_2_1_bestReview.getText().toString();
                cmt6_2_6_String = cmt6_2_6.getText().toString();

                cmt7_2_1_String = cmt7_2_1.getText().toString();
                cmt7_2_2_String = cmt7_2_2.getText().toString();
              //  cmt7_2_3_String = cmt7_2_3.getText().toString();
                cmt8_2_2_unitReview_String = cmt8_2_2_unitReview.getText().toString();
                cmt7_2_4_String = cmt7_2_4.getText().toString();

                cmt8_2_1_String = cmt8_2_1.getText().toString();
                cmt8_2_2_String = cmt8_2_2.getText().toString();
                cmt8_2_3_String = cmt8_2_3.getText().toString();
                cmt8_2_4_String = cmt8_2_4.getText().toString();
                cmt8_2_5_String = cmt8_2_5.getText().toString();
                cmt8_2_6_String = cmt8_2_6.getText().toString();
                cmt8_2_7_String = cmt8_2_7.getText().toString();
                cmt8_2_8_String = cmt8_2_8.getText().toString();

                cmt9_2_1_String = cmt9_2_1.getText().toString();
                cmt9_2_2_String = cmt9_2_2.getText().toString();
                cmt9_2_3_String = cmt9_2_3.getText().toString();
                cmt_tv_10_5_String = tv_10_5.getText().toString();
                tv_1_5a_String = tv_1_5a.getText().toString();


                tv_10_2_5_String = tv_10_2_5.getText().toString();
                tv_10_3_5_String = tv_10_3_5.getText().toString();
                tv_10_4_5_String = tv_10_4_5.getText().toString();
                tv_10_5_5_String = tv_10_5_5.getText().toString();
                check_5_comment = tv_check_5.getText().toString();


                yes_tv_total_hour.setText(String.valueOf(YesterdayAdd));
                if (Utility.isOnline(getApplicationContext())) {
                    yesterdayTimeSheetTask = new yesterdayTimeSheetTask();
                    yesterdayTimeSheetTask.execute();
                } else
                    Utility.showToast(getApplicationContext(), "Network failed. Please try later.");
            }
        });


        d_y_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tv_1_1.getText().toString() != null) && (!tv_1_1.getText().toString().isEmpty()) && !tv_1_1.getText().toString().contains("nul")) {
                    int_tv_1_1 = Integer.parseInt(tv_1_1.getText().toString());
                } else {
                    int_tv_1_1 = 0;
                }
                if ((tv_2_1_1b.getText().toString() != null) && (!tv_2_1_1b.getText().toString().isEmpty()) && !tv_2_1_1b.getText().toString().contains("nul")) {
                    int_tv_2_1_1b = Integer.parseInt(tv_2_1_1b.getText().toString());
                } else {
                    int_tv_2_1_1b = 0;
                }

                if ((tv_2_1.getText().toString() != null) && (!tv_2_1.getText().toString().isEmpty()) && !tv_2_1.getText().toString().contains("nul")) {
                    int_tv_2_1 = Integer.parseInt(tv_2_1.getText().toString());
                } else {
                    int_tv_2_1 = 0;
                }

                if ((tv_2_1_1c.getText().toString() != null) && (!tv_2_1_1c.getText().toString().isEmpty()) && !tv_2_1_1c.getText().toString().contains("nul")) {
                    int_tv_2_1_1c = Integer.parseInt(tv_2_1_1c.getText().toString());
                } else {
                    int_tv_2_1_1c = 0;
                }
                if ((tv_1_1_1a.getText().toString() != null) && (!tv_1_1_1a.getText().toString().isEmpty()) && !tv_1_1.getText().toString().contains("nul")) {
                    int_tv_1_1_1a = Integer.parseInt(tv_1_1_1a.getText().toString());
                } else {
                    int_tv_1_1_1a = 0;
                }
                if ((tv_check_1.getText().toString() != null) && (!tv_check_1.getText().toString().isEmpty()) && !tv_check_1.getText().toString().contains("nul")) {
                    int_check_1_1 = Integer.parseInt(tv_check_1.getText().toString());
                } else {
                    int_check_1_1 = 0;
                }


                if ((tv_3_1.getText().toString() != null) && (!tv_3_1.getText().toString().isEmpty()) && !tv_3_1.getText().toString().contains("nul")) {
                    int_tv_3_1 = Integer.parseInt(tv_3_1.getText().toString());
                } else {
                    int_tv_3_1 = 0;
                }
                if ((tv_4_1.getText().toString() != null) && (!tv_4_1.getText().toString().isEmpty()) && !tv_4_1.getText().toString().contains("nul")) {
                    int_tv_4_1 = Integer.parseInt(tv_4_1.getText().toString());
                } else {
                    int_tv_4_1 = 0;
                }

                if ((tv_5_1.getText().toString() != null) && (!tv_5_1.getText().toString().isEmpty()) && !tv_5_1.getText().toString().contains("nul")) {
                    int_tv_5_1 = Integer.parseInt(tv_5_1.getText().toString());
                } else {
                    int_tv_5_1 = 0;
                }
                if ((et_6_1_1.getText().toString() != null) && (!et_6_1_1.getText().toString().isEmpty()) && !et_6_1_1.getText().toString().contains("nul")) {
                    int_et_6_1_1 = Integer.parseInt(et_6_1_1.getText().toString());
                } else {
                    int_et_6_1_1 = 0;
                }
                if ((et_6_1_2.getText().toString() != null) && (!et_6_1_2.getText().toString().isEmpty()) && !et_6_1_2.getText().toString().contains("nul")) {
                    int_et_6_1_2 = Integer.parseInt(et_6_1_2.getText().toString());
                } else {
                    int_et_6_1_2 = 0;
                }
                if ((et_6_1_3.getText().toString() != null) && (!et_6_1_3.getText().toString().isEmpty()) && !et_6_1_3.getText().toString().contains("nul")) {
                    int_et_6_1_3 = Integer.parseInt(et_6_1_3.getText().toString());
                } else {
                    int_et_6_1_3 = 0;
                }
                if ((et_6_1_4.getText().toString() != null) && (!et_6_1_4.getText().toString().isEmpty()) && !et_6_1_4.getText().toString().contains("nul")) {
                    int_et_6_1_4 = Integer.parseInt(et_6_1_4.getText().toString());
                } else {
                    int_et_6_1_4 = 0;
                }
                if ((et_6_1_5.getText().toString() != null) && (!et_6_1_5.getText().toString().isEmpty()) && !et_6_1_5.getText().toString().contains("nul")) {
                    int_et_6_1_5 = Integer.parseInt(et_6_1_5.getText().toString());
                } else {
                    int_et_6_1_5 = 0;
                }

                if ((et_8_1_1_best_review.getText().toString() != null) && (!et_8_1_1_best_review.getText().toString().isEmpty()) && !et_8_1_1_best_review.getText().toString().contains("nul")) {
                    int_et_8_1_1_best_review = Integer.parseInt(et_8_1_1_best_review.getText().toString());
                } else {
                    int_et_8_1_1_best_review = 0;
                }

                if ((et_6_1_6.getText().toString() != null) && (!et_6_1_6.getText().toString().isEmpty()) && !et_6_1_6.getText().toString().contains("nul")) {
                    int_et_6_1_6 = Integer.parseInt(et_6_1_6.getText().toString());
                } else {
                    int_et_6_1_6 = 0;
                }
                if ((et_7_1_1.getText().toString() != null) && (!et_7_1_1.getText().toString().isEmpty()) && !et_7_1_1.getText().toString().contains("nul")) {
                    int_et_7_1_1 = Integer.parseInt(et_7_1_1.getText().toString());
                } else {
                    int_et_7_1_1 = 0;
                }
                if ((et_7_1_2.getText().toString() != null) && (!et_7_1_2.getText().toString().isEmpty()) && !et_7_1_2.getText().toString().contains("nul")) {
                    int_et_7_1_2 = Integer.parseInt(et_7_1_2.getText().toString());
                } else {
                    int_et_7_1_2 = 0;
                }
                if ((et_7_1_3.getText().toString() != null) && (!et_7_1_3.getText().toString().isEmpty()) && !et_7_1_3.getText().toString().contains("nul")) {
                    int_et_7_1_3 = Integer.parseInt(et_7_1_3.getText().toString());
                } else {
                    int_et_7_1_3 = 0;
                }

                if ((et_8_1_2_unitReview.getText().toString() != null) && (!et_8_1_2_unitReview.getText().toString().isEmpty()) && !et_8_1_2_unitReview.getText().toString().contains("nul")) {
                    int_et_8_1_2_unitReview = Integer.parseInt(et_8_1_2_unitReview.getText().toString());
                } else {
                    int_et_8_1_2_unitReview = 0;
                }


                if ((et_7_1_4.getText().toString() != null) && (!et_7_1_4.getText().toString().isEmpty()) && !et_7_1_4.getText().toString().contains("nul")) {
                    int_et_7_1_4 = Integer.parseInt(et_7_1_4.getText().toString());
                } else {
                    int_et_7_1_4 = 0;
                }
                if ((et_8_1_1.getText().toString() != null) && (!et_8_1_1.getText().toString().isEmpty()) && !et_8_1_1.getText().toString().contains("nul")) {
                    int_et_8_1_1 = Integer.parseInt(et_8_1_1.getText().toString());
                } else {
                    int_et_8_1_1 = 0;
                }
                if ((et_8_1_2.getText().toString() != null) && (!et_8_1_2.getText().toString().isEmpty()) && !et_8_1_2.getText().toString().contains("nul")) {
                    int_et_8_1_2 = Integer.parseInt(et_8_1_2.getText().toString());
                } else {
                    int_et_8_1_2 = 0;
                }
                if ((et_8_1_3.getText().toString() != null) && (!et_8_1_3.getText().toString().isEmpty()) && !et_8_1_3.getText().toString().contains("nul")) {
                    int_et_8_1_3 = Integer.parseInt(et_8_1_3.getText().toString());
                } else {
                    int_et_8_1_3 = 0;
                }
                if ((et_8_1_4.getText().toString() != null) && (!et_8_1_4.getText().toString().isEmpty()) && !et_8_1_4.getText().toString().contains("nul")) {
                    int_et_8_1_4 = Integer.parseInt(et_8_1_4.getText().toString());
                } else {
                    int_et_8_1_4 = 0;
                }
                if ((et_8_1_5.getText().toString() != null) && (!et_8_1_5.getText().toString().isEmpty()) && !et_8_1_5.getText().toString().contains("nul")) {
                    int_et_8_1_5 = Integer.parseInt(et_8_1_5.getText().toString());
                } else {
                    int_et_8_1_5 = 0;
                }
                if ((et_8_1_6.getText().toString() != null) && (!et_8_1_6.getText().toString().isEmpty()) && !et_8_1_6.getText().toString().contains("nul")) {
                    int_et_8_1_6 = Integer.parseInt(et_8_1_6.getText().toString());
                } else {
                    int_et_8_1_6 = 0;
                }
                if ((et_8_1_7.getText().toString() != null) && (!et_8_1_7.getText().toString().isEmpty()) && !et_8_1_7.getText().toString().contains("nul")) {

                    int_et_8_1_7 = Integer.parseInt(et_8_1_7.getText().toString());
                } else {
                    int_et_8_1_7 = 0;
                }
                if ((et_8_1_8.getText().toString() != null) && (!et_8_1_8.getText().toString().isEmpty()) && !et_8_1_8.getText().toString().contains("nul")) {
                    int_et_8_1_8 = Integer.parseInt(et_8_1_8.getText().toString());
                } else {
                    int_et_8_1_8 = 0;
                }
                if ((et_9_1_1.getText().toString() != null) && (!et_9_1_1.getText().toString().isEmpty()) && !et_9_1_1.getText().toString().contains("nul")) {
                    int_et_9_1_1 = Integer.parseInt(et_9_1_1.getText().toString());
                } else {
                    int_et_9_1_1 = 0;
                }
                if ((et_9_1_2.getText().toString() != null) && (!et_9_1_2.getText().toString().isEmpty()) && !et_9_1_2.getText().toString().contains("nul")) {
                    int_et_9_1_2 = Integer.parseInt(et_9_1_2.getText().toString());
                } else {
                    int_et_9_1_2 = 0;
                }
                if ((et_9_1_3.getText().toString() != null) && (!et_9_1_3.getText().toString().isEmpty()) && !et_9_1_3.getText().toString().contains("nul")) {
                    int_et_9_1_3 = Integer.parseInt(et_9_1_3.getText().toString());
                } else {
                    int_et_9_1_3 = 0;
                }
                if ((tv_10_1.getText().toString() != null) && (!tv_10_1.getText().toString().isEmpty()) && !tv_10_1.getText().toString().contains("nul")) {
                    int_tv_10_1 = Integer.parseInt(tv_10_1.getText().toString());
                } else {
                    int_tv_10_1 = 0;
                }


                if ((tv_10_2_1.getText().toString() != null) && (!tv_10_2_1.getText().toString().isEmpty()) && !tv_10_2_1.getText().toString().contains("nul")) {
                    int_tv_10_2_1 = Integer.parseInt(tv_10_2_1.getText().toString());
                } else {
                    int_tv_10_2_1 = 0;
                }
                if ((tv_10_3_1.getText().toString() != null) && (!tv_10_3_1.getText().toString().isEmpty()) && !tv_10_3_1.getText().toString().contains("nul")) {
                    int_tv_10_3_1 = Integer.parseInt(tv_10_3_1.getText().toString());
                } else {
                    int_tv_10_3_1 = 0;
                }
                if ((tv_10_4_1.getText().toString() != null) && (!tv_10_4_1.getText().toString().isEmpty()) && !tv_10_4_1.getText().toString().contains("nul")) {
                    int_tv_10_4_1 = Integer.parseInt(tv_10_4_1.getText().toString());
                } else {
                    int_tv_10_4_1 = 0;
                }
                if ((tv_10_5_1.getText().toString() != null) && (!tv_10_5_1.getText().toString().isEmpty()) && !tv_10_5_1.getText().toString().contains("nul")) {
                    int_tv_10_5_1 = Integer.parseInt(tv_10_5_1.getText().toString());
                } else {
                    int_tv_10_5_1 = 0;
                }

                D_Y_Add = int_tv_1_1 + int_tv_2_1 + int_tv_1_1_1a + int_tv_2_1_1c + int_tv_2_1_1b + int_tv_3_1 + int_tv_4_1 + int_tv_5_1 +
                        int_et_6_1_1 + int_et_6_1_2 + int_et_6_1_3 + int_et_6_1_4 + /*int_et_6_1_5 +*/int_et_8_1_1_best_review + int_et_6_1_6 +
                        int_et_7_1_1 + int_et_7_1_2 + /*int_et_7_1_3*/int_et_8_1_2_unitReview + int_et_7_1_4 +
                        int_et_8_1_1 + int_et_8_1_2 + int_et_8_1_3 + int_et_8_1_4 + int_et_8_1_5 + int_et_8_1_6 + int_et_8_1_7 + int_et_8_1_8 +
                        int_et_9_1_1 + int_et_9_1_2 + int_et_9_1_3 + int_tv_10_1 + int_tv_10_2_1 + int_tv_10_3_1 + int_tv_10_4_1 + int_tv_10_5_1
                        + int_check_1_1;
                d_y_tv_total_hour.setText(String.valueOf(D_Y_Add));

                tv_1_4_String = tv_1_4.getText().toString();
                tv_2_4_String = tv_2_4.getText().toString();
                tv_3_4_String = tv_3_4.getText().toString();
                tv_4_4_String = tv_4_4.getText().toString();
                tv_5_4_String = tv_5_4.getText().toString();
                cmt6_1_1_String = cmt6_1_1.getText().toString();
                cmt6_1_2_String = cmt6_1_2.getText().toString();
                cmt6_1_3_String = cmt6_1_3.getText().toString();
                cmt6_1_4_String = cmt6_1_4.getText().toString();
            //    cmt6_1_5_String = cmt6_1_5.getText().toString();
                cmt8_1_1_bestReview_String = cmt8_1_1_bestReview.getText().toString();
                cmt6_1_6_String = cmt6_1_6.getText().toString();
                cmt7_1_1_String = cmt7_1_1.getText().toString();
                cmt7_1_2_String = cmt7_1_2.getText().toString();
             //   cmt7_1_3_String = cmt7_1_3.getText().toString();
                cmt8_1_2_unitReview_String = cmt8_1_2_unitReview.getText().toString();
                cmt7_1_4_String = cmt7_1_4.getText().toString();
                cmt8_1_1_String = cmt8_1_1.getText().toString();
                cmt8_1_2_String = cmt8_1_2.getText().toString();
                cmt8_1_3_String = cmt8_1_3.getText().toString();
                cmt8_1_4_String = cmt8_1_4.getText().toString();
                cmt8_1_5_String = cmt8_1_5.getText().toString();
                cmt8_1_6_String = cmt8_1_6.getText().toString();
                cmt8_1_7_String = cmt8_1_7.getText().toString();
                cmt8_1_8_String = cmt8_1_8.getText().toString();
                cmt9_1_1_String = cmt9_1_1.getText().toString();
                cmt9_1_2_String = cmt9_1_2.getText().toString();
                cmt9_1_3_String = cmt9_1_3.getText().toString();
                cmt_tv_10_4_String = tv_10_4.getText().toString();
                tv_1_14_String = tv_1_14.getText().toString();
                tv_2_1b4_String = tv_2_1b4.getText().toString();
                tv_2_1c4_String = tv_2_1c4.getText().toString();


                tv_10_2_4_String = tv_10_2_4.getText().toString();
                tv_10_3_4_String = tv_10_3_4.getText().toString();
                tv_10_4_4_String = tv_10_4_4.getText().toString();
                tv_10_5_4_String = tv_10_5_4.getText().toString();
                check_4_comment = tv_check_4.getText().toString();
//
//                tv_1_4_String = tv_1_4.getText().toString();
//                tv_2_4_String = tv_2_4.getText().toString();
//                tv_3_4_String = tv_3_4.getText().toString();
//                tv_4_4_String = tv_4_4.getText().toString();
//                tv_5_4_String = tv_5_4.getText().toString();


                if (Utility.isOnline(getApplicationContext())) {
                    d_y_timeSheetTask = new d_y_TimeSheetTask();
                    d_y_timeSheetTask.execute();
                } else
                    Utility.showToast(getApplicationContext(), "Network failed. Please try later.");

            }
        });

        currentChangeListener();
        yesterdayChangeListener();
        dayBeforeYesterdayAdd();


    }

    public void onClick(View view) {
        Parent_tv = (TextView) view.findViewById(view.getId());
        selectEditText();
    }

    public void selectEditText() {
        if (Parent_tv.getId() == R.id.tv_1_c) {
            tv_1_6.setVisibility((tv_1_6.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_1_ca) {
            tv_1_6a.setVisibility((tv_1_6a.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_1_b) {
            tv_1_5.setVisibility((tv_1_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_1_5_view.setVisibility((tv_1_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        }
     /*   else if(Parent_tv.getId() == R.id.tv_8_a1_bestReview){
            cmt8_1_1_bestReview.setVisibility((cmt8_1_1_bestReview.getVisibility() == View.VISIBLE)
                    ? View.GONE: View.VISIBLE);
        }*/

        else if (Parent_tv.getId() == R.id.tv_2_1_1_1b) {
            tv_2_1b4.setVisibility((tv_2_1b4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_1b_view.setVisibility((tv_2_1b_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_1_1_1_1a) {
            tv_1_14.setVisibility((tv_1_14.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_1_1a_viewa.setVisibility((tv_1_1a_viewa.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_1_1b) {
            tv_1_5a.setVisibility((tv_1_5a.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_1_5_viewa.setVisibility((tv_1_5_viewa.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_1c) {
            tv_2_1c5.setVisibility((tv_2_1c5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_c_viewc.setVisibility((tv_2_c_viewc.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_cc) {
            tv_2_6c.setVisibility((tv_2_6c.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_c_view2c.setVisibility((tv_2_c_view2c.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);

        } else if (Parent_tv.getId() == R.id.tv_2_1b) {
            tv_2_1b5.setVisibility((tv_2_1b5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_5_viewb.setVisibility((tv_2_5_viewb.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_1_1_1c) {
            tv_2_1c4.setVisibility((tv_2_1c4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_1c_viewa.setVisibility((tv_2_1c_viewa.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_cb) {
            tv_2_6b.setVisibility((tv_2_6b.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_1_a) {
            tv_1_4.setVisibility((tv_1_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_1_4_view.setVisibility((tv_1_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_check_a) {
            tv_check_4.setVisibility((tv_check_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_check_4_view.setVisibility((tv_check_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_check_b) {
            tv_check_5.setVisibility((tv_check_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_check_5_view.setVisibility((tv_check_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_check_c) {
            tv_check_6.setVisibility((tv_check_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_a) {
            tv_2_4.setVisibility((tv_2_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_4_view.setVisibility((tv_2_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_b) {
            tv_2_5.setVisibility((tv_2_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_2_5_view.setVisibility((tv_2_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_2_c) {
            tv_2_6.setVisibility((tv_2_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_3_a) {
            tv_3_4.setVisibility((tv_3_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_3_4_view.setVisibility((tv_3_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_3_b) {
            tv_3_5.setVisibility((tv_3_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_3_5_view.setVisibility((tv_3_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_3_c) {

            tv_3_6.setVisibility((tv_3_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_4_a) {

            tv_4_4.setVisibility((tv_4_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_4_4_view.setVisibility((tv_4_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_4_b) {

            tv_4_5.setVisibility((tv_4_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_4_5_view.setVisibility((tv_4_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_4_c) {

            tv_4_6.setVisibility((tv_4_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_5_a) {

            tv_5_4.setVisibility((tv_5_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_5_4_view.setVisibility((tv_5_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_5_b) {

            tv_5_5.setVisibility((tv_5_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_5_5_view.setVisibility((tv_5_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_5_c) {
            tv_5_6.setVisibility((tv_5_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_6_a1) {
            layout_6_8.setVisibility((layout_6_8.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_6_8_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_6_b1) {
            layout_6_6.setVisibility((layout_6_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_6_6_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_6_c1) {
            layout_6_4.setVisibility((layout_6_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_7_a1) {
            layout_7_8.setVisibility((layout_7_8.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_7_8_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_7_b1) {
            layout_7_6.setVisibility((layout_7_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_7_6_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_7_c1) {
            layout_7_4.setVisibility((layout_7_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_8_a1) {
            layout_8_8.setVisibility((layout_8_8.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_8_8_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_8_b1) {
            layout_8_6.setVisibility((layout_8_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_8_6_view.setVisibility(View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_8_c1) {
            layout_8_4.setVisibility((layout_8_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_9_a1) {
            layout_9_8.setVisibility((layout_9_8.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_9_8_view.setVisibility(View.VISIBLE);

        } else if (Parent_tv.getId() == R.id.tv_9_b1) {
            layout_9_6.setVisibility((layout_9_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            layout_9_6_view.setVisibility(View.VISIBLE);

        } else if (Parent_tv.getId() == R.id.tv_9_c1) {
            layout_9_4.setVisibility((layout_9_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_a) {

            tv_10_4.setVisibility((tv_10_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_4_view.setVisibility((tv_10_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_b) {

            tv_10_5.setVisibility((tv_10_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_5_view.setVisibility((tv_10_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_c) {

            tv_10_6.setVisibility((tv_10_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        }


        // Lisner for icon
        else if (Parent_tv.getId() == R.id.tv_10_2_a) {

            tv_10_2_4.setVisibility((tv_10_2_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_2_4_view.setVisibility((tv_10_2_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_2_b) {

            tv_10_2_5.setVisibility((tv_10_2_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_2_5_view.setVisibility((tv_10_2_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_2_c) {

            tv_10_2_6.setVisibility((tv_10_2_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);

        } else if (Parent_tv.getId() == R.id.tv_10_3_a) {

            tv_10_3_4.setVisibility((tv_10_3_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_3_4_view.setVisibility((tv_10_3_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_3_b) {

            tv_10_3_5.setVisibility((tv_10_3_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_3_5_view.setVisibility((tv_10_3_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_3_c) {

            tv_10_3_6.setVisibility((tv_10_3_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);

        } else if (Parent_tv.getId() == R.id.tv_10_4_a) {

            tv_10_4_4.setVisibility((tv_10_4_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_4_4_view.setVisibility((tv_10_4_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_4_b) {

            tv_10_4_5.setVisibility((tv_10_4_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_4_5_view.setVisibility((tv_10_4_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_4_c) {

            tv_10_4_6.setVisibility((tv_10_4_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_5_a) {

            tv_10_5_4.setVisibility((tv_10_5_4.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_5_4_view.setVisibility((tv_10_5_4_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_5_b) {

            tv_10_5_5.setVisibility((tv_10_5_5.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
            tv_10_5_5_view.setVisibility((tv_10_5_5_view.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        } else if (Parent_tv.getId() == R.id.tv_10_5_c) {

            tv_10_5_6.setVisibility((tv_10_5_6.getVisibility() == View.VISIBLE)
                    ? View.GONE : View.VISIBLE);
        }


    }

    public class TimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String body = "AddDailyReport?EmpCode=" + myPref.getString("Id", "Id") + "&DailyMMeeting=" + int_tv_1_3 + "&DWNGemba=" + int_tv_2_3
                    + "&QualityReview=" + int_tv_3_3 + "&CustomerVisits=" + int_tv_4_3 + "&CostingReview=" + int_tv_5_3 + "&PeopleUnit=" + int_et_6_3_1 + "&PeopleBM=" + int_et_6_3_2 + "&PeopleLLLG=" + int_et_6_3_3
                    + "&PeopleTraining=" + int_et_6_3_4 + "&PeopleBest=" + /*int_et_6_3_5*/int_et_8_3_1_bestReview + "&PeopleCDP=" + int_et_6_3_6 + "&ManagementCorp=" + int_et_7_3_1 + "&ManagementProtivity=" + int_et_7_3_2 + "&ManagementUnit=" + /*int_et_7_3_3*/int_et_8_3_2_unitReview
                    + "&ManagementMeeting=" + int_et_7_3_4 + "&RegularMMC=" + int_et_8_3_1 + "&RegularSystem=" + int_et_8_3_2 + "&RegularIndent=" + int_et_8_3_3 + "&RegularDevelopment=" + int_et_8_3_4 + "&RegularSAP=" + int_et_8_3_5
                    + "&RegularManufact=" + int_et_8_3_6 + "&RegularMPCP=" + int_et_8_3_7 + "&RegularSafety=" + int_et_8_3_8 + "&SupplierComm=" + int_et_9_3_1 + "&SupplierWorst=" + int_et_9_3_2 + "&SupplierSupplier=" + int_et_9_3_3 + "&OtherAct=" + int_tv_10_3 + "&OtherAct2=" + int_tv_10_2_3 + "&OtherAct3=" + int_tv_10_3_3 + "&OtherAct4=" + int_tv_10_4_3 + "&OtherAct5=" + int_tv_10_5_3
                    + "&ReportingDate=" + CDateToStr + "&DailyMMeetingRemark=" + tv_1_6_String + "&DWNGembaRemark=" + tv_2_6_String + "&QualityReviewRemark=" + tv_3_6_String + "&CustomerVisitsRemark=" + tv_4_6_String + "&CostingReviewRemark=" + tv_5_6_String + "&PeopleUnitRemark=" + cmt6_3_1_String
                    + "&PeopleBMRemark=" + cmt6_3_2_String + "&PeopleLLLGRemark=" + cmt6_3_3_String + "&PeopleTrainingRemark=" + cmt6_3_4_String + "&PeopleBestRemark=" + cmt8_3_1_bestReview_String + "&PeopleCDPRemark=" + cmt6_3_6_String + "&ManagementCorpRemark=" + cmt7_3_1_String + "&ManagementProtivityRemark=" + cmt7_3_2_String + "&ManagementUnitRemark=" + /*cmt7_3_3_String*/cmt8_3_2_unitReview_String
                    + "&ManagementMeetingRemark=" + cmt7_3_4_String + "&RegularMMCRemark=" + cmt8_3_1_String + "&RegularSystemRemark=" + cmt8_3_2_String + "&RegularIndentRemark=" + cmt8_3_3_String + "&RegularDevelopmentRemark=" + cmt8_3_4_String + "&RegularSAPRemark=" + cmt8_3_5_String + "&RegularManufactRemark=" + cmt8_3_6_String + "&RegularMPCPRemark=" + cmt8_3_7_String
                    + "&RegularSafetyRemark=" + cmt8_3_8_String + "&SupplierCommRemark=" + cmt9_3_1_String + "&SupplierWorstRemark=" + cmt9_3_2_String + "&SupplierSupplierRemark=" + cmt9_3_3_String + "&OtherActRemark=" + cmt_10_6_String + "&OtherActRemark2=" + tv_10_2_6_String + "&OtherActRemark3=" + tv_10_3_6_String + "&OtherActRemark4=" + tv_10_4_6_String + "&OtherActRemark5=" + tv_10_5_6_String
                    + "&CheckOnCheck=" + int_check_1_3 + "&CheckOnCheckRmk=" + check_6_comment + "&Covid=" + int_tv_1_3a + "&LPA=" + int_tv_2_3b + "&LpaCovid=" + int_tv_2_3c + "&CovidRemark=" + tv_1_6a_String + "&LpaRemark=" + tv_2_6b_String + "&CovidLpaRemark=" + tv_2_6c_String + "&CKey=" + "mda@sPr$rZ#G!!";
            response = new HttpConnection().requestGetContent(body.replace(" ", "%20"));
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(DWMActivity.this, "Data Saved successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //yesterday date
    public class yesterdayTimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String body = "AddDailyReport?EmpCode=" + myPref.getString("Id", "Id") + "&DailyMMeeting=" + int_tv_1_2 + "&DWNGemba=" + int_tv_2_2
                    + "&QualityReview=" + int_tv_3_2 + "&CustomerVisits=" + int_tv_4_2 + "&CostingReview=" + int_tv_5_2 + "&PeopleUnit=" + int_et_6_2_1 + "&PeopleBM=" + int_et_6_2_2 + "&PeopleLLLG=" + int_et_6_2_3
                    + "&PeopleTraining=" + int_et_6_2_4 + "&PeopleBest=" + /*int_et_6_2_5*/int_et_8_2_1_bestReview + "&PeopleCDP=" + int_et_6_2_6 + "&ManagementCorp=" + int_et_7_2_1 + "&ManagementProtivity=" + int_et_7_2_2 + "&ManagementUnit=" + /*int_et_7_2_3*/int_et_8_2_2_unitReview
                    + "&ManagementMeeting=" + int_et_7_2_4 + "&RegularMMC=" + int_et_8_2_1 + "&RegularSystem=" + int_et_8_2_2 + "&RegularIndent=" + int_et_8_2_3 + "&RegularDevelopment=" + int_et_8_2_4 + "&RegularSAP=" + int_et_8_2_5
                    + "&RegularManufact=" + int_et_8_2_6 + "&RegularMPCP=" + int_et_8_2_7 + "&RegularSafety=" + int_et_8_2_8 + "&SupplierComm=" + int_et_9_2_1 + "&SupplierWorst=" + int_et_9_2_2 + "&SupplierSupplier=" + int_et_9_2_3 + "&OtherAct=" + int_tv_10_2 + "&OtherAct2=" + int_tv_10_2_2 + "&OtherAct3=" + int_tv_10_3_2 + "&OtherAct4=" + int_tv_10_4_2 + "&OtherAct5=" + int_tv_10_5_2
                    + "&ReportingDate=" + date2 + "&DailyMMeetingRemark=" + tv_1_5_String + "&DWNGembaRemark=" + tv_2_5_String + "&QualityReviewRemark=" + tv_3_5_String + "&CustomerVisitsRemark=" + tv_4_5_String + "&CostingReviewRemark=" + tv_5_5_String + "&PeopleUnitRemark=" + cmt6_2_1_String
                    + "&PeopleBMRemark=" + cmt6_2_2_String + "&PeopleLLLGRemark=" + cmt6_2_3_String + "&PeopleTrainingRemark=" + cmt6_2_4_String + "&PeopleBestRemark=" + /*cmt6_2_5_String*/cmt8_2_1_bestReview_String + "&PeopleCDPRemark=" + cmt6_2_6_String + "&ManagementCorpRemark=" + cmt7_2_1_String + "&ManagementProtivityRemark=" + cmt7_2_2_String + "&ManagementUnitRemark=" + /*cmt7_2_3_String*/cmt8_2_2_unitReview_String
                    + "&ManagementMeetingRemark=" + cmt7_2_4_String + "&RegularMMCRemark=" + cmt8_2_1_String + "&RegularSystemRemark=" + cmt8_2_2_String + "&RegularIndentRemark=" + cmt8_2_3_String + "&RegularDevelopmentRemark=" + cmt8_2_4_String + "&RegularSAPRemark=" + cmt8_2_5_String + "&RegularManufactRemark=" + cmt8_2_6_String + "&RegularMPCPRemark=" + cmt8_2_7_String
                    + "&RegularSafetyRemark=" + cmt8_2_8_String + "&SupplierCommRemark=" + cmt9_2_1_String + "&SupplierWorstRemark=" + cmt9_2_2_String + "&SupplierSupplierRemark=" + cmt9_2_3_String + "&OtherActRemark=" + cmt_tv_10_5_String + "&OtherActRemark2=" + tv_10_2_5_String + "&OtherActRemark3=" + tv_10_3_5_String + "&OtherActRemark4=" + tv_10_4_5_String + "&OtherActRemark5=" + tv_10_5_5_String
                    + "&CheckOnCheck=" + int_check_1_2 + "&CheckOnCheckRmk=" + check_5_comment + "&Covid=" + int_tv_1_1aE + "&LPA=" + int_tv_2_1bE + "&LpaCovid=" + int_tv_2_1cE + "&CovidRemark=" + tv_1_5a_String + "&LpaRemark=" + tv_2_1b5_String + "&CovidLpaRemark=" + tv_2_1c5_String + "&CKey=" + "mda@sPr$rZ#G!!";

            response = new HttpConnection().requestGetContent(body.replace(" ", "%20"));
            return false;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(DWMActivity.this, "Data Saved successfully", Toast.LENGTH_SHORT).show();

        }
    }


    // Day Before Yesterday

    public class d_y_TimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String body = "AddDailyReport?EmpCode=" + myPref.getString("Id", "Id") + "&DailyMMeeting=" + int_tv_1_1 + "&DWNGemba=" + int_tv_2_1
                    + "&QualityReview=" + int_tv_3_1 + "&CustomerVisits=" + int_tv_4_1 + "&CostingReview=" + int_tv_5_1 + "&PeopleUnit=" + int_et_6_1_1 + "&PeopleBM=" + int_et_6_1_2 + "&PeopleLLLG=" + int_et_6_1_3
                    + "&PeopleTraining=" + int_et_6_1_4 + "&PeopleBest=" + /*int_et_6_1_5*/ int_et_8_1_1_best_review + "&PeopleCDP=" + int_et_6_1_6 + "&ManagementCorp=" + int_et_7_1_1 + "&ManagementProtivity=" + int_et_7_1_2 + "&ManagementUnit=" + /*int_et_7_1_3*/int_et_8_1_2_unitReview
                    + "&ManagementMeeting=" + int_et_7_1_4 + "&RegularMMC=" + int_et_8_1_1 + "&RegularSystem=" + int_et_8_1_2 + "&RegularIndent=" + int_et_8_1_3 + "&RegularDevelopment=" + int_et_8_1_4 + "&RegularSAP=" + int_et_8_1_5
                    + "&RegularManufact=" + int_et_8_1_6 + "&RegularMPCP=" + int_et_8_1_7 + "&RegularSafety=" + int_et_8_1_8 + "&SupplierComm=" + int_et_9_1_1 + "&SupplierWorst=" + int_et_9_1_2 + "&SupplierSupplier=" + int_et_9_1_3 + "&OtherAct=" + int_tv_10_1 + "&OtherAct2=" + int_tv_10_2_1 + "&OtherAct3=" + int_tv_10_3_1 + "&OtherAct4=" + int_tv_10_4_1 + "&OtherAct5=" + int_tv_10_5_1
                    + "&ReportingDate=" + date1 + "&DailyMMeetingRemark=" + tv_1_4_String + "&DWNGembaRemark=" + tv_2_4_String + "&QualityReviewRemark=" + tv_3_4_String + "&CustomerVisitsRemark=" + tv_4_4_String + "&CostingReviewRemark=" + tv_5_4_String + "&PeopleUnitRemark=" + cmt6_1_1_String
                    + "&PeopleBMRemark=" + cmt6_1_2_String + "&PeopleLLLGRemark=" + cmt6_1_3_String + "&PeopleTrainingRemark=" + cmt6_1_4_String + "&PeopleBestRemark=" + /*cmt6_1_5_String*/cmt8_1_1_bestReview_String + "&PeopleCDPRemark=" + cmt6_1_6_String + "&ManagementCorpRemark=" + cmt7_1_1_String + "&ManagementProtivityRemark=" + cmt7_1_2_String + "&ManagementUnitRemark=" + /*cmt7_1_3_String*/cmt8_1_2_unitReview_String
                    + "&ManagementMeetingRemark=" + cmt7_1_4_String + "&RegularMMCRemark=" + cmt8_1_1_String + "&RegularSystemRemark=" + cmt8_1_2_String + "&RegularIndentRemark=" + cmt8_1_3_String + "&RegularDevelopmentRemark=" + cmt8_1_4_String + "&RegularSAPRemark=" + cmt8_1_5_String + "&RegularManufactRemark=" + cmt8_1_6_String + "&RegularMPCPRemark=" + cmt8_1_7_String
                    + "&RegularSafetyRemark=" + cmt8_1_8_String + "&SupplierCommRemark=" + cmt9_1_1_String + "&SupplierWorstRemark=" + cmt9_1_2_String + "&SupplierSupplierRemark=" + cmt9_1_3_String + "&OtherActRemark=" + cmt_tv_10_4_String + "&OtherActRemark2=" + tv_10_2_4_String + "&OtherActRemark3=" + tv_10_3_4_String + "&OtherActRemark4=" + tv_10_4_4_String + "&OtherActRemark5=" + tv_10_5_4_String
                    + "&CheckOnCheck=" + int_check_1_1 + "&CheckOnCheckRmk=" + check_4_comment + "&Covid=" + int_tv_1_1_1a + "&LPA=" + int_tv_2_1_1b + "&LpaCovid=" + int_tv_2_1_1c + "&CovidRemark=" + tv_1_14_String + "&LpaRemark=" + tv_2_1b4_String + "&CovidLpaRemark=" + tv_2_1c4_String + "&CKey=" + "mda@sPr$rZ#G!!";
            response = new HttpConnection().requestGetContent(body.replace(" ", "&20"));

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(DWMActivity.this, "Data Saved successfully", Toast.LENGTH_SHORT).show();

        }
    }


    //get current sheet

    public class Get_Current_TimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            response = new HttpConnection().requestGetContent("GetDetails?userid=" + myPref.getString("Id", "Id") + "&date=" +/*"23/05/2020" */CDateToStr + "&CKey=" + "mda@sPr$rZ#G!!");
            return false;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            getyesterdayTimeSheetTask = null;
            if (response != null && response.length() > 5) {
                submit_btn.setText("Update");
                dwmData = new ParseData().getDwmData(response);
                tv_1_3.setText(String.valueOf(dwmData.get(0).getDailyMMeeting()));
                tv_2_3.setText(String.valueOf(dwmData.get(0).getdWNGemba()));
                tv_3_3.setText(String.valueOf(dwmData.get(0).getQualityReview()));
                tv_4_3.setText(String.valueOf(dwmData.get(0).getCustomerVisits()));
                tv_5_3.setText(String.valueOf(dwmData.get(0).getCostingReview()));
                et_6_3_1.setText(String.valueOf(dwmData.get(0).getPeopleUnit()));
                et_6_3_2.setText(String.valueOf(dwmData.get(0).getPeopleBM()));
                et_6_3_3.setText(String.valueOf(dwmData.get(0).getPeopleLLLG()));
                et_6_3_4.setText(String.valueOf(dwmData.get(0).getPeopleTraining()));
                et_6_3_5.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                et_8_3_1_bestReview.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                et_6_3_6.setText(String.valueOf(dwmData.get(0).getPeopleCDP()));
                et_7_3_1.setText(String.valueOf(dwmData.get(0).getManagementCorp()));
                et_7_3_2.setText(String.valueOf(dwmData.get(0).getManagementProtivity()));
                et_7_3_3.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
                et_8_3_2_unitReview.setText(String.valueOf(dwmData.get(0).getManagementMeeting()));
                et_7_3_4.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
               et_8_3_1.setText(String.valueOf(dwmData.get(0).getRegularMMC()));
                et_8_3_2.setText(String.valueOf(dwmData.get(0).getRegularSystem()));
                et_8_3_3.setText(String.valueOf(dwmData.get(0).getRegularIndent()));
                et_8_3_4.setText(String.valueOf(dwmData.get(0).getRegularDevelopment()));
                et_8_3_5.setText(String.valueOf(dwmData.get(0).getRegularSAP()));
                et_8_3_6.setText(String.valueOf(dwmData.get(0).getRegularManufact()));
                et_8_3_7.setText(String.valueOf(dwmData.get(0).getRegularMPCP()));
                et_8_3_8.setText(String.valueOf(dwmData.get(0).getRegularSafety()));
                et_9_3_1.setText(String.valueOf(dwmData.get(0).getSupplierComm()));
                et_9_3_2.setText(String.valueOf(dwmData.get(0).getSupplierWorst()));
                et_9_3_3.setText(String.valueOf(dwmData.get(0).getSupplierSupplier()));
                tv_10_3.setText(String.valueOf(dwmData.get(0).getOtherAct()));

                tv_10_2_3.setText(String.valueOf(dwmData.get(0).getOtherAct2()));
                tv_10_3_3.setText(String.valueOf(dwmData.get(0).getOtherAct3()));
                tv_10_4_3.setText(String.valueOf(dwmData.get(0).getOtherAct4()));
                tv_10_5_3.setText(String.valueOf(dwmData.get(0).getOtherAct5()));

                tv_1_6.setText(dwmData.get(0).getDailyMMeetingRemark());
                tv_2_6.setText(dwmData.get(0).getDWNGembaRemark());
                tv_3_6.setText(dwmData.get(0).getQualityReviewRemark());
                tv_4_6.setText(dwmData.get(0).getCustomerVisitsRemark());
                tv_5_6.setText(dwmData.get(0).getCostingReviewRemark());
                cmt6_3_1.setText(dwmData.get(0).getPeopleUnitRemark());
                cmt6_3_2.setText(dwmData.get(0).getPeopleBMRemark());
                cmt6_3_3.setText(dwmData.get(0).getPeopleLLLGRemark());
                cmt6_3_4.setText(dwmData.get(0).getPeopleTrainingRemark());
                //cmt6_3_5.setText(dwmData.get(0).getPeopleBestRemark());
              //  cmt8_3_1_bestReview.setText(dwmData.get(0).getPeopleBestRemark());
                cmt6_3_6.setText(dwmData.get(0).getPeopleCDPRemark());
                cmt7_3_1.setText(dwmData.get(0).getManagementCorpRemark());
                cmt7_3_2.setText(dwmData.get(0).getManagementProtivityRemark());
                cmt7_3_3.setText(dwmData.get(0).getManagementUnitRemark());
                cmt8_3_2_unitReview.setText(dwmData.get(0).getManagementMeetingRemark());
                cmt7_3_4.setText(dwmData.get(0).getManagementUnitRemark());
                cmt8_3_1.setText(dwmData.get(0).getRegularMMCRemark());
                cmt8_3_2.setText(dwmData.get(0).getRegularSystemRemark());
                cmt8_3_3.setText(dwmData.get(0).getRegularIndentRemark());
                cmt8_3_4.setText(dwmData.get(0).getRegularDevelopmentRemark());
                cmt8_3_5.setText(dwmData.get(0).getRegularSAPRemark());
                cmt8_3_6.setText(dwmData.get(0).getRegularManufactRemark());
                cmt8_3_7.setText(dwmData.get(0).getRegularMPCPRemark());
                cmt8_3_8.setText(dwmData.get(0).getRegularSafetyRemark());
                cmt9_3_1.setText(dwmData.get(0).getSupplierCommRemark());
                cmt9_3_2.setText(dwmData.get(0).getSupplierWorstRemark());
                cmt9_3_3.setText(dwmData.get(0).getSupplierSupplierRemark());
                tv_10_6.setText(dwmData.get(0).getOtherActRemark());

                tv_10_2_6.setText(dwmData.get(0).getOtherActRemark2());
                tv_10_3_6.setText(dwmData.get(0).getOtherActRemark3());
                tv_10_4_6.setText(dwmData.get(0).getOtherActRemark4());
                tv_10_5_6.setText(dwmData.get(0).getOtherActRemark5());

                tv_check_3.setText(dwmData.get(0).getCheckoncheck());
                tv_check_6.setText(dwmData.get(0).getCheckoncheckrmk());
                tv_1_3_a.setText("" + dwmData.get(0).getCovid());
                tv_1_6a.setText("" + dwmData.get(0).getCovidRemark());
                tv_2_3b.setText("" + dwmData.get(0).getLpa()/*+""+dwmData.get(0).getLPA()*/);
                tv_2_6b.setText("" + dwmData.get(0).getLapRemark());
                tv_2_3c.setText("" + dwmData.get(0).getLpaCovid());
                tv_2_6c.setText("" + dwmData.get(0).getLpaCovidremark());


            } else {

            }

        }
    }


    // get yesterdayTimeSheetTask

    public class GetyesterdayTimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            response = new HttpConnection().requestGetContent("GetDetails?userid=" + myPref.getString("Id", "Id") + "&date=" + date2/*"22/05/2020"*/ + "&CKey=" + "mda@sPr$rZ#G!!");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            getyesterdayTimeSheetTask = null;
            if (response != null && response.length() > 5) {
                yes_submit_btn.setText("Update");
                dwmData = new ParseData().getDwmData(response);
                tv_1_2.setText(String.valueOf(dwmData.get(0).getDailyMMeeting()));
                tv_2_2.setText(String.valueOf(dwmData.get(0).getdWNGemba()));
                tv_3_2.setText(String.valueOf(dwmData.get(0).getQualityReview()));
                tv_4_2.setText(String.valueOf(dwmData.get(0).getCustomerVisits()));
                tv_5_2.setText(String.valueOf(dwmData.get(0).getCostingReview()));
                et_6_2_1.setText(String.valueOf(dwmData.get(0).getPeopleUnit()));
                et_6_2_2.setText(String.valueOf(dwmData.get(0).getPeopleBM()));
                et_6_2_3.setText(String.valueOf(dwmData.get(0).getPeopleLLLG()));
                et_6_2_4.setText(String.valueOf(dwmData.get(0).getPeopleTraining()));
                et_6_2_5.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                et_8_2_1_bestReview.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                et_6_2_6.setText(String.valueOf(dwmData.get(0).getPeopleCDP()));
                et_7_2_1.setText(String.valueOf(dwmData.get(0).getManagementCorp()));
                et_7_2_2.setText(String.valueOf(dwmData.get(0).getManagementProtivity()));
            //    et_7_2_3.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
                et_8_2_2_unitReview.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
                et_7_2_4.setText(String.valueOf(dwmData.get(0).getManagementMeeting()));
                et_8_2_1.setText(String.valueOf(dwmData.get(0).getRegularMMC()));
                et_8_2_2.setText(String.valueOf(dwmData.get(0).getRegularSystem()));
                et_8_2_3.setText(String.valueOf(dwmData.get(0).getRegularIndent()));
                et_8_2_4.setText(String.valueOf(dwmData.get(0).getRegularDevelopment()));
                et_8_2_5.setText(String.valueOf(dwmData.get(0).getRegularSAP()));
                et_8_2_6.setText(String.valueOf(dwmData.get(0).getRegularManufact()));
                et_8_2_7.setText(String.valueOf(dwmData.get(0).getRegularMPCP()));
                et_8_2_8.setText(String.valueOf(dwmData.get(0).getRegularSafety()));
                et_9_2_1.setText(String.valueOf(dwmData.get(0).getSupplierComm()));
                et_9_2_2.setText(String.valueOf(dwmData.get(0).getSupplierWorst()));
                et_9_2_3.setText(String.valueOf(dwmData.get(0).getSupplierSupplier()));
                tv_10_2.setText(String.valueOf(dwmData.get(0).getOtherAct()));

                tv_10_2_2.setText(String.valueOf(dwmData.get(0).getOtherAct2()));
                tv_10_3_2.setText(String.valueOf(dwmData.get(0).getOtherAct3()));
                tv_10_4_2.setText(String.valueOf(dwmData.get(0).getOtherAct4()));
                tv_10_5_2.setText(String.valueOf(dwmData.get(0).getOtherAct5()));

                tv_1_5.setText(dwmData.get(0).getDailyMMeetingRemark());
                tv_2_5.setText(dwmData.get(0).getDWNGembaRemark());
                tv_3_5.setText(dwmData.get(0).getQualityReviewRemark());
                tv_4_5.setText(dwmData.get(0).getCustomerVisitsRemark());
                tv_5_5.setText(dwmData.get(0).getCostingReviewRemark());
                cmt6_2_1.setText(dwmData.get(0).getPeopleUnitRemark());
                cmt6_2_2.setText(dwmData.get(0).getPeopleBMRemark());
                cmt6_2_3.setText(dwmData.get(0).getPeopleLLLGRemark());
                cmt6_2_4.setText(dwmData.get(0).getPeopleTrainingRemark());
                cmt8_2_1_bestReview.setText(dwmData.get(0).getPeopleBestRemark());
                cmt6_2_6.setText(dwmData.get(0).getPeopleCDPRemark());
                cmt7_2_1.setText(dwmData.get(0).getManagementCorpRemark());
                cmt7_2_2.setText(dwmData.get(0).getManagementProtivityRemark());
              //  cmt7_2_3.setText(dwmData.get(0).getManagementUnitRemark());
                cmt8_2_2_unitReview.setText(dwmData.get(0).getManagementUnitRemark());
                cmt7_2_4.setText(dwmData.get(0).getManagementMeetingRemark());
                cmt8_2_1.setText(dwmData.get(0).getRegularMMCRemark());
                cmt8_2_2.setText(dwmData.get(0).getRegularSystemRemark());
                cmt8_2_3.setText(dwmData.get(0).getRegularIndentRemark());
                cmt8_2_4.setText(dwmData.get(0).getRegularDevelopmentRemark());
                cmt8_2_5.setText(dwmData.get(0).getRegularSAPRemark());
                cmt8_2_6.setText(dwmData.get(0).getRegularManufactRemark());
                cmt8_2_7.setText(dwmData.get(0).getRegularMPCPRemark());
                cmt8_2_8.setText(dwmData.get(0).getRegularSafetyRemark());
                cmt9_2_1.setText(dwmData.get(0).getSupplierCommRemark());
                cmt9_2_2.setText(dwmData.get(0).getSupplierWorstRemark());
                cmt9_2_3.setText(dwmData.get(0).getSupplierSupplierRemark());
                tv_10_5.setText(dwmData.get(0).getOtherActRemark());

                tv_10_2_5.setText(dwmData.get(0).getOtherActRemark2());
                tv_10_3_5.setText(dwmData.get(0).getOtherActRemark3());
                tv_10_4_5.setText(dwmData.get(0).getOtherActRemark4());
                tv_10_5_5.setText(dwmData.get(0).getOtherActRemark5());

                tv_check_2.setText(dwmData.get(0).getCheckoncheck());
                tv_check_5.setText(dwmData.get(0).getCheckoncheckrmk());
                tv_1_1aE.setText("" + dwmData.get(0).getCovid());
                tv_1_5a.setText("" + dwmData.get(0).getCovidRemark());
                tv_2_1bE.setText("" + dwmData.get(0).getLpa());
                tv_2_1b5.setText("" + dwmData.get(0).getLapRemark());
                tv_2_1cE.setText("" + dwmData.get(0).getLpaCovid());
                tv_2_1c5.setText("" + dwmData.get(0).getLpaCovidremark());
            } else {

            }

        }
    }

    // get d_y_TimeSheetTask

    public class Getd_y_TimeSheetTask extends AsyncTask<Void, Void, Boolean> {
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            response = new HttpConnection().requestGetContent("GetDetails?userid=" + myPref.getString("Id", "Id") + "&date=" + date1/*"21/05/2020" */ + "&CKey=" + "mda@sPr$rZ#G!!");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            getyesterdayTimeSheetTask = null;
            if (response != null && response.length() > 5) {
                d_y_submit_btn.setText("Update");
                dwmData = new ParseData().getDwmData(response);
                if (dwmData.get(0).getDailyMMeeting() != null) {
                    tv_1_1.setText(String.valueOf(dwmData.get(0).getDailyMMeeting()));
                }
                if (dwmData.get(0).getdWNGemba() != null) {
                    tv_2_1.setText(String.valueOf(dwmData.get(0).getdWNGemba()));
                }
                if (dwmData.get(0).getQualityReview() != null) {
                    tv_3_1.setText(String.valueOf(dwmData.get(0).getQualityReview()));
                }
                if (dwmData.get(0).getCustomerVisits() != null) {
                    tv_4_1.setText(String.valueOf(dwmData.get(0).getCustomerVisits()));
                }
                if (dwmData.get(0).getCostingReview() != null) {
                    tv_5_1.setText(String.valueOf(dwmData.get(0).getCostingReview()));
                }
                if (dwmData.get(0).getPeopleUnit() != null) {
                    et_6_1_1.setText(String.valueOf(dwmData.get(0).getPeopleUnit()));
                }
                if (dwmData.get(0).getPeopleBM() != null) {
                    et_6_1_2.setText(String.valueOf(dwmData.get(0).getPeopleBM()));
                }
                if (dwmData.get(0).getPeopleLLLG() != null) {
                    et_6_1_3.setText(String.valueOf(dwmData.get(0).getPeopleLLLG()));
                }
                if (dwmData.get(0).getPeopleTraining() != null) {
                    et_6_1_4.setText(String.valueOf(dwmData.get(0).getPeopleTraining()));
                }
                if (dwmData.get(0).getPeopleBest() != null) {
                    et_6_1_5.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                    et_8_1_1_best_review.setText(String.valueOf(dwmData.get(0).getPeopleBest()));
                }
                if (dwmData.get(0).getPeopleCDP() != null) {
                    et_6_1_6.setText(String.valueOf(dwmData.get(0).getPeopleCDP()));
                }
                if (dwmData.get(0).getManagementCorp() != null) {
                    et_7_1_1.setText(String.valueOf(dwmData.get(0).getManagementCorp()));
                }
                if (dwmData.get(0).getManagementProtivity() != null) {
                    et_7_1_2.setText(String.valueOf(dwmData.get(0).getManagementProtivity()));
                }
                if (dwmData.get(0).getManagementUnit() != null) {
                    et_7_1_3.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
                    et_8_1_2_unitReview.setText(String.valueOf(dwmData.get(0).getManagementUnit()));
                }
                if (dwmData.get(0).getManagementMeeting() != null) {
                    et_7_1_4.setText(String.valueOf(dwmData.get(0).getManagementMeeting()));
                }
                if (dwmData.get(0).getRegularMMC() != null) {
                    et_8_1_1.setText(String.valueOf(dwmData.get(0).getRegularMMC()));
                }
                if (dwmData.get(0).getRegularSystem() != null) {
                    et_8_1_2.setText(String.valueOf(dwmData.get(0).getRegularSystem()));
                }
                if (dwmData.get(0).getRegularIndent() != null) {
                    et_8_1_3.setText(String.valueOf(dwmData.get(0).getRegularIndent()));
                }
                if (dwmData.get(0).getRegularDevelopment() != null) {
                    et_8_1_4.setText(String.valueOf(dwmData.get(0).getRegularDevelopment()));
                }
                if (dwmData.get(0).getRegularSAP() != null) {
                    et_8_1_5.setText(String.valueOf(dwmData.get(0).getRegularSAP()));
                }
                if (dwmData.get(0).getRegularManufact() != null) {
                    et_8_1_6.setText(String.valueOf(dwmData.get(0).getRegularManufact()));
                }
                if (dwmData.get(0).getRegularMPCP() != null) {
                    et_8_1_7.setText(String.valueOf(dwmData.get(0).getRegularMPCP()));
                }
                if (dwmData.get(0).getRegularSafety() != null) {
                    et_8_1_8.setText(String.valueOf(dwmData.get(0).getRegularSafety()));
                }
                if (dwmData.get(0).getSupplierComm() != null) {
                    et_9_1_1.setText(String.valueOf(dwmData.get(0).getSupplierComm()));
                }
                if (dwmData.get(0).getSupplierWorst() != null) {
                    et_9_1_2.setText(String.valueOf(dwmData.get(0).getSupplierWorst()));
                }
                if (dwmData.get(0).getSupplierSupplier() != null) {
                    et_9_1_3.setText(String.valueOf(dwmData.get(0).getSupplierSupplier()));
                }
                if (dwmData.get(0).getOtherAct() != null) {
                    tv_10_1.setText(String.valueOf(dwmData.get(0).getOtherAct()));
                }
                if (dwmData.get(0).getOtherAct2() != null) {
                    tv_10_2_1.setText(String.valueOf(dwmData.get(0).getOtherAct2()));
                }
                if (dwmData.get(0).getOtherAct3() != null) {
                    tv_10_3_1.setText(String.valueOf(dwmData.get(0).getOtherAct3()));
                }
                if (dwmData.get(0).getOtherAct4() != null) {
                    tv_10_4_1.setText(String.valueOf(dwmData.get(0).getOtherAct4()));
                }
                if (dwmData.get(0).getOtherAct5() != null) {
                    tv_10_5_1.setText(String.valueOf(dwmData.get(0).getOtherAct5()));
                }
                if (dwmData.get(0).getDailyMMeetingRemark() != null) {
                    tv_1_4.setText(dwmData.get(0).getDailyMMeetingRemark());
                }
                if (dwmData.get(0).getDWNGembaRemark() != null) {
                    tv_2_4.setText(dwmData.get(0).getDWNGembaRemark());
                }
                if (dwmData.get(0).getQualityReviewRemark() != null) {
                    tv_3_4.setText(dwmData.get(0).getQualityReviewRemark());
                }
                if (dwmData.get(0).getCustomerVisitsRemark() != null) {
                    tv_4_4.setText(dwmData.get(0).getCustomerVisitsRemark());
                }
                if (dwmData.get(0).getCostingReviewRemark() != null) {
                    tv_5_4.setText(dwmData.get(0).getCostingReviewRemark());
                }
                if (dwmData.get(0).getPeopleUnitRemark() != null) {
                    cmt6_1_1.setText(dwmData.get(0).getPeopleUnitRemark());
                }
                if (dwmData.get(0).getPeopleBMRemark() != null) {
                    cmt6_1_2.setText(dwmData.get(0).getPeopleBMRemark());
                }
                if (dwmData.get(0).getPeopleLLLGRemark() != null) {
                    cmt6_1_3.setText(dwmData.get(0).getPeopleLLLGRemark());
                }
                if (dwmData.get(0).getPeopleTrainingRemark() != null) {
                    cmt6_1_4.setText(dwmData.get(0).getPeopleTrainingRemark());
                }
                if (dwmData.get(0).getPeopleBestRemark() != null) {
                    cmt8_1_1_bestReview.setText(dwmData.get(0).getPeopleBestRemark());
               //     cmt6_1_5.setText(dwmData.get(0).getPeopleBestRemark());
                }
                if (dwmData.get(0).getPeopleCDPRemark() != null) {
                    cmt6_1_6.setText(dwmData.get(0).getPeopleCDPRemark());
                }
                if (dwmData.get(0).getManagementCorpRemark() != null) {
                    cmt7_1_1.setText(dwmData.get(0).getManagementCorpRemark());
                }
                if (dwmData.get(0).getManagementProtivityRemark() != null) {
                    cmt7_1_2.setText(dwmData.get(0).getManagementProtivityRemark());
                }
                if (dwmData.get(0).getManagementUnitRemark() != null) {
                 //   cmt7_1_3.setText(dwmData.get(0).getManagementUnitRemark());
                }
                if (dwmData.get(0).getManagementUnitRemark() != null) {
                    cmt8_1_2_unitReview.setText(dwmData.get(0).getManagementUnitRemark());
                }
                if (dwmData.get(0).getManagementMeetingRemark() != null) {
                    cmt7_1_4.setText(dwmData.get(0).getManagementMeetingRemark());
                }
                if (dwmData.get(0).getRegularMMCRemark() != null) {
                    cmt8_1_1.setText(dwmData.get(0).getRegularMMCRemark());
                }
                if (dwmData.get(0).getRegularSystemRemark() != null) {
                    cmt8_1_2.setText(dwmData.get(0).getRegularSystemRemark());
                }
                if (dwmData.get(0).getRegularIndentRemark() != null) {
                    cmt8_1_3.setText(dwmData.get(0).getRegularIndentRemark());
                }
                if (dwmData.get(0).getRegularDevelopmentRemark() != null) {
                    cmt8_1_4.setText(dwmData.get(0).getRegularDevelopmentRemark());
                }
                if (dwmData.get(0).getRegularSAPRemark() != null) {
                    cmt8_1_5.setText(dwmData.get(0).getRegularSAPRemark());
                }
                if (dwmData.get(0).getRegularManufactRemark() != null) {
                    cmt8_1_6.setText(dwmData.get(0).getRegularManufactRemark());
                }

                if (dwmData.get(0).getRegularMPCPRemark() != null) {
                    cmt8_1_7.setText(dwmData.get(0).getRegularMPCPRemark());
                }
                if (dwmData.get(0).getRegularSafetyRemark() != null) {
                    cmt8_1_8.setText(dwmData.get(0).getRegularSafetyRemark());
                }
                if (dwmData.get(0).getSupplierCommRemark() != null) {
                    cmt9_1_1.setText(dwmData.get(0).getSupplierCommRemark());
                }
                if (dwmData.get(0).getSupplierWorstRemark() != null) {
                    cmt9_1_2.setText(dwmData.get(0).getSupplierWorstRemark());
                }
                if (dwmData.get(0).getSupplierSupplierRemark() != null) {
                    cmt9_1_3.setText(dwmData.get(0).getSupplierSupplierRemark());
                }
                if (dwmData.get(0).getOtherActRemark() != null) {
                    tv_10_4.setText(dwmData.get(0).getOtherActRemark());
                }
                if (dwmData.get(0).getOtherActRemark2() != null) {
                    tv_10_2_4.setText(dwmData.get(0).getOtherActRemark2());
                }
                if (dwmData.get(0).getOtherActRemark3() != null) {
                    tv_10_3_4.setText(dwmData.get(0).getOtherActRemark3());
                }
                if (dwmData.get(0).getOtherActRemark4() != null) {
                    tv_10_4_4.setText(dwmData.get(0).getOtherActRemark4());
                }
                if (dwmData.get(0).getOtherActRemark5() != null) {
                    tv_10_5_4.setText(dwmData.get(0).getOtherActRemark5());
                }
                if (dwmData.get(0).getCheckoncheck() != null) {
                    tv_check_1.setText(dwmData.get(0).getCheckoncheck());
                }
                if (dwmData.get(0).getCheckoncheckrmk() != null) {
                    tv_check_4.setText(dwmData.get(0).getCheckoncheckrmk());
                }
                if (dwmData.get(0).getCovid() != null) {
                    tv_1_1_1a.setText("" + dwmData.get(0).getCovid());
                }
                if (dwmData.get(0).getCovidRemark() != null) {
                    tv_1_14.setText("" + dwmData.get(0).getCovidRemark());
                }
                if (dwmData.get(0).getLpa() != null) {
                    tv_2_1_1b.setText("" + dwmData.get(0).getLpa());
                }
                if (dwmData.get(0).getLapRemark() != null) {
                    tv_2_1b4.setText("" + dwmData.get(0).getLapRemark());
                }
                if (dwmData.get(0).getLpaCovid() != null) {
                    tv_2_1_1c.setText("" + dwmData.get(0).getLpaCovid());
                }
                if (dwmData.get(0).getLpaCovidremark() != null) {
                    tv_2_1c4.setText("" + dwmData.get(0).getLpaCovidremark());
                }

            } else {

            }

        }
    }

    // for current date listener
    void currentChangeListener() {
        tv_1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_2_3c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_2_3b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_1_3_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_4_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_5_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_3_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_6_3_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_1_bestReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_3_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_8_3_2_unitReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_3_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_3_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_4_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_5_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_check_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    currentDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    // for yesterday date current date listener

    void yesterdayChangeListener() {
        tv_1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_2_1cE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_2_1bE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_1_1aE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_4_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_5_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_2_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_2_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_8_2_1_bestReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        et_6_2_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      /*  et_7_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
        et_8_2_2_unitReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_7_2_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_2_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_4_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_5_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_check_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    yesDateAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //for day before yesterday adding

    void dayBeforeYesterdayAdd() {
        tv_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_2_1_1c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_2_1_1b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_1_1_1a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_4_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_5_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_6_1_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_8_1_1_best_review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_6_1_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_8_1_1_best_review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_6_1_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_8_1_2_unitReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_7_1_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_8_1_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_1_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_9_1_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_4_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_10_5_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_check_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    dayBeforeYesAdd();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //for current date data adding
    void currentDateAdd() {
        if ((tv_1_3.getText().toString() != null) && (!tv_1_3.getText().toString().isEmpty()) && !tv_1_3.getText().toString().equals("null")) {
            int_tv_1_3 = Integer.parseInt(tv_1_3.getText().toString());
        } else {
            int_tv_1_3 = 0;
        }
        if ((tv_2_3.getText().toString() != null) && (!tv_2_3.getText().toString().isEmpty()) && !tv_2_3.getText().toString().contains("nul")) {
            int_tv_2_3 = Integer.parseInt(tv_2_3.getText().toString());
        } else {
            int_tv_2_3 = 0;
        }
        if ((tv_2_3c.getText().toString() != null) && (!tv_2_3c.getText().toString().isEmpty()) && !tv_2_3c.getText().toString().contains("nul")) {
            int_tv_2_3c = Integer.parseInt(tv_2_3c.getText().toString());
        } else {
            int_tv_2_3c = 0;
        }

        if ((tv_check_3.getText().toString() != null) && (!tv_check_3.getText().toString().isEmpty()) && !tv_check_3.getText().toString().contains("nul")) {
            int_check_1_3 = Integer.parseInt(tv_check_3.getText().toString());
        } else {
            int_check_1_3 = 0;
        }
        if ((tv_2_3b.getText().toString() != null) && (!tv_2_3b.getText().toString().isEmpty()) && !tv_2_3b.getText().toString().contains("nul")) {
            int_tv_2_3b = Integer.parseInt(tv_2_3b.getText().toString());
        } else {
            int_tv_2_3b = 0;
        }


        if ((tv_3_3.getText().toString() != null) && (!tv_3_3.getText().toString().isEmpty()) && !tv_3_3.getText().toString().contains("nul")) {
            int_tv_3_3 = Integer.parseInt(tv_3_3.getText().toString());
        } else {
            int_tv_3_3 = 0;
        }
        if ((tv_4_3.getText().toString() != null) && (!tv_4_3.getText().toString().isEmpty()) && !tv_4_3.getText().toString().contains("nul")) {
            int_tv_4_3 = Integer.parseInt(tv_4_3.getText().toString());
        } else {
            int_tv_4_3 = 0;
        }

        if ((tv_5_3.getText().toString() != null) && (!tv_5_3.getText().toString().isEmpty()) && !tv_5_3.getText().toString().contains("nul")) {
            int_tv_5_3 = Integer.parseInt(tv_5_3.getText().toString());
        } else {
            int_tv_5_3 = 0;
        }
        if ((et_6_3_1.getText().toString() != null) && (!et_6_3_1.getText().toString().isEmpty()) && !et_6_3_1.getText().toString().contains("nul")) {
            int_et_6_3_1 = Integer.parseInt(et_6_3_1.getText().toString());
        } else {
            int_et_6_3_1 = 0;
        }
        if ((et_6_3_2.getText().toString() != null) && (!et_6_3_2.getText().toString().isEmpty()) && !et_6_3_2.getText().toString().contains("nul")) {
            int_et_6_3_2 = Integer.parseInt(et_6_3_2.getText().toString());
        } else {
            int_et_6_3_2 = 0;
        }
        if ((et_6_3_3.getText().toString() != null) && (!et_6_3_3.getText().toString().isEmpty()) && !et_6_3_3.getText().toString().contains("nul")) {
            int_et_6_3_3 = Integer.parseInt(et_6_3_3.getText().toString());
        } else {
            int_et_6_3_3 = 0;
        }
        if ((et_6_3_4.getText().toString() != null) && (!et_6_3_4.getText().toString().isEmpty()) && !et_6_3_4.getText().toString().contains("nul")) {
            int_et_6_3_4 = Integer.parseInt(et_6_3_4.getText().toString());
        } else {
            int_et_6_3_4 = 0;
        }
        if ((et_6_3_5.getText().toString() != null) && (!et_6_3_5.getText().toString().isEmpty()) && !et_6_3_5.getText().toString().contains("nul")) {
            int_et_6_3_5 = Integer.parseInt(et_6_3_5.getText().toString());
        } else {
            int_et_6_3_5 = 0;
        }

        if ((et_8_3_1_bestReview.getText().toString() != null) && (!et_8_3_1_bestReview.getText().toString().isEmpty()) && !et_8_3_1_bestReview.getText().toString().contains("nul")) {
            int_et_8_3_1_bestReview = Integer.parseInt(et_8_3_1_bestReview.getText().toString());
        } else {
            int_et_8_3_1_bestReview = 0;
        }


        if ((et_6_3_6.getText().toString() != null) && (!et_6_3_6.getText().toString().isEmpty()) && !et_6_3_6.getText().toString().contains("nul")) {
            int_et_6_3_6 = Integer.parseInt(et_6_3_6.getText().toString());
        } else {
            int_et_6_3_6 = 0;
        }
        if ((et_7_3_1.getText().toString() != null) && (!et_7_3_1.getText().toString().isEmpty()) && !et_7_3_1.getText().toString().contains("nul")) {
            int_et_7_3_1 = Integer.parseInt(et_7_3_1.getText().toString());
        } else {
            int_et_7_3_1 = 0;
        }

        if ((et_7_3_2.getText().toString() != null) && (!et_7_3_2.getText().toString().isEmpty()) && !et_7_3_2.getText().toString().contains("nul")) {
            int_et_7_3_2 = Integer.parseInt(et_7_3_2.getText().toString());
        } else {
            int_et_7_3_2 = 0;
        }
        if ((et_7_3_3.getText().toString() != null) && (!et_7_3_3.getText().toString().isEmpty()) && !et_7_3_3.getText().toString().contains("nul")) {
            int_et_7_3_3 = Integer.parseInt(et_7_3_3.getText().toString());
        } else {
            int_et_7_3_3 = 0;
        }

        if ((et_8_3_2_unitReview.getText().toString() != null) && (!et_8_3_2_unitReview.getText().toString().isEmpty()) && !et_8_3_2_unitReview.getText().toString().contains("nul")) {
            int_et_8_3_2_unitReview = Integer.parseInt(et_8_3_2_unitReview.getText().toString());
        } else {
            int_et_8_3_2_unitReview = 0;
        }

        if ((et_7_3_4.getText().toString() != null) && (!et_7_3_4.getText().toString().isEmpty()) && !et_7_3_4.getText().toString().contains("nul")) {
            int_et_7_3_4 = Integer.parseInt(et_7_3_4.getText().toString());
        } else {
            int_et_7_3_4 = 0;
        }
        if ((et_8_3_1.getText().toString() != null) && (!et_8_3_1.getText().toString().isEmpty()) && !et_8_3_1.getText().toString().contains("nul")) {
            int_et_8_3_1 = Integer.parseInt(et_8_3_1.getText().toString());
        } else {
            int_et_8_3_1 = 0;
        }
        if ((et_8_3_2.getText().toString() != null) && (!et_8_3_2.getText().toString().isEmpty()) && !et_8_3_2.getText().toString().contains("nul")) {
            int_et_8_3_2 = Integer.parseInt(et_8_3_2.getText().toString());
        } else {
            int_et_8_3_2 = 0;
        }
        if ((et_8_3_3.getText().toString() != null) && (!et_8_3_3.getText().toString().isEmpty()) && !et_8_3_3.getText().toString().contains("nul")) {
            int_et_8_3_3 = Integer.parseInt(et_8_3_3.getText().toString());
        } else {
            int_et_8_3_3 = 0;
        }
        if ((et_8_3_4.getText().toString() != null) && (!et_8_3_4.getText().toString().isEmpty()) && !et_8_3_4.getText().toString().contains("nul")) {
            int_et_8_3_4 = Integer.parseInt(et_8_3_4.getText().toString());
        } else {
            int_et_8_3_4 = 0;
        }
        if ((et_8_3_5.getText().toString() != null) && (!et_8_3_5.getText().toString().isEmpty()) && !et_8_3_5.getText().toString().contains("nul")) {
            int_et_8_3_5 = Integer.parseInt(et_8_3_5.getText().toString());
        } else {
            int_et_8_3_5 = 0;
        }
        if ((et_8_3_6.getText().toString() != null) && (!et_8_3_6.getText().toString().isEmpty()) && !et_8_3_6.getText().toString().contains("nul")) {
            int_et_8_3_6 = Integer.parseInt(et_8_3_6.getText().toString());
        } else {
            int_et_8_3_6 = 0;
        }
        if ((et_8_3_7.getText().toString() != null) && (!et_8_3_7.getText().toString().isEmpty()) && !et_8_3_7.getText().toString().contains("nul")) {

            int_et_8_3_7 = Integer.parseInt(et_8_3_7.getText().toString());
        } else {
            int_et_8_3_7 = 0;
        }
        if ((et_8_3_8.getText().toString() != null) && (!et_8_3_8.getText().toString().isEmpty()) && !et_8_3_8.getText().toString().contains("nul")) {
            int_et_8_3_8 = Integer.parseInt(et_8_3_8.getText().toString());
        } else {
            int_et_8_3_8 = 0;
        }
        if ((et_9_3_1.getText().toString() != null) && (!et_9_3_1.getText().toString().isEmpty()) && !et_9_3_1.getText().toString().contains("nul")) {
            int_et_9_3_1 = Integer.parseInt(et_9_3_1.getText().toString());
        }
        if ((et_9_3_2.getText().toString() != null) && (!et_9_3_2.getText().toString().isEmpty()) && !et_9_3_2.getText().toString().contains("nul")) {
            int_et_9_3_2 = Integer.parseInt(et_9_3_2.getText().toString());
        } else {
            int_et_9_3_2 = 0;
        }
        if ((et_9_3_3.getText().toString() != null) && (!et_9_3_3.getText().toString().isEmpty()) && !et_9_3_3.getText().toString().contains("nul")) {
            int_et_9_3_3 = Integer.parseInt(et_9_3_3.getText().toString());
        } else {
            int_et_9_3_3 = 0;
        }
        if ((tv_10_3.getText().toString() != null) && (!tv_10_3.getText().toString().isEmpty()) && !tv_10_3.getText().toString().contains("nul")) {
            int_tv_10_3 = Integer.parseInt(tv_10_3.getText().toString());
        } else {
            int_tv_10_3 = 0;
        }
        if ((tv_1_3_a.getText().toString() != null) && (!tv_1_3_a.getText().toString().isEmpty()) && !tv_1_3_a.getText().toString().contains("nul")) {
            int_tv_1_3a = Integer.parseInt(tv_1_3_a.getText().toString());
        } else {
            int_tv_1_3a = 0;
        }
        if ((tv_10_2_3.getText().toString() != null) && (!tv_10_2_3.getText().toString().isEmpty()) && !tv_10_2_3.getText().toString().contains("nul")) {
            int_tv_10_2_3 = Integer.parseInt(tv_10_2_3.getText().toString());
        } else {
            int_tv_10_2_3 = 0;
        }

        if ((tv_10_3_3.getText().toString() != null) && (!tv_10_3_3.getText().toString().isEmpty()) && !tv_10_3_3.getText().toString().contains("nul")) {
            int_tv_10_3_3 = Integer.parseInt(tv_10_3_3.getText().toString());
        } else {
            int_tv_10_3_3 = 0;
        }
        if ((tv_10_4_3.getText().toString() != null) && (!tv_10_4_3.getText().toString().isEmpty()) && !tv_10_4_3.getText().toString().contains("nul")) {
            int_tv_10_4_3 = Integer.parseInt(tv_10_4_3.getText().toString());
        } else {
            int_tv_10_4_3 = 0;
        }
        if ((tv_10_5_3.getText().toString() != null) && (!tv_10_5_3.getText().toString().isEmpty()) && !tv_10_5_3.getText().toString().contains("nul")) {
            int_tv_10_5_3 = Integer.parseInt(tv_10_5_3.getText().toString());
        } else {
            int_tv_10_5_3 = 0;
        }

        Add = int_tv_1_3 + int_tv_1_3a + int_tv_2_3 + int_tv_3_3 + int_tv_4_3 + int_tv_5_3 + int_tv_2_3b + int_tv_2_3c +
                int_et_6_3_1 + int_et_6_3_2 + int_et_6_3_3 + int_et_6_3_4 +
                /*int_et_6_3_5*/ int_et_8_3_1_bestReview + int_et_6_3_6 + int_et_7_3_1 + int_et_7_3_2 +
                /*int_et_7_3_3*/int_et_8_3_2_unitReview + int_et_7_3_4 + int_et_8_3_1 + int_et_8_3_2 +
                int_et_8_3_3 + int_et_8_3_4 + int_et_8_3_5 + int_et_8_3_6 +
                int_et_8_3_7 + int_et_8_3_8 + int_et_9_3_1 + int_et_9_3_2 +
                int_et_9_3_3 + int_tv_10_3 + int_tv_10_2_3 + int_tv_10_3_3 + int_tv_10_4_3 + int_tv_10_5_3 + int_check_1_3;
        tv_total_hour.setText(String.valueOf(Add));
    }

    //for yesterday current date data adding
    void yesDateAdd() {
        if ((tv_1_2.getText().toString() != null) && (!tv_1_2.getText().toString().isEmpty()) && !tv_1_2.getText().toString().contains("nul")) {
            int_tv_1_2 = Integer.parseInt(tv_1_2.getText().toString());
        } else {
            int_tv_1_2 = 0;
        }
        if ((tv_1_1aE.getText().toString() != null) && (!tv_1_1aE.getText().toString().isEmpty()) && !tv_1_1aE.getText().toString().contains("nul")) {
            int_tv_1_1aE = Integer.parseInt(tv_1_1aE.getText().toString());
        } else {
            int_tv_1_1aE = 0;
        }
        if ((tv_2_2.getText().toString() != null) && (!tv_2_2.getText().toString().isEmpty()) && !tv_2_2.getText().toString().contains("nul")) {
            int_tv_2_2 = Integer.parseInt(tv_2_2.getText().toString());
        } else {
            int_tv_2_2 = 0;
        }
        if ((tv_2_1bE.getText().toString() != null) && (!tv_2_1bE.getText().toString().isEmpty()) && !tv_2_1bE.getText().toString().contains("nul")) {
            int_tv_2_1bE = Integer.parseInt(tv_2_1bE.getText().toString());
        } else {
            int_tv_2_1bE = 0;
        }

        if ((tv_check_2.getText().toString() != null) && (!tv_check_2.getText().toString().isEmpty()) && !tv_check_2.getText().toString().contains("nul")) {
            int_check_1_2 = Integer.parseInt(tv_check_2.getText().toString());
        } else {
            int_check_1_2 = 0;
        }

        if ((tv_3_2.getText().toString() != null) && (!tv_3_2.getText().toString().isEmpty()) && !tv_3_2.getText().toString().contains("nul")) {
            int_tv_3_2 = Integer.parseInt(tv_3_2.getText().toString());
        } else {
            int_tv_3_2 = 0;
        }
        if ((tv_2_1cE.getText().toString() != null) && (!tv_2_1cE.getText().toString().isEmpty()) && !tv_2_1cE.getText().toString().contains("nul")) {
            int_tv_2_1cE = Integer.parseInt(tv_2_1cE.getText().toString());
        } else {
            int_tv_2_1cE = 0;
        }

        if ((tv_3_2.getText().toString() != null) && (!tv_3_2.getText().toString().isEmpty()) && !tv_3_2.getText().toString().contains("nul")) {
            int_tv_3_2 = Integer.parseInt(tv_3_2.getText().toString());
        } else {
            int_tv_3_2 = 0;
        }
        if ((tv_4_2.getText().toString() != null) && (!tv_4_2.getText().toString().isEmpty()) && !tv_4_2.getText().toString().contains("nul")) {
            int_tv_4_2 = Integer.parseInt(tv_4_2.getText().toString());
        } else {
            int_tv_4_2 = 0;
        }

        if ((tv_5_2.getText().toString() != null) && (!tv_5_2.getText().toString().isEmpty()) && !tv_5_2.getText().toString().contains("nul")) {
            int_tv_5_2 = Integer.parseInt(tv_5_2.getText().toString());
        } else {
            int_tv_5_2 = 0;
        }
        if ((et_6_2_1.getText().toString() != null) && (!et_6_2_1.getText().toString().isEmpty()) && !et_6_2_1.getText().toString().contains("nul")) {
            int_et_6_2_1 = Integer.parseInt(et_6_2_1.getText().toString());
        } else {
            int_et_6_2_1 = 0;
        }
        if ((et_6_2_2.getText().toString() != null) && (!et_6_2_2.getText().toString().isEmpty()) && !et_6_2_2.getText().toString().contains("nul")) {
            int_et_6_2_2 = Integer.parseInt(et_6_2_2.getText().toString());
        } else {
            int_et_6_2_2 = 0;
        }
        if ((et_6_2_3.getText().toString() != null) && (!et_6_2_3.getText().toString().isEmpty()) && !et_6_2_2.getText().toString().contains("nul")) {
            int_et_6_2_3 = Integer.parseInt(et_6_2_3.getText().toString());
        } else {
            int_et_6_2_3 = 0;
        }
        if ((et_6_2_4.getText().toString() != null) && (!et_6_2_4.getText().toString().isEmpty()) && !et_6_2_4.getText().toString().contains("nul")) {
            int_et_6_2_4 = Integer.parseInt(et_6_2_4.getText().toString());
        } else {
            int_et_6_2_4 = 0;
        }
        if ((et_6_2_5.getText().toString() != null) && (!et_6_2_5.getText().toString().isEmpty()) && !et_6_2_5.getText().toString().contains("nul")) {
            int_et_6_2_5 = Integer.parseInt(et_6_2_5.getText().toString());
        } else {
            int_et_6_2_5 = 0;
        }

        if ((et_8_2_1_bestReview.getText().toString() != null) && (!et_8_2_1_bestReview.getText().toString().isEmpty()) && !et_8_2_1_bestReview.getText().toString().contains("nul")) {
            int_et_8_2_1_bestReview = Integer.parseInt(et_8_2_1_bestReview.getText().toString());
        } else {
            int_et_8_2_1_bestReview = 0;
        }

        if ((et_6_2_6.getText().toString() != null) && (!et_6_2_6.getText().toString().isEmpty()) && !et_6_2_6.getText().toString().contains("nul")) {
            int_et_6_2_6 = Integer.parseInt(et_6_2_6.getText().toString());
        } else {
            int_et_6_2_6 = 0;
        }
        if ((et_7_2_1.getText().toString() != null) && (!et_7_2_1.getText().toString().isEmpty()) && !et_7_2_1.getText().toString().contains("nul")) {
            int_et_7_2_1 = Integer.parseInt(et_7_2_1.getText().toString());
        } else {
            int_et_7_2_1 = 0;
        }
        if ((et_7_2_2.getText().toString() != null) && (!et_7_2_2.getText().toString().isEmpty()) && !et_7_2_2.getText().toString().contains("nul")) {
            int_et_7_2_2 = Integer.parseInt(et_7_2_2.getText().toString());
        } else {
            int_et_7_2_2 = 0;
        }
       /* if ((et_7_2_3.getText().toString() != null) && (!et_7_2_3.getText().toString().isEmpty()) && !et_7_2_3.getText().toString().contains("nul")) {
            int_et_7_2_3 = Integer.parseInt(et_7_2_3.getText().toString());
        } else {
            int_et_7_2_3 = 0;
        }*/

        if ((et_8_2_2_unitReview.getText().toString() != null) && (!et_8_2_2_unitReview.getText().toString().isEmpty()) && !et_8_2_2_unitReview.getText().toString().contains("nul")) {
            int_et_8_2_2_unitReview = Integer.parseInt(et_8_2_2_unitReview.getText().toString());
        } else {
            int_et_8_2_2_unitReview = 0;
        }
        if ((et_7_2_4.getText().toString() != null) && (!et_7_2_4.getText().toString().isEmpty()) && !et_7_2_4.getText().toString().contains("nul")) {
            int_et_7_2_4 = Integer.parseInt(et_7_2_4.getText().toString());
        } else {
            int_et_7_2_4 = 0;
        }
        if ((et_8_2_1.getText().toString() != null) && (!et_8_2_1.getText().toString().isEmpty()) && !et_8_2_1.getText().toString().contains("nul")) {
            int_et_8_2_1 = Integer.parseInt(et_8_2_1.getText().toString());
        } else {
            int_et_8_2_1 = 0;
        }
        if ((et_8_2_2.getText().toString() != null) && (!et_8_2_2.getText().toString().isEmpty()) && !et_8_2_2.getText().toString().contains("nul")) {
            int_et_8_2_2 = Integer.parseInt(et_8_2_2.getText().toString());
        } else {
            int_et_8_2_2 = 0;
        }
        if ((et_8_2_3.getText().toString() != null) && (!et_8_2_3.getText().toString().isEmpty()) && !et_8_2_3.getText().toString().contains("nul")) {
            int_et_8_2_3 = Integer.parseInt(et_8_2_3.getText().toString());
        } else {
            int_et_8_2_3 = 0;
        }
        if ((et_8_2_4.getText().toString() != null) && (!et_8_2_4.getText().toString().isEmpty()) && !et_8_2_4.getText().toString().contains("nul")) {
            int_et_8_2_4 = Integer.parseInt(et_8_2_4.getText().toString());
        } else {
            int_et_8_2_4 = 0;
        }
        if ((et_8_2_5.getText().toString() != null) && (!et_8_2_5.getText().toString().isEmpty()) && !et_8_2_5.getText().toString().contains("nul")) {
            int_et_8_2_5 = Integer.parseInt(et_8_2_5.getText().toString());
        } else {
            int_et_8_2_5 = 0;
        }
        if ((et_8_2_6.getText().toString() != null) && (!et_8_2_6.getText().toString().isEmpty()) && !et_8_2_6.getText().toString().contains("nul")) {
            int_et_8_2_6 = Integer.parseInt(et_8_2_6.getText().toString());
        } else {
            int_et_8_2_6 = 0;
        }
        if ((et_8_2_7.getText().toString() != null) && (!et_8_2_7.getText().toString().isEmpty()) && !et_8_2_7.getText().toString().contains("nul")) {

            int_et_8_2_7 = Integer.parseInt(et_8_2_7.getText().toString());
        } else {
            int_et_8_2_7 = 0;
        }
        if ((et_8_2_8.getText().toString() != null) && (!et_8_2_8.getText().toString().isEmpty()) && !et_8_2_8.getText().toString().contains("nul")) {
            int_et_8_2_8 = Integer.parseInt(et_8_2_8.getText().toString());
        } else {
            int_et_8_2_8 = 0;
        }
        if ((et_9_2_1.getText().toString() != null) && (!et_9_2_1.getText().toString().isEmpty()) && !et_9_2_1.getText().toString().contains("nul")) {
            int_et_9_2_1 = Integer.parseInt(et_9_2_1.getText().toString());
        } else {
            int_et_9_2_1 = 0;
        }
        if ((et_9_2_2.getText().toString() != null) && (!et_9_2_2.getText().toString().isEmpty()) && !et_9_2_2.getText().toString().contains("nul")) {
            int_et_9_2_2 = Integer.parseInt(et_9_2_2.getText().toString());
        } else {
            int_et_9_2_2 = 0;
        }
        if ((et_9_2_3.getText().toString() != null) && (!et_9_2_3.getText().toString().isEmpty()) && !et_9_2_3.getText().toString().contains("nul")) {
            int_et_9_2_3 = Integer.parseInt(et_9_2_3.getText().toString());
        } else {
            int_et_9_2_3 = 0;
        }
        if ((tv_10_2.getText().toString() != null) && (!tv_10_2.getText().toString().isEmpty()) && !tv_10_2.getText().toString().contains("nul")) {
            int_tv_10_2 = Integer.parseInt(tv_10_2.getText().toString());
        } else {
            int_tv_10_2 = 0;
        }
        if ((tv_10_2_2.getText().toString() != null) && (!tv_10_2_2.getText().toString().isEmpty()) && !tv_10_2_2.getText().toString().contains("nul")) {
            int_tv_10_2_2 = Integer.parseInt(tv_10_2_2.getText().toString());
        } else {
            int_tv_10_2_2 = 0;
        }
        if ((tv_10_3_2.getText().toString() != null) && (!tv_10_3_2.getText().toString().isEmpty()) && !tv_10_3_2.getText().toString().contains("nul")) {
            int_tv_10_3_2 = Integer.parseInt(tv_10_3_2.getText().toString());
        } else {
            int_tv_10_3_2 = 0;
        }
        if ((tv_10_4_2.getText().toString() != null) && (!tv_10_4_2.getText().toString().isEmpty()) && !tv_10_4_2.getText().toString().contains("nul")) {
            int_tv_10_4_2 = Integer.parseInt(tv_10_4_2.getText().toString());
        } else {
            int_tv_10_4_2 = 0;
        }
        if ((tv_10_5_2.getText().toString() != null) && (!tv_10_5_2.getText().toString().isEmpty()) && !tv_10_5_2.getText().toString().contains("nul")) {
            int_tv_10_5_2 = Integer.parseInt(tv_10_5_2.getText().toString());
        } else {
            int_tv_10_5_2 = 0;
        }
        YesterdayAdd = int_tv_1_2 + int_tv_1_1aE + int_tv_2_2 + int_tv_3_2 + int_tv_4_2 + int_tv_5_2 + int_tv_2_1bE + int_tv_2_1cE +
                int_et_6_2_1 + int_et_6_2_2 + int_et_6_2_3 + int_et_6_2_4 + int_et_6_2_5 + int_et_6_2_6 +
                int_et_7_2_1 + int_et_7_2_2 + /*int_et_7_2_3*/int_et_8_2_2_unitReview + int_et_7_2_4 +
                int_et_8_2_1 + int_et_8_2_2 + int_et_8_2_3 + int_et_8_2_4 + int_et_8_2_5 + int_et_8_2_6 + int_et_8_2_7 + int_et_8_2_8 +
                int_et_9_2_1 + int_et_9_2_2 + int_et_9_2_3 + int_tv_10_2 + int_tv_10_2_2 + int_tv_10_3_2 + int_tv_10_4_2 + int_tv_10_5_2 +
                int_check_1_2;
        yes_tv_total_hour.setText(String.valueOf(YesterdayAdd));
    }


    void dayBeforeYesAdd() {
        if ((tv_1_1.getText().toString() != null) && (!tv_1_1.getText().toString().isEmpty()) && !tv_1_1.getText().toString().contains("nul")) {
            int_tv_1_1 = Integer.parseInt(tv_1_1.getText().toString());
        } else {
            int_tv_1_1 = 0;
        }
        if ((tv_2_1.getText().toString() != null) && (!tv_2_1.getText().toString().isEmpty()) && !tv_2_1.getText().toString().contains("nul")) {
            int_tv_2_1 = Integer.parseInt(tv_2_1.getText().toString());
        } else {
            int_tv_2_1 = 0;
        }
        if ((tv_2_1_1c.getText().toString() != null) && (!tv_2_1_1c.getText().toString().isEmpty()) && !tv_2_1_1c.getText().toString().contains("nul")) {
            int_tv_2_1_1c = Integer.parseInt(tv_2_1_1c.getText().toString());
        } else {
            int_tv_2_1_1c = 0;
        }
        if ((tv_2_1_1b.getText().toString() != null) && (!tv_2_1_1b.getText().toString().isEmpty()) && !tv_2_1_1b.getText().toString().contains("nul")) {
            int_tv_2_1_1b = Integer.parseInt(tv_2_1_1b.getText().toString());
        } else {
            int_tv_2_1_1b = 0;
        }

        if ((tv_check_1.getText().toString() != null) && (!tv_check_1.getText().toString().isEmpty()) && !tv_check_1.getText().toString().contains("nul")) {
            int_check_1_1 = Integer.parseInt(tv_check_1.getText().toString());
        } else {
            int_check_1_1 = 0;
        }

        if ((tv_3_1.getText().toString() != null) && (!tv_3_1.getText().toString().isEmpty()) && !tv_3_1.getText().toString().contains("nul")) {
            int_tv_3_1 = Integer.parseInt(tv_3_1.getText().toString());
        } else {
            int_tv_3_1 = 0;
        }
        if ((tv_4_1.getText().toString() != null) && (!tv_4_1.getText().toString().isEmpty()) && !tv_4_1.getText().toString().contains("nul")) {
            int_tv_4_1 = Integer.parseInt(tv_4_1.getText().toString());
        } else {
            int_tv_4_1 = 0;
        }

        if ((tv_5_1.getText().toString() != null) && (!tv_5_1.getText().toString().isEmpty()) && !tv_5_1.getText().toString().contains("nul")) {
            int_tv_5_1 = Integer.parseInt(tv_5_1.getText().toString());
        } else {
            int_tv_5_1 = 0;
        }
        if ((et_6_1_1.getText().toString() != null) && (!et_6_1_1.getText().toString().isEmpty()) && !et_6_1_1.getText().toString().contains("nul")) {
            int_et_6_1_1 = Integer.parseInt(et_6_1_1.getText().toString());
        } else {
            int_et_6_1_1 = 0;
        }
        if ((et_6_1_2.getText().toString() != null) && (!et_6_1_2.getText().toString().isEmpty()) && !et_6_1_2.getText().toString().contains("nul")) {
            int_et_6_1_2 = Integer.parseInt(et_6_1_2.getText().toString());
        } else {
            int_et_6_1_2 = 0;
        }
        if ((et_6_1_3.getText().toString() != null) && (!et_6_1_3.getText().toString().isEmpty()) && !et_6_1_3.getText().toString().contains("nul")) {
            int_et_6_1_3 = Integer.parseInt(et_6_1_3.getText().toString());
        } else {
            int_et_6_1_3 = 0;
        }
        if ((et_6_1_4.getText().toString() != null) && (!et_6_1_4.getText().toString().isEmpty()) && !et_6_1_4.getText().toString().contains("nul")) {
            int_et_6_1_4 = Integer.parseInt(et_6_1_4.getText().toString());
        } else {
            int_et_6_1_4 = 0;
        }
        if ((et_6_1_5.getText().toString() != null) && (!et_6_1_5.getText().toString().isEmpty()) && !et_6_1_5.getText().toString().contains("nul")) {
            int_et_6_1_5 = Integer.parseInt(et_6_1_5.getText().toString());
        } else {
            int_et_6_1_5 = 0;
        }
        if ((et_8_1_1_best_review.getText().toString() != null) && (!et_8_1_1_best_review.getText().toString().isEmpty()) && !et_8_1_1_best_review.getText().toString().contains("nul")) {
            int_et_8_1_1_best_review = Integer.parseInt(et_8_1_1_best_review.getText().toString());
        } else {
            int_et_8_1_1_best_review = 0;
        }
        if ((et_6_1_6.getText().toString() != null) && (!et_6_1_6.getText().toString().isEmpty()) && !et_6_1_6.getText().toString().contains("nul")) {
            int_et_6_1_6 = Integer.parseInt(et_6_1_6.getText().toString());
        } else {
            int_et_6_1_6 = 0;
        }
        if ((et_7_1_1.getText().toString() != null) && (!et_7_1_1.getText().toString().isEmpty()) && !et_7_1_1.getText().toString().contains("nul")) {
            int_et_7_1_1 = Integer.parseInt(et_7_1_1.getText().toString());
        } else {
            int_et_7_1_1 = 0;
        }
        if ((et_7_1_2.getText().toString() != null) && (!et_7_1_2.getText().toString().isEmpty()) && !et_7_1_2.getText().toString().contains("nul")) {
            int_et_7_1_2 = Integer.parseInt(et_7_1_2.getText().toString());
        } else {
            int_et_7_1_2 = 0;
        }
        if ((et_7_1_3.getText().toString() != null) && (!et_7_1_3.getText().toString().isEmpty()) && !et_7_1_3.getText().toString().contains("nul")) {
            int_et_7_1_3 = Integer.parseInt(et_7_1_3.getText().toString());
        } else {
            int_et_7_1_3 = 0;
        }

        if ((et_8_1_2_unitReview.getText().toString() != null) && (!et_8_1_2_unitReview.getText().toString().isEmpty()) && !et_8_1_2_unitReview.getText().toString().contains("nul")) {
            int_et_8_1_2_unitReview = Integer.parseInt(et_8_1_2_unitReview.getText().toString());
        } else {
            int_et_8_1_2_unitReview = 0;
        }


        if ((et_7_1_4.getText().toString() != null) && (!et_7_1_4.getText().toString().isEmpty()) && !et_7_1_4.getText().toString().contains("nul")) {
            int_et_7_1_4 = Integer.parseInt(et_7_1_4.getText().toString());
        } else {
            int_et_7_1_4 = 0;
        }
        if ((et_8_1_1.getText().toString() != null) && (!et_8_1_1.getText().toString().isEmpty()) && !et_8_1_1.getText().toString().contains("nul")) {
            int_et_8_1_1 = Integer.parseInt(et_8_1_1.getText().toString());
        } else {
            int_et_8_1_1 = 0;
        }
        if ((et_8_1_2.getText().toString() != null) && (!et_8_1_2.getText().toString().isEmpty()) && !et_8_1_2.getText().toString().contains("nul")) {
            int_et_8_1_2 = Integer.parseInt(et_8_1_2.getText().toString());
        } else {
            int_et_8_1_2 = 0;
        }
        if ((et_8_1_3.getText().toString() != null) && (!et_8_1_3.getText().toString().isEmpty()) && !et_8_1_3.getText().toString().contains("nul")) {
            int_et_8_1_3 = Integer.parseInt(et_8_1_3.getText().toString());
        } else {
            int_et_8_1_3 = 0;
        }
        if ((et_8_1_4.getText().toString() != null) && (!et_8_1_4.getText().toString().isEmpty()) && !et_8_1_4.getText().toString().contains("nul")) {
            int_et_8_1_4 = Integer.parseInt(et_8_1_4.getText().toString());
        } else {
            int_et_8_1_4 = 0;
        }
        if ((et_8_1_5.getText().toString() != null) && (!et_8_1_5.getText().toString().isEmpty()) && !et_8_1_5.getText().toString().contains("nul")) {
            int_et_8_1_5 = Integer.parseInt(et_8_1_5.getText().toString());
        } else {
            int_et_8_1_5 = 0;
        }
        if ((et_8_1_6.getText().toString() != null) && (!et_8_1_6.getText().toString().isEmpty()) && !et_8_1_6.getText().toString().contains("nul")) {
            int_et_8_1_6 = Integer.parseInt(et_8_1_6.getText().toString());
        } else {
            int_et_8_1_6 = 0;
        }
        if ((et_8_1_7.getText().toString() != null) && (!et_8_1_7.getText().toString().isEmpty()) && !et_8_1_7.getText().toString().contains("nul")) {

            int_et_8_1_7 = Integer.parseInt(et_8_1_7.getText().toString());
        } else {
            int_et_8_1_7 = 0;
        }
        if ((et_8_1_8.getText().toString() != null) && (!et_8_1_8.getText().toString().isEmpty()) && !et_8_1_8.getText().toString().contains("nul")) {
            int_et_8_1_8 = Integer.parseInt(et_8_1_8.getText().toString());
        } else {
            int_et_8_1_8 = 0;
        }
        if ((et_9_1_1.getText().toString() != null) && (!et_9_1_1.getText().toString().isEmpty()) && !et_9_1_1.getText().toString().contains("nul")) {
            int_et_9_1_1 = Integer.parseInt(et_9_1_1.getText().toString());
        } else {
            int_et_9_1_1 = 0;
        }
        if ((et_9_1_2.getText().toString() != null) && (!et_9_1_2.getText().toString().isEmpty()) && !et_9_1_2.getText().toString().contains("nul")) {
            int_et_9_1_2 = Integer.parseInt(et_9_1_2.getText().toString());
        } else {
            int_et_9_1_2 = 0;
        }
        if ((et_9_1_3.getText().toString() != null) && (!et_9_1_3.getText().toString().isEmpty()) && !et_9_1_3.getText().toString().contains("nul")) {
            int_et_9_1_3 = Integer.parseInt(et_9_1_3.getText().toString());
        } else {
            int_et_9_1_3 = 0;
        }
        if ((tv_10_1.getText().toString() != null) && (!tv_10_1.getText().toString().isEmpty()) && !tv_10_1.getText().toString().contains("nul")) {
            int_tv_10_1 = Integer.parseInt(tv_10_1.getText().toString());
        } else {
            int_tv_10_1 = 0;
        }


        if ((tv_10_2_1.getText().toString() != null) && (!tv_10_2_1.getText().toString().isEmpty()) && !tv_10_2_1.getText().toString().contains("nul")) {
            int_tv_10_2_1 = Integer.parseInt(tv_10_2_1.getText().toString());
        } else {
            int_tv_10_2_1 = 0;
        }
        if ((tv_10_3_1.getText().toString() != null) && (!tv_10_3_1.getText().toString().isEmpty()) && !tv_10_3_1.getText().toString().contains("nul")) {
            int_tv_10_3_1 = Integer.parseInt(tv_10_3_1.getText().toString());
        } else {
            int_tv_10_3_1 = 0;
        }
        if ((tv_10_4_1.getText().toString() != null) && (!tv_10_4_1.getText().toString().isEmpty()) && !tv_10_4_1.getText().toString().contains("nul")) {
            int_tv_10_4_1 = Integer.parseInt(tv_10_4_1.getText().toString());
        } else {
            int_tv_10_4_1 = 0;
        }
        if ((tv_10_5_1.getText().toString() != null) && (!tv_10_5_1.getText().toString().isEmpty()) && !tv_10_5_1.getText().toString().contains("nul")) {
            int_tv_10_5_1 = Integer.parseInt(tv_10_5_1.getText().toString());
        } else {
            int_tv_10_5_1 = 0;
        }

        if ((tv_1_1_1a.getText().toString() != null) && (!tv_1_1_1a.getText().toString().isEmpty()) && !tv_1_1_1a.getText().toString().contains("nul")) {
            int_tv_1_1_1a = Integer.parseInt(tv_1_1_1a.getText().toString());
        } else {
            int_tv_1_1_1a = 0;
        }

        D_Y_Add = int_tv_1_1 + int_tv_1_1_1a + int_tv_2_1 + int_tv_2_1_1b + int_tv_3_1 + int_tv_4_1 + int_tv_5_1 + int_tv_2_1_1c +
                int_et_6_1_1 + int_et_6_1_2 + int_et_6_1_3 + int_et_6_1_4 + /*int_et_6_1_5*/ int_et_8_1_1_best_review + int_et_6_1_6 +
                int_et_7_1_1 + int_et_7_1_2 + /*int_et_7_1_3*/int_et_8_1_2_unitReview + int_et_7_1_4 +
                int_et_8_1_1 + int_et_8_1_2 + int_et_8_1_3 + int_et_8_1_4 + int_et_8_1_5 + int_et_8_1_6 + int_et_8_1_7 + int_et_8_1_8 +
                int_et_9_1_1 + int_et_9_1_2 + int_et_9_1_3 + int_tv_10_1 + int_tv_10_2_1 + int_tv_10_3_1 + int_tv_10_4_1 + int_tv_10_5_1
                + int_check_1_1;
        d_y_tv_total_hour.setText(String.valueOf(D_Y_Add));

    }

}