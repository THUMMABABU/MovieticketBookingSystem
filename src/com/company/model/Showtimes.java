package com.company.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Showtimes {
    public  int showtimeId ;
    public int movieId;
    public String  showtime;

    public Showtimes(int showtimeId, int movieId, String showtime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.showtime = showtime;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

}
