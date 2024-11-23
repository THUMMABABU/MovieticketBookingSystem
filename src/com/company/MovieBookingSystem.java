package com.company;

import com.company.daoimp.BookingDaoImp;
import com.company.daoimp.MoviesDaoImp;
import com.company.daoimp.ShowtimeDaoImp;
import com.company.model.Bookings;
import com.company.model.Movies;
import com.company.model.ShowTimes;
import com.company.service.MovieService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class MovieBookingSystem {
    private static final Scanner sc = new Scanner(System.in);
    private final MovieService movieService;

    public MovieBookingSystem(Connection connection) {
        this.movieService = new MovieService(
                new BookingDaoImp(connection),
                new MoviesDaoImp(connection),
                new ShowtimeDaoImp(connection)
        );
    }

    public static void main(String[] args) {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/movieticketbooking";
        String userName = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        MovieBookingSystem movieBookingSystem = new MovieBookingSystem(connection);
        movieBookingSystem.systemStart();
    }

    public void systemStart() {
        System.out.println("Welcome to Online Movie Booking System.. happy to book the tickets..");
        while (true) {
            System.out.println("*".repeat(20));
            System.out.println("1 -> All movies");
            System.out.println("2 -> Book a Ticket");
            System.out.println("3 -> Cancel a ticket");
            System.out.println("4 -> View Booked Tickets");
            System.out.println("5 -> Available Seats Check");
            System.out.println("6 -> Exit System");
            System.out.println("Please choose one from above options");
            System.out.println("*".repeat(20));
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    movieDetails();
                    break;
                case 2:
                    bookTickets();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    viewBookedTickets();
                    break;
                case 5:
                    availableSeatsBasedOnTime();
                    break;
                case 6:
                    exitSystem();
                    return;
                default:
                    System.out.println("Please provide a valid input...");
            }
        }
    }

    private void availableSeatsBasedOnTime() {
        System.out.println("Enter showtimeId:");
        int timeId = sc.nextInt();
        System.out.println("Enter number of seats:");
        int seats = sc.nextInt();

        boolean successful = movieService.checkSeatAvailability(timeId, seats);
        if (!successful) {
            System.out.println("Selected seats are unavailable.");
        } else {
            System.out.println("Selected seats are available.");
        }
    }

    private void movieDetails() {
        List<Movies> movies = movieService.getAllMovies();
        for (Movies movie : movies) {
            System.out.println(movie.getMovieName());
        }
    }

    private void bookTickets() {
        System.out.println("Please enter the Movie Name: ");
        String movieName = sc.nextLine();
        Movies movie = movieService.getMovieByName(movieName);
        if (movie == null) {
            System.out.println("Invalid movie name. Please try again.");
            return;
        }
        System.out.println("Movie is available: " + movieName + " showtime:");
        List<ShowTimes> showTimes = movieService.getShowTimesForMovie(movie.getMovieId());
        if (showTimes.isEmpty()) {
            System.out.println("No showtime available for this movie.");
            return;
        }

        for (ShowTimes showtime : showTimes) {
            System.out.println("Showtime: " + showtime.getShowTime());
        }

        System.out.println("Enter the showtime (HH:MM:SS): ");
        String timeStr = sc.nextLine();
        Time time = Time.valueOf(timeStr);
        ShowTimes showtime = movieService.getShowtimeByTime(movie.getMovieId(), time);

        if (showtime == null) {
            System.out.println("Showtime not available.");
            return;
        }

        System.out.println("Enter your username: ");
        String userName = sc.nextLine();
        System.out.println("Enter your contact information: ");
        String contactInfo = sc.nextLine();
        System.out.println("Enter the number of tickets: ");
        int tickets = sc.nextInt();
        System.out.println("Enter the number of seats: ");
        int seats = sc.nextInt();

        boolean seatsAvailable = movieService.checkSeatAvailability(showtime.getShowTimeId(), seats);
        boolean success = movieService.bookTickets(showtime.getShowTimeId(), movie.getMovieId(), userName, contactInfo, tickets, seats);

        if (seatsAvailable && success) {
            System.out.println("Tickets booked successfully.");
        } else {
            System.out.println("Selected seats are unavailable. Please try again.");
        }
    }

    private void cancelTicket() {
        System.out.println("Please provide the Booking ID: ");
        int bookingId = sc.nextInt();
        boolean success = movieService.cancelBooking(bookingId);
        if (success) {
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Invalid Booking ID. Please try again.");
        }
    }

    private void viewBookedTickets() {
        System.out.println("Enter the movie ID: ");
        int movieId = sc.nextInt();
        List<Bookings> bookingsList = movieService.viewBookings(movieId);
        if (bookingsList.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            for (Bookings booking : bookingsList) {
                System.out.printf("|Booking ID: %-5d| Movie: %-7s | Showtime: %-7s | Seats: %-3d | Tickets: %-3d | User: %-8s | Contact Info: %-9s|\n",
                        booking.getBookingId(),
                        booking.getMovieName(),
                        booking.getShowTime(),
                        booking.getSeats(),
                        booking.getTickets(),
                        booking.getUserName(),
                        booking.getContactInfo());
            }
        }
    }

    private void exitSystem() {
        System.out.println("Exiting the application.");
    }


}