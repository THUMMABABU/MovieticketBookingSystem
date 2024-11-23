package com.company.daoimp;

import com.company.dao.BookingDao;
import com.company.exception.CustomException;
import com.company.model.Bookings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingDaoImp implements BookingDao {

    private static final String BOOKING_TICKETS = "INSERT INTO bookings(showtime_id,movie_id,user_name,contact_info,tickets,seats) VALUES (?,?,?,?,?,?)";
    private static final String CANCEL_TICKET = "DELETE FROM bookings WHERE booking_id=?";
    private static final String VIEWED_BOOKING_TICKETS =
            "SELECT b.booking_id,m.movie_id,m.movie_name, s.showtime_id,s.showtime,\n" +
                    "b.seats,b.tickets,b.user_name,\n" +
                    "b.contact_info FROM (bookings b join movies m ON b.movie_id=m.movie_id\n" +
                    " JOIN showtimes s ON b.showtime_id=s.showtime_id) WHERE  m.movie_id=?";
    private static final Logger logger = Logger.getLogger(BookingDaoImp.class.getName()); // Logger initialization
    private final Connection connection;

    public BookingDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean bookTickets(int showtimeId, int movieId, String userName, String contactInfo, int tickets, int seats) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(BOOKING_TICKETS);
            preparedStatement.setInt(1, showtimeId);
            preparedStatement.setInt(2, movieId);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, contactInfo);
            preparedStatement.setInt(5, tickets);
            preparedStatement.setInt(6, seats);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                logger.log(Level.INFO, "Booking successful for user, MovieID,ShowtimeID, Tickets. Seats");
                return true;
            } else {
                logger.log(Level.WARNING, "Booking failed for user, Movie ID: {1}, Showtime ID: {2}",
                        new Object[]{userName, movieId, showtimeId});
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while booking tickets for user, MovieID,ShowtimeID");
           // logger.log(Level.SEVERE, "SQLException: " + e.getMessage(), e);
            throw new CustomException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean cancelTicket(int bookingId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_TICKET);
            preparedStatement.setInt(1, bookingId);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                logger.log(Level.INFO, "Cancellation successful for bookingID");
                return true;
            } else {
                logger.log(Level.WARNING, "Cancellation failed for booking ID:", bookingId);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while canceling booking ID: " + bookingId, e);
            throw new CustomException("Error occurred while canceling tickets: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Bookings> viewBookingTickets(int movieId) {
        List<Bookings> bookingsArrayList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(VIEWED_BOOKING_TICKETS);
            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                String userName = resultSet.getString("user_name");
                String contactInfo = resultSet.getString("contact_info");
                int tickets = resultSet.getInt("tickets");
                int seats = resultSet.getInt("seats");
                String movieName = resultSet.getString("movie_name");
                String showtime = resultSet.getString("showtime");

                Bookings bookings = new Bookings(bookingId, userName, contactInfo, tickets, seats);
                bookings.setMovieName(movieName);
                bookings.setShowTime(showtime);
                bookingsArrayList.add(bookings);

                // Log each booking found
                logger.log(Level.INFO, "Viewing booking,Movie, Showtime, User Tickets, Seats");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while viewing tickets for Movie ID: " + movieId, e);
            throw  new CustomException("Error occurred while viewBookingTickets:"+e.getMessage());
        }
        return bookingsArrayList;
    }
}
