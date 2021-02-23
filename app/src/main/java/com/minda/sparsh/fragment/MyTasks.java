package com.minda.sparsh.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minda.sparsh.Adapter.TicketsAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.MyTicketsResponse;
import com.minda.sparsh.services.ITHelpDeskServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class MyTasks extends Fragment {
    @BindView(R.id.my_tickets)
    RecyclerView myTickets;
    TicketsAdapter ticketsAdapter;
    List<MyTicketsResponse> myTicketsResponse = new ArrayList<>();
    String empCode;
    SharedPreferences myPref;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_list)
    TextView no_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myTicketsview = inflater.inflate(R.layout.my_tickets, container, false);
        ButterKnife.bind(this, myTicketsview);
        ticketsAdapter = new TicketsAdapter(getActivity(),myTicketsResponse);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myTickets.setLayoutManager(mLayoutManager);
        myTickets.setAdapter(ticketsAdapter);
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        if(Utility.isOnline(getActivity())) {
            getMyTaskTickets(empCode, "", "", "", "", "", "0", "0", "0", "", "");
        }
        return myTicketsview;
    }


    public void getMyTaskTickets(String EmpCode, String Location, String TicketTypeId, String Priority, String TicketGroupId, String StatusId, String SubCat, String SubCat2, String SubCat3, String ReportedDate, String CloserDate){
        myTicketsResponse.clear();
        myTickets.getRecycledViewPool().clear();
        ticketsAdapter.notifyDataSetChanged();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getMyTaskTickets(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<MyTicketsResponse> list = (List<MyTicketsResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0) {
                        myTicketsResponse.addAll(list);
                        no_list.setVisibility(View.GONE);
                    }
                    else{
                        no_list.setVisibility(View.VISIBLE);
                    }
                    ticketsAdapter.notifyDataSetChanged();
                }

            }
        },EmpCode,Location,TicketTypeId,Priority,TicketGroupId,StatusId,SubCat,SubCat2,SubCat3,ReportedDate,CloserDate);
    }

}
