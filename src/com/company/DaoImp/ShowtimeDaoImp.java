package com.company.DaoImp;

import com.company.Dao.ShowtimesDao;
import com.company.model.Showtimes;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ShowtimeDaoImp implements ShowtimesDao {
private  Connection connection;
private static  final String GET_SHOWTIME_QUERY="select * from showtimes where movie_id=? and showtime=? ";
private  static final String GET_SHOWTIME_MOVIE_QUERY="select * from showtimes where movie_id=?";
    public ShowtimeDaoImp(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Showtimes getShowTime(int movieId, String showtime) {
           Showtimes showtimes=null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(GET_SHOWTIME_QUERY);
            preparedStatement.setInt(1,movieId);
            preparedStatement.setString(2,  showtime);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                 showtimes=new Showtimes(resultSet.getInt("showtime_id"),resultSet.getInt("movie_id"),resultSet.getString("showtime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
    @Override
    public List<Showtimes> getShowTimeMovie(int movieId) {
        ArrayList<Showtimes> showtimesArrayList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(GET_SHOWTIME_MOVIE_QUERY);
            preparedStatement.setInt(1,movieId);
           ResultSet resultSet=preparedStatement.executeQuery();
           while(resultSet.next()){
               Showtimes showtimes=new Showtimes(resultSet.getInt("showtime_id"),resultSet.getInt("movie_id"),resultSet.getString("showtime"));
            showtimesArrayList.add(showtimes);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return  showtimesArrayList;
    }
}
