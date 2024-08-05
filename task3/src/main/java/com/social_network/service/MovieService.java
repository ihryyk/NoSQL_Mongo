package com.social_network.service;

import com.social_network.model.dao.MovieDao;
import com.social_network.model.document.Movie;

public class MovieService {

    private final MovieDao movieDao;

    public MovieService() {

        movieDao = new MovieDao();
    }

    public Movie saveMovie(Movie movie) {
        return movieDao.save(movie);
    }

}
