package com.company.dao;

import com.company.model.Bookings;

import java.util.List;

public interface BookingDao {
    boolean bookTickets(int showtimeId, int movieId, String userName, String contactInfo, int tickets, int seats);

    boolean cancelTicket(int bookingId);

    List<Bookings> viewBookingTickets(int movieId);
}
