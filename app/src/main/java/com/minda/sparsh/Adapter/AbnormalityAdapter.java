package com.minda.sparsh.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.minda.sparsh.AbnormalityAddressing2Activity;
import com.minda.sparsh.AbnormalityAddressingActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.LoginActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.ViewImageActivity;
import com.minda.sparsh.model.AbnormalityView_Model;
import com.minda.sparsh.model.AddTargetDate_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbnormalityAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<AbnormalityView_Model> homeData;
    public String date;
    public int total;
    AbnormalityAdapter.ViewHolder holder;
    private final ProgressDialog progress;
    long time, time_target, time_update, time_abnormility, oneday = 86400000;
    private String mUserChoosenTask = "";
    String username, empcode;

    SharedPreferences myPref;


    public AbnormalityAdapter(Context applicationContext, List<AbnormalityView_Model> venueData) {
        this.mContext = applicationContext;
        inflater = LayoutInflater.from(mContext);
        this.homeData = venueData;
        progress = new ProgressDialog(mContext);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        time = System.currentTimeMillis();
        myPref = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        username = myPref.getString("username", "");
        empcode = myPref.getString("Id", "Id");

    }

    public static class ViewHolder {
        public TextView level, tv_result, tv_status, tv_Actual_date, tv_test_date, tv_action, tv_plant, tv_business, tv_domain, tv_category, tv_date, tv_abnormality, tv_sn, tv_update, tv_view, tv_uplodedBy, tv_department, tv_updatedby, remark;
        public LinearLayout laycellview;

        public ViewHolder(View view) {
            level = view.findViewById(R.id.level);
            tv_result = view.findViewById(R.id.tv_result);
            tv_status = view.findViewById(R.id.tv_status);
            tv_Actual_date = view.findViewById(R.id.tv_Actual_date);
            tv_test_date = view.findViewById(R.id.tv_test_date);
            tv_uplodedBy = view.findViewById(R.id.tv_uplodedBy);
            tv_action = view.findViewById(R.id.tv_action);
            tv_updatedby = view.findViewById(R.id.tv_updatedby);

            tv_department = view.findViewById(R.id.tv_department);
            tv_plant = view.findViewById(R.id.tv_plant);
            tv_business = view.findViewById(R.id.tv_business);
            tv_domain = view.findViewById(R.id.tv_domain);
            tv_category = view.findViewById(R.id.tv_category);
            tv_date = view.findViewById(R.id.tv_date);
            tv_abnormality = view.findViewById(R.id.tv_abnormality);
            tv_sn = view.findViewById(R.id.tv_sn);
            laycellview = view.findViewById(R.id.laycellview);
            tv_update = view.findViewById(R.id.tv_update);
            tv_view = view.findViewById(R.id.tv_view);
            remark = view.findViewById(R.id.remark);

        }
    }

    @Override
    public int getCount() {
        return homeData.size();
    }

    @Override
    public Object getItem(int position) {
        return homeData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.abnormality, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_updatedby.setText(homeData.get(position).getUploadedBy());
        holder.level.setText(homeData.get(position).getFlag());
        holder.remark.setOnClickListener(view -> {
            if (holder.remark.getText().toString() != null && !holder.remark.getText().toString().equals("NA")) {
                showRemarks(holder.remark.getText().toString());
            }
        });

        switch (homeData.get(position).getFlag()) {
            case "Pending for assigning at BEST Coordinator":
                holder.remark.setText("NA");
                break;
            case "Pending at HOD":
                if (homeData.get(position).getAssignRemark() != null) {
                    holder.remark.setText("" + homeData.get(position).getAssignRemark());
                }
                break;
            case "Pending at User":
                if (homeData.get(position).getSendBackRemark() != null) {
                    holder.remark.setText("" + homeData.get(position).getSendBackRemark());
                }
                break;
            case "Pending for approval at BEST Coordinator":
                holder.remark.setText("NA");
                break;
            case "Closed":
                if (homeData.get(position).getCloserRemark() != null) {
                    holder.remark.setText("" + homeData.get(position).getCloserRemark());
                }
                break;

        }
        if (homeData.get(position).getTargetDate() != null && !homeData.get(position).getTargetDate().equalsIgnoreCase("")) {
            holder.tv_test_date.setText(homeData.get(position).getTargetDate());
        }
        else{
            holder.tv_test_date.setText("Add Date");

        }

        holder.tv_result.setText(homeData.get(position).getBenefits());
        if (homeData.get(position).getTargetDate() != null) {
            String tergetdate, updatedate, abnormilitydate;

            tergetdate = homeData.get(position).getTargetDate() + " " + "11:59 PM";
            updatedate = homeData.get(position).getImplementationDate() + " " + "11:59 PM";
            abnormilitydate = homeData.get(position).getAbnormalityDate() + " " + "11:59 PM";

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            try {
                Date mDate = sdf.parse(tergetdate);
                time_target = mDate.getTime();
                if (homeData.get(position).getImplementationDate() != null) {
                    Date mDate1 = sdf.parse(updatedate);
                    time_update = mDate1.getTime();
                }
                Date mDate2 = sdf.parse(abnormilitydate);
                time_abnormility = mDate2.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (time_target < time) {

                if (homeData.get(position).getStatus()) {
                    holder.tv_status.setBackgroundResource(R.color.red);
                    if (time_update != 0) {
                        holder.tv_status.setText(String.valueOf((time_update - time_target) / oneday) + " D Late");
                    } else {
                        holder.tv_status.setText(String.valueOf((time - time_target) / oneday) + " D Late");
                    }

                } else {
                    if (time_update > time_target) {
                        holder.tv_status.setBackgroundResource(R.color.orange);
                        if (time_update != 0) {
                            holder.tv_status.setText(String.valueOf((time_update - time_target) / oneday) + " D Late");
                        } else {
                            holder.tv_status.setText(String.valueOf((time - time_target) / oneday) + " D Late");
                        }
                    } else {
                        holder.tv_status.setBackgroundResource(R.color.green);
                    }
                }
            } else {
                if (homeData.get(position).getStatus()) {
                    holder.tv_status.setBackgroundResource(R.color.white);
                } else {
                    if (time_update > time_target) {
                        holder.tv_status.setBackgroundResource(R.color.orange);
                        if (time_update != 0) {
                            holder.tv_status.setText(String.valueOf((time_update - time_target) / oneday) + " D Late");
                        } else {
                            holder.tv_status.setText(String.valueOf((time - time_target) / oneday) + " D Late");
                        }
                    } else {
                        holder.tv_status.setBackgroundResource(R.color.green);
                    }

                }
            }


        } else {
            String abnormilitydate;

            abnormilitydate = homeData.get(position).getAbnormalityDate() + " " + "11:59 PM";


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            try {
                Date mDate = sdf.parse(abnormilitydate);
                time_abnormility = mDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (time_abnormility + (3 * oneday) < time) {
                holder.tv_status.setBackgroundResource(R.color.red);
            } else {
                holder.tv_status.setBackgroundResource(R.color.white);
            }
        }

        holder.tv_Actual_date.setText(homeData.get(position).getImplementationDate());
//            holder.tv_test_date.setText(homeData.get(position).getAbnormalityDate());
        holder.tv_action.setText(homeData.get(position).getAction());
        holder.tv_department.setText(homeData.get(position).getDepartmentName());
        holder.tv_plant.setText(homeData.get(position).getPlantName());
        holder.tv_business.setText(homeData.get(position).getBusinessName());
        holder.tv_category.setText(homeData.get(position).getCategory());
        holder.tv_domain.setText(homeData.get(position).getDomain());
        holder.tv_date.setText(homeData.get(position).getAbnormalityDate());
        holder.tv_abnormality.setText(homeData.get(position).getDescription());
        holder.tv_sn.setText(String.valueOf(position + 1));
        holder.tv_uplodedBy.setText(homeData.get(position).getUpdatedBy());
        holder.tv_abnormality.setOnClickListener(view -> {
            if (homeData.get(position).getDescription().length() != 0)
                showdetail("Abnormality", homeData.get(position).getDescription());
        });
        holder.tv_action.setOnClickListener(view -> {
            if (homeData.get(position).getAction() != null) {
                if (homeData.get(position).getAction().length() != 0)
                    showdetail("Action", homeData.get(position).getAction());
            }
        });
        holder.tv_result.setOnClickListener(view -> {
            if (homeData.get(position).getBenefits() != null) {
                if (homeData.get(position).getBenefits().length() != 0)
                    showdetail("Benifits", homeData.get(position).getBenefits());
            }
        });

        holder.tv_update.setOnClickListener(view -> {

            // showUpdateOptions();
            if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                if (holder.tv_test_date.getText().toString() != null && holder.tv_test_date.getText().toString().length() > 0 && !holder.tv_test_date.getText().toString().equalsIgnoreCase("Add Date")/*get(position).getTargetDate() != null*/) {
                    Intent intent = new Intent(mContext, AbnormalityAddressing2Activity.class);
                    intent.putExtra("ID", homeData.get(position).getID());
                    intent.putExtra("domain", homeData.get(position).getDomain());
                    intent.putExtra("business", homeData.get(position).getBusinessName());
                    intent.putExtra("plant", homeData.get(position).getPlantName());
                    intent.putExtra("department", homeData.get(position).getDepartmentName());
                    mContext.startActivity(intent);
                    AbnormalityAddressingActivity context = (AbnormalityAddressingActivity) mContext;
                    context.finish();
                } else {
                    Snackbar.make(view, "Please Set Target Date First", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(view, "You are not Authorized", Snackbar.LENGTH_LONG).show();
            }
        });

        holder.tv_view.setOnClickListener(view -> {
               /* Intent intent = new Intent(mContext, ViewImageActivity.class);
                intent.putExtra("ID", homeData.get(position).getID());
                intent.putExtra("Level",homeData.get(position).getFlag());
                mContext.startActivity(intent);
          */
            switch (homeData.get(position).getFlag()) {
                case "Pending at HOD":
                    if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("HOD") && homeData.get(position).getAssignedto().equals(empcode)) {
                        if (holder.tv_test_date.getText().toString() != null && holder.tv_test_date.getText().toString().length() > 0 && !holder.tv_test_date.getText().toString().equalsIgnoreCase("Add Date")/*get(position).getTargetDate() != null*/) {
                            Intent intent = new Intent(mContext, AbnormalityAddressing2Activity.class);
                            intent.putExtra("ID", homeData.get(position).getID());
                            intent.putExtra("domain", homeData.get(position).getDomain());
                            intent.putExtra("business", homeData.get(position).getBusinessName());
                            intent.putExtra("plant", homeData.get(position).getPlantName());
                            intent.putExtra("department", homeData.get(position).getDepartmentName());
                            mContext.startActivity(intent);
                            AbnormalityAddressingActivity context = (AbnormalityAddressingActivity) mContext;
                            context.finish();
                        } else {
                            Intent intent = new Intent(mContext, ViewImageActivity.class);
                            intent.putExtra("ID", homeData.get(position).getID());
                            intent.putExtra("Level", homeData.get(position).getFlag());
                            intent.putExtra("assignedto", homeData.get(position).getAssignedto());
                            mContext.startActivity(intent);

                        }

                    } else {
                        Intent intent = new Intent(mContext, ViewImageActivity.class);
                        intent.putExtra("ID", homeData.get(position).getID());
                        intent.putExtra("Level", homeData.get(position).getFlag());
                        mContext.startActivity(intent);

                        // Snackbar.make(view, "Please Set Target Date First", Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case "Pending for assigning at BEST Coordinator":
                case "Closed":
                    Intent intent = new Intent(mContext, ViewImageActivity.class);
                    intent.putExtra("ID", homeData.get(position).getID());
                    intent.putExtra("Level", homeData.get(position).getFlag());
                    mContext.startActivity(intent);
                    break;
                case "Pending for approval at BEST Coordinator":

                    if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                        Intent intent1 = new Intent(mContext, ViewImageActivity.class);
                        intent1.putExtra("ID", homeData.get(position).getID());
                        intent1.putExtra("Level", homeData.get(position).getFlag());
                        mContext.startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(mContext, ViewImageActivity.class);
                        intent1.putExtra("ID", homeData.get(position).getID());
                        intent1.putExtra("Level", homeData.get(position).getFlag());
                        mContext.startActivity(intent1);

                    }
                    break;
                case "Pending at User":
                    if (username.equalsIgnoreCase(homeData.get(position).getUploadedBy())) {
                        Intent intent4 = new Intent(mContext, AbnormalityAddressingActivity.class);
                        intent4.putExtra("ab", (Parcelable) homeData.get(position));
                        intent4.putExtra("ADD", true);
                        mContext.startActivity(intent4);
                    } else {
                        Intent intent3 = new Intent(mContext, ViewImageActivity.class);
                        intent3.putExtra("ID", homeData.get(position).getID());
                        intent3.putExtra("Level", homeData.get(position).getFlag());
                        intent3.putExtra("uploadedby", homeData.get(position).getUploadedBy());
                        mContext.startActivity(intent3);
                    }
                    break;

            }

        });

        holder.tv_test_date.setOnClickListener(view -> {

            if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                if (holder.tv_test_date.getText().toString().equalsIgnoreCase("Add Date")) {
                    setdate(homeData.get(position).getID(), position, holder.tv_test_date);

                } else {
                    Snackbar.make(view, "You have Already set Target Date", Snackbar.LENGTH_LONG).show();
                }


            } else {
                Snackbar.make(view, "You are not Authorized", Snackbar.LENGTH_LONG).show();
            }
        });
        //"\n" + promotionData.get(position).getEventDate());


        return convertView;
    }

    public void showdetail(String title, String messege) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(title);
        dialog.setMessage(messege);
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void setdate(final int id, final int position, TextView textView) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String month = String.valueOf(monthOfYear + 1);

                    if (month.length() == 1) {
                        month = "0" + month;
                    } else {
                        month = month;
                    }

                    if (String.valueOf(dayOfMonth + 1).length() == 1) {
                        date = "0" + String.valueOf(dayOfMonth) + "-" + month + "-" + String.valueOf(year);
                    } else {
                        date = String.valueOf(dayOfMonth) + "-" + month + "-" + String.valueOf(year);
                    }

                    textView.setText(date);

                    hitSetTargetDateApi(id, date, homeData.get(position).getDepartment(), holder.tv_test_date);


                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void hitSetTargetDateApi(int id, String date, final String department, TextView textView) {
        if (Utility.isOnline(mContext)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddTargetDate_Model>> response = promotingMyinterface.SetTargetDate(RetrofitClient2.CKEY, id, date);
            response.enqueue(new Callback<List<AddTargetDate_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<AddTargetDate_Model>> call, @NotNull Response<List<AddTargetDate_Model>> response) {
                    showProgress(false);
                    List<AddTargetDate_Model> AddDateresponse = response.body();
                    textView.setText(date);
                    notifyDataSetChanged();
                    if (AddDateresponse != null) {
                        Toast.makeText(mContext, AddDateresponse.get(0).getColumn1(), Toast.LENGTH_LONG).show();
                        AbnormalityAddressingActivity contxt = (AbnormalityAddressingActivity) mContext;
                        contxt.hitSubdepartmentApi(department);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<AddTargetDate_Model>> call, @NotNull Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean b) {
        try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy hh:mm a";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(time);
            assert date != null;
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void showRemarks(String remarks) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(remarks);
        alertDialogBuilder.setTitle("Remarks");
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            arg0.dismiss();

        });

        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

} 
