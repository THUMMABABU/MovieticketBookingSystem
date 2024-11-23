package com.company.model;

public class Movies {
    public int movieId;
    public String movieName;
    public int availableTickets;

    public Movies(int movieId, String movieName, int availableTickets) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.availableTickets = availableTickets;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String toString() {
        return "movieId: " + movieId + "movieName: " + movieName + "availableTickets: " + availableTickets;
    }
}
