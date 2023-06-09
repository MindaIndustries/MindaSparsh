package com.minda.sparsh.client;

import com.minda.sparsh.model.CVPDetailModel;
import com.minda.sparsh.model.CVPEmpNameModel;
import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.CustomerModel;
import com.minda.sparsh.model.EditCalendarModel;
import com.minda.sparsh.model.LocationModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekByCustomerModel;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.model.YearModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CVPClient {

    @FormUrlEncoded
    @POST("QrCode/UpdateRandomNumber")
    Call<String> updateRandomNumber(@Field("EmpCode") String EmpCode, @Field("RandomNumber") String RandomNumber);

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
    Call<SaveCalendarResponse> saveCalendarBooking(@Field("WeekDaysId")int WeekDaysId, @Field("CustomerId") String CustomerId, @Field("CustLocationId") String CustLocationId, @Field("MeetingTypeId") int MeetingTypeId, @Field("Year") String Year, @Field("CreatedBy") String CreatedBy, @Field("CalenderType") int CalenderType, @Field("CalendarBookingDate") String CalendarBookingDate);

    @GET("GetYear")
    Call<YearModel> getYear();

    @GET("GetCalenderWithEmpCode")
    Call<CVPViewCalendarModel> getMeetings(@Query("EmpCode") String EmpCode,@Query("Year")String Year,@Query("CalenderType") String CalenderType,@Query("WeekId") String WeekId );

    @GET("EditCalenderDetail")
    Call<EditCalendarModel> editCalendar(@Query("Id") String Id, @Query("EmpCode") String EmpCode);

    @FormUrlEncoded
    @POST("DeleleCalenderBooking")
    Call<SaveCalendarResponse> deleteCalendarBooking(@Field("Id") String id, @Field("EmpCode") String EmpCode, @Field("CalenderType") String CalenderType );

    @FormUrlEncoded
    @PUT("UpdateCalenderBooking")
    Call<SaveCalendarResponse> updateCalendarBooking(@Field("Id") int Id, @Field("WeekDaysId") int weekDayId, @Field("EmpCode") String EmpCode,@Field("CustLocationId") String CustLocationId,@Field("MeetingTypeId") String MeetingTypeId, @Field("CalendarBookingDate") String CalendarBookingDate);

    @GET("GetWeekByCustomer")
    Call<WeekByCustomerModel> getWeekByCustomer(@Query("CustomerId") String CustomerId, @Query("EmpCode") String EmpCode);

    @GET("GetCvpDetailsReport")
    Call<CVPDetailModel> getCvpDetail(@Query("MomId") String MomId);

    @GET("GetEmployeeName")
    Call<CVPEmpNameModel> getEmpName(@Query("Empcode") String empcode);

}
