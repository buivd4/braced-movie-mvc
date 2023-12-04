package com.mkyong.common.models.dao;

import com.mkyong.common.exceptions.DatabaseActionException;
import com.mkyong.common.models.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Setter;
import lombok.Getter;

@Repository
@Getter
@Setter
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTable() throws DatabaseActionException {
        try {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS MOVIES (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255)," +
                    "genre VARCHAR(255)," +
                    "yearOfRelease INTEGER)");
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void deleteTable() throws DatabaseActionException {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS MOVIES");
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void createMovie(Movie movie) throws DatabaseActionException {
        String sql = "INSERT INTO MOVIES (title, genre, yearOfRelease) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, movie.getTitle(), movie.getGenre(), movie.getYearOfRelease());
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void deleteMovie(int id) throws DatabaseActionException {
        String sql = "DELETE FROM MOVIES WHERE id=?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) throws DatabaseActionException {
        String sql = "UPDATE MOVIES SET title=? WHERE id=?";
        try {
            jdbcTemplate.update(sql, newTitle, id);
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) throws DatabaseActionException {
        String sql = "SELECT * FROM MOVIES WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, rowNum) -> Optional.of(mapResultSetToMovie(resultSet)));
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    @Override
    public List<Movie> findAll() throws DatabaseActionException {
        String sql = "SELECT * FROM MOVIES";
        try {
            return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Movie()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .genre(resultSet.getString("genre"))
            .yearOfRelease(resultSet.getInt("yearOfRelease"))
            );
        } catch (Exception e) {
            throw new DatabaseActionException();
        }
    }

    private Movie mapResultSetToMovie(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String genre = resultSet.getString("genre");
        int yearOfRelease = resultSet.getInt("yearOfRelease");
        return new Movie().id(id).title(title).genre(genre).yearOfRelease(yearOfRelease);
    }
}
