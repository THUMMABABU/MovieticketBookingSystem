package com.company.dao;

import com.company.model.ShowTimes;

import java.sql.Time;
import java.util.List;

public interface ShowTimesDao {
    ShowTimes getShowTime(int movieId, Time showtime);

    List<ShowTimes> getShowTimeMovie(int movieId);

    boolean getSeatsAvailability(int seats, int showtimeId);

}
