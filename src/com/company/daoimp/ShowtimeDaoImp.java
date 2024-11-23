package com.company.daoimp;

import com.company.dao.ShowTimesDao;
import com.company.exception.CustomException;
import com.company.model.ShowTimes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowtimeDaoImp implements ShowTimesDao {

    private static final String GET_SHOWTIME_QUERY = "SELECT * FROM showtimes WHERE movie_id=? and showtime=? ";
    private static final String GET_SHOWTIME_MOVIE_QUERY = "SELECT * FROM showtimes WHERE movie_id=?";
    private static final String GET_SHOW_TIME_SEATS = "SELECT m.available_tickets FROM showtimes s JOIN movies m ON  s.movie_id=m.movie_id WHERE showtime_id=?";
    private final Connection connection;

    // Logger initialization
    private static final Logger logger = Logger.getLogger(ShowtimeDaoImp.class.getName());

    public ShowtimeDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ShowTimes getShowTime(int movieId, Time showtime) {
        ShowTimes showtimes = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_QUERY);
            preparedStatement.setInt(1, movieId);
            preparedStatement.setTime(2, showtime);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                showtimes = new ShowTimes(resultSet.getInt("showtime_id"), resultSet.getInt("movie_id"), resultSet.getTime("showtime"));
                // Log successful retrieval of showtime
                logger.log(Level.INFO, "Successfully retrieved showtime for Movie ID");
            } else {
                // Log when no showtime is found
                logger.log(Level.WARNING, "No showtime found for Movie ID");
            }
        } catch (SQLException e) {
            // Log the error with stack trace
            logger.log(Level.SEVERE, "Error occurred while retrieving showtime for Movie ID: " + movieId + ", Showtime: " + showtime, e);
            throw  new CustomException("Error occurred while retrieving showtime and MovieId:"+e.getMessage());
        }
        return showtimes;
    }

    @Override
    public List<ShowTimes> getShowTimeMovie(int movieId) {
        List<ShowTimes> showTimesArrayList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOWTIME_MOVIE_QUERY);
            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ShowTimes showtimes = new ShowTimes(resultSet.getInt("showtime_id"), resultSet.getInt("movie_id"), resultSet.getTime("showtime"));
                showTimesArrayList.add(showtimes);
                // Log each showtime retrieved
                logger.log(Level.INFO, "Successfully retrieved showtime for MovieID, ShowtimeID");
            }
        } catch (SQLException e) {
            // Log error while fetching showtimes for movie
            logger.log(Level.SEVERE, "Error occurred while retrieving showtime for Movie ID: " + movieId, e);
            throw  new CustomException("Error occurred while seat availability:"+e.getMessage());
        }
        return showTimesArrayList;
    }

    @Override
    public boolean getSeatsAvailability(int seats, int showtimeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SHOW_TIME_SEATS);
            preparedStatement.setInt(1, showtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int availableSeats = resultSet.getInt("available_tickets");
                boolean isAvailable = availableSeats >= seats;

                // Log availability check and result
                if (isAvailable) {
                    logger.log(Level.INFO, "Sufficient seats available. Requested, Available, ShowtimeID");
                } else {
                    logger.log(Level.WARNING, "Insufficient seats. Requested,Available,ShowtimeID" );
                }

                return isAvailable;
            }
        } catch (SQLException e) {
            // Log error while checking seat availability
            logger.log(Level.SEVERE, "Error occurred while checking seat availability for Showtime ID: " + showtimeId, e);
            throw  new CustomException("Error occurred while seat availability:"+e.getMessage());
        }
        return false;
    }
}
