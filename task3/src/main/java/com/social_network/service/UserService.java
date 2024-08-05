package com.social_network.service;

import com.social_network.model.dao.MovieDao;
import com.social_network.model.dao.UserDao;
import com.social_network.model.document.Friend;
import com.social_network.model.document.User;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class UserService {

    private final UserDao userDao;
    private final MovieDao movieDao;

    public UserService() {
        userDao = new UserDao();
        movieDao = new MovieDao();
    }

    public User saveUser(User user) {
        for (Friend friend : user.getFriends()) {
            if (!user.getFriends().isEmpty() && userDao.getUserById(friend.getUserId()).isEmpty()) {
                throw new IllegalArgumentException("Friend with id " + friend.getUserId() + " does not exist");
            }
        }
        for (ObjectId movieId : user.getWatchedMovies()) {
            if (!user.getWatchedMovies().isEmpty() && movieDao.getMovieById(movieId).isEmpty()) {
                throw new IllegalArgumentException("Movie with id " + movieId + " does not exist");
            }
        }
        return userDao.saveUser(user);
    }

    public int getNumberOfNewFriendships(LocalDateTime startMonth, LocalDateTime endMonth) {
        return userDao.getNumberOfNewFriendships(startMonth, endMonth);
    }

    public int getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends() {
        return userDao.getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends();
    }

}
