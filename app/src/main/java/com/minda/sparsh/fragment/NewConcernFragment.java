package com.minda.sparsh.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.customview.NoDefaultSpinner;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.services.EHSServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class NewConcernFragment extends Fragment {

    @BindView(R.id.new_concern)
    RelativeLayout newConcern;
    @BindView(R.id.concern_date_spinner)
    TextView concernDateText;
    @BindView(R.id.unit_spinner)
    NoDefaultSpinner unitSpinner;
    @BindView(R.id.responsible_spinner)
    NoDefaultSpinner responsibleSpinner;
    @BindView(R.id.msm_reference_value)
    EditText msmReferenceValue;
    @BindView(R.id.existing_system_value)
    EditText existingSystemValue;
    @BindView(R.id.proposed_system_value)
    EditText proposedSystemValue;
    @BindView(R.id.benefit_value)
    EditText benefitValue;
    @BindView(R.id.save)
    Button save;
    String unitcode;
    SharedPreferences myPref;
    ArrayList<String> unitsName = new ArrayList<String>();
    List<EHSUnitModel> units = new ArrayList<EHSUnitModel>();
    ArrayAdapter<String> adapterUnit, adapterResponsible6M;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newConcern = inflater.inflate(R.layout.new_concern, container, false);
        ButterKnife.bind(this, newConcern);
        concernDateText.setText(getlogDate(System.currentTimeMillis()));
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        initUnitSpinner();
        getUnits();

        return newConcern;
    }

    public String getlogDate(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hr = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int am_pm = cal.get(Calendar.AM_PM);
        String AM_PM;
        if (am_pm == 0) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        month = month + 1;
        String monthNo;
        if (month < 10) {
            monthNo = "0" + month;
        } else {
            monthNo = "" + month;
        }
        String dayOfMonthStr;
        if (day < 10) {
            dayOfMonthStr = "0" + day;
        } else {
            dayOfMonthStr = "" + day;
        }

        return dayOfMonthStr + "-" + monthNo + "-" + year + " " + hr + ":" + min + " " + AM_PM;
    }

    public void getUnits() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getUnits(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                units.clear();
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSUnitModel> list = (List<EHSUnitModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        units.addAll(list);
                        for (EHSUnitModel unit : units) {
                            unitsName.add(unit.getUnitCode() + ":" + unit.getUnitName());
                        }
                        if (unitsName.size() > 0) {
                            unitSpinner.setSelection(1);
                        }
                    }
                }
            }
        }, unitcode);

    }

    public void initUnitSpinner() {
        unitsName.clear();
        unitsName.add("Select");
        adapterUnit = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, unitsName) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adapterUnit.setDropDownViewResource(R.layout.spinner_row);
        unitSpinner.setAdapter(adapterUnit);
        unitSpinner.setSelection(0);

    }


}
