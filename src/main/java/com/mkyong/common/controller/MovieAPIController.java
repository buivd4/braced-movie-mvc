package com.mkyong.common.controller;

import com.mkyong.common.exceptions.DatabaseActionException;
import com.mkyong.common.models.dao.MovieDAO;
import com.mkyong.common.models.entities.Movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Controller
@RequestMapping("/api")
public class MovieAPIController {
    @Autowired
    private MovieDAO movieDAO;

    @RequestMapping(value="/movies", method=RequestMethod.GET)
    public ResponseEntity<Object> getMovies() throws DatabaseActionException{
        List<Movie> movies = movieDAO.findAll();
        return new ResponseEntity<Object>(movies, HttpStatus.OK);
    }

    @RequestMapping(value="/movies/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> getMovieById(@PathVariable int id) throws DatabaseActionException{
        Optional<Movie> movie = movieDAO.findMovieById(id);
        if (movie.isEmpty()){
            return new ResponseEntity<Object>(movie, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(movie.get(), HttpStatus.OK);
    }
    
    @RequestMapping(value="/movies", method=RequestMethod.POST)
    public ResponseEntity<String> createMovie(@RequestBody Movie movie) throws DatabaseActionException{
        movieDAO.createMovie(movie);
        return new ResponseEntity<String>("Created!", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/movies/{id}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateMovie(@PathVariable int id, @RequestBody Movie movie) throws DatabaseActionException{
        Optional<Movie> existingMovie = movieDAO.findMovieById(id);
        if (existingMovie.isPresent()) {
            movieDAO.updateMoviesTitle(id, movie.getTitle());
        }
        return new ResponseEntity<String>("Updated!", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/movies/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteMovie(@PathVariable int id) throws DatabaseActionException{
        movieDAO.deleteMovie(id);
        return new ResponseEntity<String>("Deleted!", HttpStatus.NO_CONTENT);
    }
}
