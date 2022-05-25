package com.minda.sparsh.client;

import com.minda.sparsh.model.AutoNameModel;
import com.minda.sparsh.model.CityModel;
import com.minda.sparsh.model.MeetingBookResponse;
import com.minda.sparsh.model.MeetingRoomDetailData;
import com.minda.sparsh.model.MeetingRoomListModel;
import com.minda.sparsh.model.MeetingRoomModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MeetingRoomClient {

    @GET("MeetingRoom/UnitCity")
    Call<CityModel> getCity(@Query("EmpCode") String EmpCode);

    @GET("MeetingRoom/GetUnitByCity")
    Call<MeetingRoomModel> getUnitByCity(@Query("city") String city);

    @GET("MeetingRoom/GetMeetingRoom")
    Call<MeetingRoomListModel> getMeetingRooms(@Query("EmpCode") String EmpCode, @Query("RoomType") boolean RoomType, @Query("UnitCode") String UnitCode);

    @GET("MeetingRoom/GetBookingSlot")
    Call<MeetingRoomDetailData> getMeetingRoomDetail(@Query("EmpCode") String EmpCode, @Query("ActType") String ActType,@Query("BookingDate") String BookingDate,@Query("MeetingRoomID") int MeetingRoomID);

    @FormUrlEncoded
    @POST("MeetingRoom/BookMeetingRoom")
    Call<MeetingBookResponse> bookMeetingRoom(@Field("MeetingRoomSlotID") String MeetingRoomSlotID, @Field("MeetingRoomID") int MeetingRoomID, @Field("EmpCode") String EmpCode, @Field("BookingDate") String BookingDate, @Field("Purpose") String Purpose, @Field("AttendeeInt") String AttendeeInt, @Field("AttendeeExt") String AttendeeExt, @Field("ActType") String ActType, @Field("MeetingID") String MeetingID, @Field("UnitCode") String UnitCode, @Field("RoomType") boolean RoomType);

    @GET("MeetingRoom/GetAutoName")
    Call<AutoNameModel> getAutoName(@Query("prefixText") String prefixText);
}
