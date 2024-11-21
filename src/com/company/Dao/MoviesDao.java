package com.company.Dao;

import com.company.model.Movies;

import java.util.List;

public interface MoviesDao {
  Movies getMovieByName(String movieName);
 // Movies getMovieById(int movieId);
  List<Movies>getAllMovies();

}
