package com.company.Dao;

import com.company.model.Showtimes;

import java.util.Date;
import java.util.List;

public interface ShowtimesDao {
  Showtimes getShowTime(int movieId, String showtime);
  List<Showtimes>getShowTimeMovie(int movieId);
}
