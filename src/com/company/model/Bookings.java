package com.company.model;

public class Bookings {
    public int bookingId;
    public int showTimeId;
    public int movieId;
    public String userName;
    public String contactInfo;
    public int tickets;
    public int seats;
    public String movieName;
    public String showTime;

    public Bookings(int bookingId, String userName, String contactInfo, int tickets, int seats) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.contactInfo = contactInfo;
        this.tickets = tickets;
        this.seats = seats;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showtime) {
        this.showTime = showtime;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowtimeId() {
        return showTimeId;
    }

    public void setShowtimeId(int showtime) {
        this.showTimeId = showTimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
