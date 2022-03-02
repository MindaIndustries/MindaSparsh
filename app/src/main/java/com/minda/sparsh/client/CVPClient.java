package com.minda.sparsh.client;

import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.CustomerModel;
import com.minda.sparsh.model.LocationModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.model.YearModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CVPClient {

    @GET("GetWeeks")
    Call<WeekModel> getWeeks(@Query("date") String date);

    @GET("GetCustomer")
    Call<CustomerModel> getCustomers(@Query("Empcode") String Empcode);

    @GET("GetMeetingType")
    Call<MeetingTypeModel> getMeetingType();

    @GET("GetLocation")
    Call<LocationModel> getLocation(@Query("CustomerId") String CustomerId);

    @GET("GetCalenderPlanning")
    Call<CalendarTypeModel> getCalendarTypes();
    @FormUrlEncoded
    @POST("SaveCalenderBooking")
    Call<SaveCalendarResponse> saveCalendarBooking(@Field("WeekDaysId")int WeekDaysId, @Field("CustomerId") String CustomerId, @Field("CustLocationId") String CustLocationId, @Field("MeetingTypeId") int MeetingTypeId, @Field("Year") String Year, @Field("CreatedBy") String CreatedBy, @Field("CalenderType") int CalenderType);

    @GET("GetYear")
    Call<YearModel> getYear();

    @GET("GetCalenderWithEmpCode")
    Call<CVPViewCalendarModel> getMeetings(@Query("EmpCode") String EmpCode,@Query("Year")String Year,@Query("Month") String Month,@Query("CalenderType") String CalenderType );

}
