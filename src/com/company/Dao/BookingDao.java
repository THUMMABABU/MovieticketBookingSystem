package com.company.Dao;

import com.company.model.Bookings;

import java.sql.ResultSet;
import java.util.List;

public interface BookingDao {
    public boolean bookTickets(String userName, String contactIfo, int tickets, int seats);
    public boolean cancelTicket(int bookingId);
    List<Bookings> viewBookingTikets(int movieId);
    ResultSet getSeatsAvaialbility(int seats , String showtime);


}
