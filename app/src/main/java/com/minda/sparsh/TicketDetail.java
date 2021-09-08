package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.CCListAdapter;
import com.minda.sparsh.Adapter.TicketHistoryAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.MyTicketsResponse;
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
    @BindView(R.id.cclist)
    RecyclerView cclist;
    TicketHistoryAdapter ticketsAdapter;
    ArrayList<TicketHistoryResponse> tktHistoryList = new ArrayList<>();
    ArrayList<String> recyclerview_list = new ArrayList<>();

    SharedPreferences myPref;
    String empCode, ticketNo;
    CCListAdapter ccListAdapter;
    MyTicketsResponse myTicket;
    @BindView(R.id.header)
    LinearLayout headerHistory;

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
        empCode = myPref.getString("Id", "");
        ticketsAdapter = new TicketHistoryAdapter(TicketDetail.this, tktHistoryList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(TicketDetail.this, LinearLayoutManager.VERTICAL, false);
        tktHistoryRv.setLayoutManager(mLayoutManager);
        tktHistoryRv.setAdapter(ticketsAdapter);
        ccListAdapter = new CCListAdapter(TicketDetail.this, recyclerview_list);
        final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(TicketDetail.this, LinearLayoutManager.VERTICAL, false);
        cclist.setLayoutManager(mLayoutManager1);
        cclist.setAdapter(ccListAdapter);

        if (getIntent() != null && getIntent().getSerializableExtra("ticketno") != null) {
            myTicket = (MyTicketsResponse) getIntent().getSerializableExtra("ticketno");


            if (myTicket != null && myTicket.getFiles() != null) {
                recyclerview_list.clear();
                String[] files = myTicket.getFiles().split(",");
                for (String file : files) {
                    recyclerview_list.add(file);
                }
                ccListAdapter.notifyDataSetChanged();

            }
        }

        if (getIntent() != null && getIntent().getSerializableExtra("ticketno") != null) {
            if (myTicket != null && myTicket.getFiles() != null) {
                ticketNo = myTicket.getTicketNo();
                getTicketHistory();
            }
        }

    }

    @OnClick(R.id.update)
    public void onClickupdateRemark() {
        if (Utility.isOnline(TicketDetail.this)) {
            if (remark_et.getText().toString().length() > 0) {
                updateRemark();
            } else {
                Toast.makeText(this, "Please enter remarks", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateRemark() {

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.updateRemark(carotResponse -> {

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(TicketDetail.this, "Remarks Updated", Toast.LENGTH_LONG).show();
                remark_et.setText("");
                getTicketHistory();
            }
        }, remark_et.getText().toString(), empCode, ticketNo);


    }


    public void getTicketHistory() {
        tktHistoryList.clear();
        tktHistoryRv.getRecycledViewPool().clear();
        ticketsAdapter.notifyDataSetChanged();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketHistory(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {

                List<TicketHistoryResponse> list = (List<TicketHistoryResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    tktHistoryList.addAll(list);
                }
                if (tktHistoryList.size() > 0) {
                    headerHistory.setVisibility(View.VISIBLE);
                } else {
                    headerHistory.setVisibility(View.GONE);
                }
            } else {
                headerHistory.setVisibility(View.GONE);


            }
            ticketsAdapter.notifyDataSetChanged();
        }, ticketNo);
    }
}
