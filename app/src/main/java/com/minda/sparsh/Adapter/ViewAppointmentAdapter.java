package com.minda.sparsh.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.UpdateVisitorDetailsActivity;
import com.minda.sparsh.VisitorManagementActivity;
import com.minda.sparsh.VisitorManagementListActivity;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.model.ViewAppointmentModel;
import com.minda.sparsh.util.Constant;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAppointmentAdapter extends RecyclerView.Adapter<ViewAppointmentAdapter.MyViewHolder> {
    private List<ViewAppointmentModel> viewAppointmentModelList;
    Context context;
    private ProgressDialog progress = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_appointment_date, tv_time, tv_name, tv_compname, tv_mob, tv_purpose, tv_edit, tv_cancel,
                tv_additional_person, tv_check_in, tv_check_out, tv_visitor_id;
        LinearLayout lay_edit_cancel, lay_visit_cancel;

        public MyViewHolder(View view) {
            super(view);
            progress = new ProgressDialog(context);
            progress.setMessage("Please wait...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);

            lay_edit_cancel = (LinearLayout) view.findViewById(R.id.lay_edit_cancel);
            lay_visit_cancel = (LinearLayout) view.findViewById(R.id.lay_visit_cancel);
            tv_appointment_date = (TextView) view.findViewById(R.id.tv_appointment_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_compname = (TextView) view.findViewById(R.id.tv_compname);
            tv_mob = (TextView) view.findViewById(R.id.tv_mob);
            tv_purpose = (TextView) view.findViewById(R.id.tv_purpose);
            tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
            tv_additional_person = (TextView) view.findViewById(R.id.tv_additional_person);
            tv_check_in = (TextView) view.findViewById(R.id.tv_check_in);
            tv_check_out = (TextView) view.findViewById(R.id.tv_check_out);
            tv_visitor_id = (TextView) view.findViewById(R.id.tv_visitor_id);

        }
    }


    public ViewAppointmentAdapter(List<ViewAppointmentModel> viewAppointmentModelList, Context context1) {
        this.viewAppointmentModelList = viewAppointmentModelList;
        this.context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_appointment_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ViewAppointmentModel viewAppointmentModel = viewAppointmentModelList.get(position);
        holder.tv_appointment_date.setText(viewAppointmentModel.getAppointmentDate1());
        holder.tv_time.setText(viewAppointmentModel.getTimeIn() + " - " + viewAppointmentModel.getTimeOut());
        holder.tv_name.setText(viewAppointmentModel.getFirstName() + " " + viewAppointmentModel.getLastName());
        holder.tv_compname.setText(viewAppointmentModel.getCompanyName());
        holder.tv_mob.setText(viewAppointmentModel.getMobile());
        holder.tv_purpose.setText(viewAppointmentModel.getPurpose());
        holder.tv_additional_person.setText(viewAppointmentModel.getAddPersons());
        holder.tv_visitor_id.setText(viewAppointmentModel.getVisitorLogID() + "");
        if (viewAppointmentModel.getCheckIn() != null) {
            holder.tv_check_in.setText(viewAppointmentModel.getCheckIn() + "");

        }
        if (viewAppointmentModel.getCheckOut() != null) {
            holder.tv_check_out.setText(viewAppointmentModel.getCheckOut() + "");

        }

        if (viewAppointmentModel.getMainStatus()) {
            holder.lay_edit_cancel.setVisibility(View.VISIBLE);
            holder.lay_visit_cancel.setVisibility(View.GONE);

        } else {
            holder.lay_edit_cancel.setVisibility(View.GONE);
            holder.lay_visit_cancel.setVisibility(View.VISIBLE);
        }

        if (viewAppointmentModel.getCheckIn() != null) {
            holder.lay_edit_cancel.setVisibility(View.GONE);
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate = sdf.format(c.getTime());
        if (!checkTimings(strDate, viewAppointmentModel.getAppointmentDate1() + " " + viewAppointmentModel.getTimeOut())) {
            holder.lay_edit_cancel.setVisibility(View.GONE);

        }
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String strDate = sdf.format(c.getTime());
                if (checkTimings(strDate, viewAppointmentModel.getAppointmentDate1() + " " + viewAppointmentModel.getTimeOut())) {
                    Intent intent = new Intent(context, VisitorManagementActivity.class);
                    intent.putExtra(Constant.CALL_FROM_ACTIVITY, "visitor_list");
                    intent.putExtra(Constant.VISITOR_ID, viewAppointmentModel.getVisitorLogID());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Time passed you can not added the details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String strDate = sdf.format(c.getTime());
                if (checkTimings(strDate, viewAppointmentModel.getAppointmentDate1() + " " + viewAppointmentModel.getTimeOut())) {
                    hitUpdateVisitorStatusApi(RetrofitClient2.CKEY, String.valueOf(viewAppointmentModel.getVisitorLogID()), "0");

                } else {
                    Toast.makeText(context, "Appointment can't cancel", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAppointmentModelList.size();
    }


    public void hitUpdateVisitorStatusApi(String key, String visitorId, String status) {
        if (Utility.isOnline(context)) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = anInterface.UpdateVisitorStatus(key, visitorId, status);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
                    dismissProgress();
                    List<AddAbnormality_Model> rps = response.body();

                    try {
                        if (rps != null) {
                            if (rps.get(0).getColumn1() != null) {

                                Toast.makeText(context, "Visit Canceled", Toast.LENGTH_LONG).show();
                                ((VisitorManagementListActivity) context).finish();

                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(context, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        try {
            if (progress != null)
                progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissProgress() {
        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // time compare
//    private boolean checkTimings(String time, String endtime) {
//
//        String pattern = "HH:mm";
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//
//        try {
//            Date date1 = sdf.parse(time);
//            Date date2 = sdf.parse(endtime);
//
//            if (date1.before(date2)) {
//                return true;
//            } else {
//
//                return false;
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    private boolean checkTimings(String systemDate, String serverDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            String str1 = systemDate;
            Date date1 = formatter.parse(str1);

            String str2 = serverDate;
            Date date2 = formatter.parse(str2);

            if (date1.compareTo(date2) < 0) {
                System.out.println("date2 is Greater than my date1");
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
