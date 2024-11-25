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
    private static final String DECREASE_AVAILABLE_TICKETS = "UPDATE movies SET available_tickets = available_tickets - ? WHERE movie_id = ?";
    private static final String INCREASE_AVAILABLE_TICKETS = "UPDATE movies SET available_tickets = available_tickets + ? WHERE movie_id = ?";
    private static final String GET_BOOKING_DETAILS = "SELECT tickets, movie_id FROM bookings WHERE booking_id=?";
    private static final Logger logger = Logger.getLogger(BookingDaoImp.class.getName()); // Logger initialization
    private final Connection connection;

    public BookingDaoImp(Connection connection) {
        this.connection = connection;
    }


    public boolean bookTickets(int showtimeId, int movieId, String userName, String contactInfo, int tickets, int seats) {
        try (
                PreparedStatement bookingStmt = connection.prepareStatement(BOOKING_TICKETS);
                PreparedStatement updateTicketsStmt = connection.prepareStatement(DECREASE_AVAILABLE_TICKETS)
        ) {
            // Insert booking record
            bookingStmt.setInt(1, showtimeId);
            bookingStmt.setInt(2, movieId);
            bookingStmt.setString(3, userName);
            bookingStmt.setString(4, contactInfo);
            bookingStmt.setInt(5, tickets);
            bookingStmt.setInt(6, seats);

            if (bookingStmt.executeUpdate() > 0) {
                // Decrease available tickets
                updateTicketsStmt.setInt(1, tickets);
                updateTicketsStmt.setInt(2, movieId);
                return updateTicketsStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while booking tickets.", e);
            throw new CustomException("Booking failed: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean cancelTicket(int bookingId) {
        try (
                PreparedStatement getBookingDetailsStmt = connection.prepareStatement(GET_BOOKING_DETAILS);
                PreparedStatement cancelStmt = connection.prepareStatement(CANCEL_TICKET);
                PreparedStatement updateTicketsStmt = connection.prepareStatement(INCREASE_AVAILABLE_TICKETS)
        ) {

            getBookingDetailsStmt.setInt(1, bookingId);
            ResultSet resultSet = getBookingDetailsStmt.executeQuery();

            if (resultSet.next()) {
                int tickets = resultSet.getInt("tickets");
                int movieId = resultSet.getInt("movie_id");


                cancelStmt.setInt(1, bookingId);
                if (cancelStmt.executeUpdate() > 0) {

                    updateTicketsStmt.setInt(1, tickets);
                    updateTicketsStmt.setInt(2, movieId);
                    return updateTicketsStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while canceling booking.", e);
            throw new CustomException("Cancellation failed: " + e.getMessage());
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


                logger.log(Level.INFO, "Viewing booking,Movie, Showtime, User Tickets, Seats");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while viewing tickets for Movie ID: " + movieId, e);
            throw new CustomException("Error occurred while viewBookingTickets:" + e.getMessage());
        }
        return bookingsArrayList;
    }
}


