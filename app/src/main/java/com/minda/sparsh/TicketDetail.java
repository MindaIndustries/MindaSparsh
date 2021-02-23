package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.TicketHistoryAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.TicketHistoryResponse;
import com.minda.sparsh.services.ITHelpDeskServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketDetail extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.remark_et)
    EditText remark_et;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.tkt_history)
    RecyclerView tktHistoryRv;
    TicketHistoryAdapter ticketsAdapter;
    ArrayList<TicketHistoryResponse> tktHistoryList = new ArrayList<>();

    SharedPreferences myPref;
    String empCode,ticketNo,Remarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Ticket History");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id","");
        ticketsAdapter = new TicketHistoryAdapter(TicketDetail.this,tktHistoryList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(TicketDetail.this, LinearLayoutManager.VERTICAL, false);
        tktHistoryRv.setLayoutManager(mLayoutManager);
        tktHistoryRv.setAdapter(ticketsAdapter);

        if(getIntent()!=null && getIntent().getStringExtra("ticketno")!=null){
            ticketNo = getIntent().getStringExtra("ticketno");
            getTicketHistory();
        }

    }

    @OnClick(R.id.update)
    public void onClickupdateRemark(){
        if(Utility.isOnline(TicketDetail.this)){
            updateRemark();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void updateRemark(){

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.updateRemark(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    Toast.makeText(TicketDetail.this, "Remarks Updated", Toast.LENGTH_LONG).show();
                    remark_et.setText("");
                    getTicketHistory();
                }
            }
        },remark_et.getText().toString(),empCode,ticketNo);


    }


    public void getTicketHistory(){
        tktHistoryList.clear();
        tktHistoryRv.getRecycledViewPool().clear();
        ticketsAdapter.notifyDataSetChanged();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketHistory(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){

                    List<TicketHistoryResponse> list = (List<TicketHistoryResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        tktHistoryList.addAll(list);
                         }
                }
                ticketsAdapter.notifyDataSetChanged();
            }
        },ticketNo);
    }
}
