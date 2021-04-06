package com.minda.sparsh.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.minda.sparsh.AbnormalityAddressing2Activity;
import com.minda.sparsh.AbnormalityAddressingActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.ViewImageActivity;
import com.minda.sparsh.model.AbnormalityView_Model;
import com.minda.sparsh.model.AddTargetDate_Model;
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

public class AbnormalityAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater = null;
    private List<AbnormalityView_Model> homeData = null;
    public String date;
    public int total;
    AbnormalityAdapter.ViewHolder holder;
    private ProgressDialog progress = null;
    long time, time_target, time_update, time_abnormility, oneday = 86400000;


    public AbnormalityAdapter(Context applicationContext, List<AbnormalityView_Model> venueData) {
        this.mContext = applicationContext;
        inflater = LayoutInflater.from(mContext);
        this.homeData = venueData;
        progress = new ProgressDialog(mContext);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        SimpleDateFormat timeformet = new SimpleDateFormat("hh:mm a");
        time = System.currentTimeMillis();


    }

    public class ViewHolder {
        public TextView tv_result, tv_status, tv_Actual_date, tv_test_date, tv_action, tv_plant, tv_business, tv_domain, tv_category, tv_date, tv_abnormality, tv_sn, tv_update, tv_view, tv_uplodedBy, tv_department, tv_updatedby;
        public LinearLayout laycellview;
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

        if (convertView == null) {
            final ViewHolder holder = new ViewHolder();
            this.holder = holder;
            convertView = inflater.inflate(R.layout.abnormality, null);
            holder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_Actual_date = (TextView) convertView.findViewById(R.id.tv_Actual_date);
            holder.tv_test_date = (TextView) convertView.findViewById(R.id.tv_test_date);
            holder.tv_uplodedBy = (TextView) convertView.findViewById(R.id.tv_uplodedBy);
            holder.tv_action = (TextView) convertView.findViewById(R.id.tv_action);
            holder.tv_updatedby = (TextView) convertView.findViewById(R.id.tv_updatedby);

            holder.tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            holder.tv_plant = (TextView) convertView.findViewById(R.id.tv_plant);
            holder.tv_business = (TextView) convertView.findViewById(R.id.tv_business);
            holder.tv_domain = (TextView) convertView.findViewById(R.id.tv_domain);
            holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_abnormality = (TextView) convertView.findViewById(R.id.tv_abnormality);
            holder.tv_sn = (TextView) convertView.findViewById(R.id.tv_sn);
            holder.laycellview = (LinearLayout) convertView.findViewById(R.id.laycellview);
            holder.tv_update = (TextView) convertView.findViewById(R.id.tv_update);
            holder.tv_view = (TextView) convertView.findViewById(R.id.tv_view);
            holder.tv_updatedby.setText(homeData.get(position).getUploadedBy());

            convertView.setTag(holder);
            if (homeData.get(position).getTargetDate() != null && !homeData.get(position).getTargetDate().equalsIgnoreCase("")) {
                holder.tv_test_date.setText(homeData.get(position).getTargetDate());
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
                    Date mDate1 = sdf.parse(updatedate);
                    time_update = mDate1.getTime();
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
            holder.tv_abnormality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeData.get(position).getDescription().length() != 0)
                        showdetail("Abnormality", homeData.get(position).getDescription());
                }
            });
            holder.tv_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeData.get(position).getAction() != null) {
                        if (homeData.get(position).getAction().length() != 0)
                            showdetail("Action", homeData.get(position).getAction());
                    }
                }
            });
            holder.tv_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (homeData.get(position).getBenefits() != null) {
                        if (homeData.get(position).getBenefits().length() != 0)
                            showdetail("Benifits", homeData.get(position).getBenefits());
                    }
                }
            });

            holder.tv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                }
            });
            holder.tv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ViewImageActivity.class);
                    intent.putExtra("ID", homeData.get(position).getID());
                    mContext.startActivity(intent);
                }
            });

            holder.tv_test_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                        if (holder.tv_test_date.getText().toString().equalsIgnoreCase("Add Date")) {
                            setdate(homeData.get(position).getID(), position, holder.tv_test_date);

                        } else {
                            Snackbar.make(view, "You have Already set Target Date", Snackbar.LENGTH_LONG).show();
                        }


                    } else {
                        Snackbar.make(view, "You are not Authorized", Snackbar.LENGTH_LONG).show();
                    }
                }
            });


            //"\n" + promotionData.get(position).getEventDate());

        }


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
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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


                    }
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
                public void onResponse(Call<List<AddTargetDate_Model>> call, Response<List<AddTargetDate_Model>> response) {
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
                public void onFailure(Call<List<AddTargetDate_Model>> call, Throwable t) {

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

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


} 
