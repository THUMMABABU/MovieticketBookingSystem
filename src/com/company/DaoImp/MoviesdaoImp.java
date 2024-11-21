package com.company.DaoImp;

import com.company.Dao.MoviesDao;
import com.company.model.Movies;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesdaoImp implements MoviesDao {
    private Connection connection;
    private static final String GET_MOVIE_QUERY="Select * from movies where movie_name=?";
    private  static final String GET_ALL_MOVIE="select * from movies";
    public MoviesdaoImp(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Movies getMovieByName(String movieName) {
        Movies movies=null;
        try {
            PreparedStatement preparedStatement =connection.prepareStatement(GET_MOVIE_QUERY);
            preparedStatement.setString(1,movieName);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()) {
                 movies= new Movies(resultSet.getInt("movie_id"),
                         resultSet.getString("movie_name"),
                         resultSet.getInt("available_tickets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

   /* @Override
    public Movies getMovieById(int movieId) {
        PreparedStatement preparedStatement=null;
        Movies movies=null;
        try {
             preparedStatement =connection.prepareStatement(GET_MOVIE_BY_ID);
            preparedStatement.setInt(1,movieId);
            ResultSet resultSet=preparedStatement.executeQuery();
           if(resultSet.next()){
                movies=new Movies(resultSet.getInt("movie_name"),
                                        resultSet.getString("movie_name"),
                                        resultSet.getInt("available_tickets"));
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    */

    @Override
    public List<Movies> getAllMovies() {
        ArrayList<Movies> moviesArrayList=new ArrayList<>();
        try {
            Statement  statement=connection.createStatement();
           ResultSet resultSet=statement.executeQuery(GET_ALL_MOVIE);
           while(resultSet.next()){
               Movies movies=new Movies(resultSet.getInt("movie_id"), resultSet.getString("movie_name"),resultSet.getInt("available_tickets") );

             moviesArrayList.add(movies);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moviesArrayList;
    }

}

