package com.minda.sparsh.cvp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.MOMActionAdapter;
import com.minda.sparsh.Adapter.MOMDiscussionAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CVPDetailModel;
import com.minda.sparsh.services.CVPServices;
import com.nativess.xmllayouttopdflibrary.PDFBuilder;
import com.nativess.xmllayouttopdflibrary.PDFGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMOMDetails extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mom_id)
    TextView mom_id;
    @BindView(R.id.customer_value)
    TextView customerValue;
    @BindView(R.id.date_value)
    TextView dateValue;
    @BindView(R.id.start_time_value)
    TextView startTimeValue;
    @BindView(R.id.end_time_value)
    TextView end_time_value;
    @BindView(R.id.agenda_value)
    TextView agenda_value;
    @BindView(R.id.location_value)
    TextView location_value;
    @BindView(R.id.customer_location_value)
    TextView customer_location_value;
    @BindView(R.id.uno_attendees)
    TextView uno_attendees;
    @BindView(R.id.customer_attendees_value)
    TextView customer_attendees_value;
    @BindView(R.id.action_header)
    TextView action_header;
    @BindView(R.id.horizontalView1)
    HorizontalScrollView horizontalView1;
    @BindView(R.id.discussions)
    RecyclerView discussions;
    @BindView(R.id.action_points)
    RecyclerView action_points;
    @BindView(R.id.tv_scroll_Approval_Details)
    TextView tv_scroll_Approval_Details;
    @BindView(R.id.download)
    ImageView download;
    String momId;
    MOMDiscussionAdapter momDiscussionAdapter;
    MOMActionAdapter momActionAdapter;
    ArrayList<CVPDetailModel.CVPDetailData.OverAllDiscussion> discussionsList = new ArrayList<>();
    ArrayList<CVPDetailModel.CVPDetailData.ActionPoint> actionList = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mom);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("MOM Details");

        if(getIntent().getStringExtra("mId")!=null) {
            momId = getIntent().getStringExtra("mId");
            getMomDetails(momId);
        }
        momDiscussionAdapter = new MOMDiscussionAdapter(ViewMOMDetails.this,discussionsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ViewMOMDetails.this, LinearLayoutManager.VERTICAL, false);
        discussions.setLayoutManager(mLayoutManager);
        discussions.setAdapter(momDiscussionAdapter);
        momActionAdapter = new MOMActionAdapter(ViewMOMDetails.this,actionList);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ViewMOMDetails.this, LinearLayoutManager.VERTICAL, false);
        action_points.setLayoutManager(mLayoutManager1);
        action_points.setAdapter(momActionAdapter);

        tv_scroll_Approval_Details.setOnClickListener(view -> horizontalView1.postDelayed(() -> horizontalView1.fullScroll(HorizontalScrollView.FOCUS_RIGHT), 100L));

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT== Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        //todo when permission is granted
                        createPdf();
                    } else {
                        //request for the permission
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }
                else{
                    createPdf();
                }
            }
        });
    }

    public void getMomDetails(String momId){
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCvpDetail(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    CVPDetailModel cvpDetailModel = (CVPDetailModel) carotResponse.getData();
                    if(cvpDetailModel!=null){
                        CVPDetailModel.CVPDetailData cvpDetailData = cvpDetailModel.getData();
                        if(cvpDetailData!=null){
                            if(cvpDetailData.getCustomerMomId()!=null){
                                mom_id.setText("MOM - "+cvpDetailData.getCustomerMomId());
                            }
                            if(cvpDetailData.getCustomer()!=null) {
                                customerValue.setText(cvpDetailData.getCustomer());
                            }
                            if(cvpDetailData.getDateOfVisit()!=null){
                                dateValue.setText(cvpDetailData.getDateOfVisit());
                            }
                            if(cvpDetailData.getStartTime()!=null){
                                startTimeValue.setText(cvpDetailData.getStartTime());
                            }
                            if(cvpDetailData.getEndTime()!=null){
                                end_time_value.setText(cvpDetailData.getEndTime());
                            }
                            if(cvpDetailData.getAgenda()!=null){
                                agenda_value.setText(cvpDetailData.getAgenda());
                            }
                            if(cvpDetailData.getLocation()!=null){
                                location_value.setText(cvpDetailData.getLocation().trim());
                            }
                            if(cvpDetailData.getCustLocation()!=null){
                                customer_location_value.setText(cvpDetailData.getCustLocation().trim());
                            }
                            if(cvpDetailData.getInternalCustomer()!=null){
                                uno_attendees.setText(cvpDetailData.getInternalCustomer());
                            }
                            if(cvpDetailData.getExternalCustomer()!=null){
                                customer_attendees_value.setText(cvpDetailData.getExternalCustomer());
                            }

                            if(cvpDetailData.getOverAllDisussion()!=null){
                                List<CVPDetailModel.CVPDetailData.OverAllDiscussion> overall = cvpDetailData.getOverAllDisussion();
                                if(overall!=null && overall.size()>0){
                                    discussionsList.addAll(overall);
                                    momDiscussionAdapter.notifyDataSetChanged();
                                }

                            }
                            if(cvpDetailData.getActionPoint()!=null){
                                List<CVPDetailModel.CVPDetailData.ActionPoint> actionpoints = cvpDetailData.getActionPoint();
                                if(actionpoints!=null && actionpoints.size()>0){
                                    actionList.addAll(actionpoints);
                                    momActionAdapter.notifyDataSetChanged();
                                    action_header.setVisibility(View.VISIBLE);
                                    horizontalView1.setVisibility(View.VISIBLE);
                                    tv_scroll_Approval_Details.setVisibility(View.VISIBLE);
                                }
                                else{
                                    action_header.setVisibility(View.GONE);
                                    horizontalView1.setVisibility(View.GONE);
                                    tv_scroll_Approval_Details.setVisibility(View.GONE);
                                }
                                }
                                else{
                                    action_header.setVisibility(View.GONE);
                                    horizontalView1.setVisibility(View.GONE);
                                tv_scroll_Approval_Details.setVisibility(View.GONE);
                            }
                            }
                            }
                   // createPdf();


                }

            }
        }, momId);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createPdf(){
        RelativeLayout view = findViewById(R.id.main);

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bm = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        view.draw(canvas);

        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(view.getWidth(), view.getHeight(), 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        page.getCanvas().drawBitmap(bm,0F,0F,null);
        document.finishPage(page);
        File filePath = new File(Environment.getExternalStorageDirectory(),
                mom_id.getText().toString() + ".pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

    }


/*
    private void createPdf(){
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(100, 100, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

 Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(50, 50, 30, paint);

        // finish the page
        document.finishPage(page);

        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(500, 500, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, 100, paint);


        Canvas pageCanvas = page.getCanvas();
        pageCanvas.scale(1f, 1f);
        int pageWidth = pageCanvas.getWidth();
        int pageHeight = pageCanvas.getHeight();
        int measureWidth = View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(pageHeight, View.MeasureSpec.EXACTLY);
        View contentView = findViewById(R.id.main);
        contentView.measure(measureWidth, measuredHeight);
        contentView.layout(0, 0, pageWidth, pageHeight);
        contentView.draw(pageCanvas);


        document.finishPage(page);

        // write the document content
      //  String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }
*/
}
