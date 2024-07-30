package ua.epam.mishchenko.ticketbooking.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.epam.mishchenko.ticketbooking.facade.impl.BookingFacadeImpl;
import ua.epam.mishchenko.ticketbooking.model.UserAccount;

import java.math.BigDecimal;

@Controller
@RequestMapping("/user-accounts")
public class UserAccountController {

    private final BookingFacadeImpl bookingFacade;

    public UserAccountController(BookingFacadeImpl bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @PostMapping
    public UserAccount refillUserAccount(@RequestParam String userId, @RequestParam long money) {
        return bookingFacade.refillUserAccount(userId, BigDecimal.valueOf(money));
    }

}
