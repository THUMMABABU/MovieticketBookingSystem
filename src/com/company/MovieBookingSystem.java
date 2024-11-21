package com.company;

import com.company.Dao.BookingDao;
import com.company.Dao.MoviesDao;
import com.company.Dao.ShowtimesDao;
import com.company.DaoImp.BookingDaoImp;
import com.company.DaoImp.MoviesdaoImp;
import com.company.DaoImp.ShowtimeDaoImp;
import com.company.model.Bookings;
import com.company.model.Movies;
import com.company.model.Showtimes;

import java.sql.*;
import java.util.*;

public class MovieBookingSystem {
  private static Scanner sc=new Scanner(System.in);
    private BookingDao bookingDao;
    private MoviesDao moviesDao;
    private ShowtimesDao showtimesDao;

    public MovieBookingSystem(Connection connection) {
        this.bookingDao =new BookingDaoImp(connection);
        this.moviesDao = new MoviesdaoImp(connection);
        this.showtimesDao = new ShowtimeDaoImp(connection);
    }
    public void SystemStart(){
        System.out.println("welcome to Online Movie Booking System..happy to book the Tickets..");
        while(true){
            System.out.println("*".repeat(160));
            System.out.println("1,All movies: ");
        System.out.println("2,Book a Ticket: ");
        System.out.println("3,Cancel a ticket: ");
        System.out.println("4,ViewedBookedTickets: ");
        System.out.println("5,ExitSystem:");
        System.out.println("please  Choose Above 4 Options");

        int chose =sc.nextInt();
        sc.nextLine();

            switch (chose){
                case 1:
                    movieDetails();
                case 2:
                    bookTickets();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    viewBookedTicket();
                    break;
                case 5:
                    ExitSystem();
                    return;
                default:
                    System.out.println("please Provide valid Input...");
            }
            sc.nextLine();
        }
    }
    private void movieDetails() {
        List<Movies> movies=moviesDao.getAllMovies();
        for(Movies movies1:movies){
            System.out.println(movies1.getMovieName());
        }
    }
    private void bookTickets() {
        System.out.println("Please enter the MovieName: ");
        String movieName=sc.next();
        Movies movies=moviesDao.getMovieByName(movieName.toLowerCase());
        if(movies==null){
            System.out.println("Ivalid movie title or ticket count.please try again.");
            return ;
        }
        System.out.println("movie is Available "+movieName+"showTimes: ");
        List<Showtimes>showtimes=showtimesDao.getShowTimeMovie(movies.getMovieId());
        if(showtimes.isEmpty()){
            System.out.println("ShowTime Not Available in this Movie");
            return;
        }
        for(Showtimes showtimes1:showtimes){
            System.out.println("ShowTimings: - "+showtimes1.getShowtime());
        }

        System.out.println("enter the showtime: ");
        String showtime =sc.next();

        Showtimes showtimes2=showtimesDao.getShowTime(movies.getMovieId(),showtime);

        if(showtimes2==null){
            System.out.println("showTime Not Available...");
            return ;
        }
        // booking  tickets like insert Query
        System.out.println("enter the  userName: ");
        String userName=sc.next();
        System.out.println("enter the  contactInfo: ");
        String contactInfo=sc.next();
        System.out.println("enter the no of tickets: ");
        int tickets=sc.nextInt();
        System.out.println("enter the  seats: ");
        int seats=sc.nextInt();
         ResultSet seatsAvaialbility1=bookingDao.getSeatsAvaialbility(seats,showtimes2.getShowtime());
         if(seatsAvaialbility1==null){
             System.out.println("selected seats are unvailable .please choose again");
         }
         boolean successBooking=bookingDao.bookTickets(userName,contactInfo,tickets,seats);
         if(successBooking ){
             System.out.println("Tickets booked successfully.");
         }else{
             System.out.println("selected seats are unvailable .please choose again");
         }
    }
    private void cancelTicket() {
        System.out.println("please given the BookingId ");
        int bookingId=sc.nextInt();
        boolean Success=bookingDao.cancelTicket(bookingId);
        if(Success){
            System.out.println("Booking Cancelled successfully.");
        }
        else {
            System.out.println("Invalid Booking Id.Please try again.");
        }

    }
    private void viewBookedTicket() {
        System.out.println("enter the movieId: ");
        int movieId=sc.nextInt();
        List<Bookings> bookingsList=bookingDao.viewBookingTikets(movieId);
        if(bookingsList.isEmpty()){
            System.out.println("No bookings available..");
        }
        for (Bookings bookings:bookingsList){
            System.out.printf("|bookingID: %-2d|MovieTitle:%-5s |showtimes: %-7s|seats: %-2s|tickets:%-2d"+
                            "|userName: %-5s |contact_info: %-3s|",bookings.getBookingId(),
                    bookings.getMovieName(),bookings.getShowTime()
                    ,bookings.getSeats()
                    ,bookings.getTickets()
                    ,bookings.getUsername()
                    ,bookings.getContactInfo());
        }
        System.out.println();
    }
    private void ExitSystem() {
        System.out.println("Exit The Application");
    }
    public static void main(String[] args) {
        Connection connection=null;
       String url="jdbc:mysql://localhost:3306/movieticketbooking";
       String userName="root";
       String password="root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection(url,userName,password);         ;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
   MovieBookingSystem movieBookingSystem=new MovieBookingSystem(connection);
        movieBookingSystem.SystemStart();
    }
}
