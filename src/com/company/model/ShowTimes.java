package com.company.model;

import java.sql.Time;

public class ShowTimes {
    public int showTimeId;
    public int movieId;
    public Time showTime;

    public ShowTimes(int showtimeId, int movieId, Time showtime) {
        this.showTimeId = showtimeId;
        this.movieId = movieId;
        this.showTime = showtime;
    }

    public int getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

}
