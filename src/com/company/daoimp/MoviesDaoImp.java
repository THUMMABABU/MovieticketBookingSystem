package com.company.daoimp;

import com.company.dao.MoviesDao;
import com.company.exception.CustomException;
import com.company.model.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoviesDaoImp implements MoviesDao {

    private static final String GET_MOVIE_QUERY = "SELECT * FROM movies WHERE movie_name=?";
    private static final String GET_ALL_MOVIE = "SELECT * FROM movies";
    // Logger initialization
    private static final Logger logger = Logger.getLogger(MoviesDaoImp.class.getName());
    private final Connection connection;

    public MoviesDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Movies getMovieByName(String movieName) {
        Movies movie = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_MOVIE_QUERY);
            preparedStatement.setString(1, movieName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                movie = new Movies(resultSet.getInt("movie_id"),
                        resultSet.getString("movie_name"),
                        resultSet.getInt("available_tickets"));
                // Log successful retrieval of the movie
                logger.log(Level.INFO, "Successfully retrieved movie:", movieName);
            } else {
                // Log when no movie is found
                logger.log(Level.WARNING, "No movie found with name:", movieName);
            }
        } catch (SQLException e) {
            // Log error and exception details
            logger.log(Level.SEVERE, "Error occurred while retrieving movie by name: " + movieName, e);
            throw new CustomException("Error occurred while retrieving movie by name");
        }
        return movie;
    }

    @Override
    public List<Movies> getAllMovies() {
        List<Movies> moviesArrayList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_MOVIE);

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String movieName = resultSet.getString("movie_name");
                int availableTickets = resultSet.getInt("available_tickets");

                Movies movie = new Movies(movieId, movieName, availableTickets);
                moviesArrayList.add(movie);

                // Log each movie retrieved
                logger.log(Level.INFO, "Successfully retrieved movie: ", movieName);
            }
        } catch (SQLException e) {
            // Log error and exception details
            logger.log(Level.SEVERE, "Error occurred while retrieving all movies", e);
            throw new CustomException("Error occurred while retrieving all movies");

        }
        return moviesArrayList;
    }

}
