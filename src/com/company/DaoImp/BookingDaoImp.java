package com.company.DaoImp;

import com.company.Dao.BookingDao;
import com.company.model.Bookings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImp implements BookingDao {
private Connection connection;
private  static final String BOOKING_TICKETS="INSERT INTO bookings (user_name,contact_info,tickets,seats) values (?,?,?,?)";
private static  final String CANCEL_TICKET="DELETE FROM bookings where booking_id=?";
private  static  final String VIEWED_BOOKING_TICKETS="select b.booking_id,m.movie_id,m.movie_name, s.showtime_id,s.showtime,\n" +
        "b.seats,b.tickets,b.user_name,\n" +
        "b.contact_info from ( bookings b join movies m   on b.movie_id=m.movie_id\n" +
        " join  showtimes s on b.showtime_id=s.showtime_id ) where  m.movie_id=?";
    private  static  final String SEATAVAILABULIT_QUERY="select  seats from bookings where showtime_id=? and seats=?";
    public BookingDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean bookTickets( String userName, String contactIfo, int tickets, int seats) {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(BOOKING_TICKETS);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,contactIfo);
            preparedStatement.setInt(3,tickets);
            preparedStatement.setInt(4,seats);
           int result= preparedStatement.executeUpdate();
           return result>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean cancelTicket(int bookingId) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(CANCEL_TICKET);
            preparedStatement.setInt(1,bookingId);
            int result=preparedStatement.executeUpdate();
         return result>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Bookings> viewBookingTikets(int movieId) {
        List<Bookings> bookingsArrayList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(VIEWED_BOOKING_TICKETS);
            preparedStatement.setInt(1,movieId);
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                Bookings bookings =new Bookings(resultSet.getInt("booking_id"),
                                               resultSet.getString("user_name"),
                                               resultSet.getString("contact_info"),
                                               resultSet.getInt("tickets"),
                                               resultSet.getInt("seats"));
                bookings.setMovieName(resultSet.getString("movie_name"));
                bookings.setShowTime(resultSet.getString("showtime"));
                bookingsArrayList.add(bookings);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsArrayList;
    }
    @Override
    public ResultSet getSeatsAvaialbility(int seats , String showtime) {
       ResultSet booking = null;
        PreparedStatement  preparedStatement=null;
        try {
            preparedStatement   = connection.prepareStatement(SEATAVAILABULIT_QUERY);
           preparedStatement.setInt(1,seats);
            preparedStatement.setString(2,showtime);
               booking= preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }
}