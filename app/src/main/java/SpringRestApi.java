package com.example.book_inbeta1;

import android.widget.RelativeLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpringRestApi {

    //Room
    @GET("getRoom/{id}")
    Call<MeetingRoom> getsMeetingRoom(@Path("id") long id);

    @GET("getAll")
    Call<List<MeetingRoom>> getRooms();

    @POST("create/")
    Call<MeetingRoom> createMeetingRoom(@Body MeetingRoom meetingRoom);


    @DELETE("deleteRoom/{id}")
    Call<Void> deleteMeetingRoom(@Path("id") long id);

    //Resevation

    @GET("gReservation/{id_r}")
    Call<List<String>> getListDate(@Path("id_r") long id_r);

    @POST("cReservation/")
    Call<Reservation> makeReservation(@Body Reservation reservation);

    @DELETE("dReservation/{id}")
    Call<Void> cancelReservation(@Path("id") long id);
}
