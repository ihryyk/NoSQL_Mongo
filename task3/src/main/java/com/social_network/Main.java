package com.social_network;

import com.social_network.service.MessageService;
import com.social_network.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        MessageService messageService = new MessageService();

        System.out.println("GetAverageNumberOfMessagesOnDayOfWeek " + messageService.getAverageNumberOfMessagesOnDayOfWeek(DayOfWeek.MONDAY));
        System.out.println("getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends " + userService.getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends());
        System.out.println("getNumberOfNewFriendships " + userService.getNumberOfNewFriendships(LocalDateTime.of(2022, 2, 1, 0, 0), LocalDateTime.of(2022, 4, 1, 0, 0)));
    }
}
