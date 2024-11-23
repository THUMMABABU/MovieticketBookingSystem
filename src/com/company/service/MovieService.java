package com.company.service;

import com.company.dao.BookingDao;
import com.company.dao.MoviesDao;
import com.company.dao.ShowTimesDao;
import com.company.model.Bookings;
import com.company.model.Movies;
import com.company.model.ShowTimes;

import java.sql.Time;
import java.util.List;

public class MovieService {
    private final BookingDao bookingDao;
    private final MoviesDao moviesDao;
    private final ShowTimesDao showTimesDao;

    public MovieService(BookingDao bookingDao, MoviesDao moviesDao, ShowTimesDao showTimesDao) {
        this.bookingDao = bookingDao;
        this.moviesDao = moviesDao;
        this.showTimesDao = showTimesDao;
    }

    public List<Movies> getAllMovies() {
        return moviesDao.getAllMovies();
    }

    public Movies getMovieByName(String movieName) {
        return moviesDao.getMovieByName(movieName.toLowerCase());
    }

    public List<ShowTimes> getShowTimesForMovie(int movieId) {
        return showTimesDao.getShowTimeMovie(movieId);
    }

    public boolean checkSeatAvailability(int showtimeId, int seatsRequired) {
        return showTimesDao.getSeatsAvailability(seatsRequired, showtimeId);
    }

    public boolean bookTickets(int showtimeId, int movieId, String userName, String contactInfo, int tickets, int seats) {
        return bookingDao.bookTickets(showtimeId, movieId, userName, contactInfo, tickets, seats);
    }

    public boolean cancelBooking(int bookingId) {
        return bookingDao.cancelTicket(bookingId);


    }

    public List<Bookings> viewBookings(int movieId) {
        return bookingDao.viewBookingTickets(movieId);
    }

    public ShowTimes getShowtimeByTime(int movieId, Time showTime) {
        return showTimesDao.getShowTime(movieId, showTime);
    }


}