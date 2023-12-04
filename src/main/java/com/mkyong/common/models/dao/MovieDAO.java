package com.mkyong.common.models.dao;
import java.util.List;
import java.util.Optional;

import com.mkyong.common.exceptions.DatabaseActionException;
import com.mkyong.common.models.entities.Movie;


public interface MovieDAO {
  void createTable() throws DatabaseActionException;
  void deleteTable() throws DatabaseActionException;
  void createMovie(final Movie movie) throws DatabaseActionException;
  void deleteMovie(int id) throws DatabaseActionException;
  void updateMoviesTitle(int id, String newTitle) throws DatabaseActionException;
  Optional<Movie> findMovieById(int id) throws DatabaseActionException;
  List<Movie> findAll() throws DatabaseActionException;
}
