package com.company.dao;

import com.company.model.Movies;

import java.util.List;

public interface MoviesDao {
    Movies getMovieByName(String movieName);

    List<Movies> getAllMovies();



}
