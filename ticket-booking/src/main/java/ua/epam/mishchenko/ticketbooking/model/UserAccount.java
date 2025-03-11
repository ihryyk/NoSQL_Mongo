package ua.epam.mishchenko.ticketbooking.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;

@Document(collection = "user_accounts")
public class UserAccount {

    @Id
    private String id;

    @DBRef
    private User user;

    private BigDecimal money;

    public UserAccount() {
    }

    public UserAccount(User user, BigDecimal money) {
        this.user = user;
        this.money = money;
    }

    public UserAccount(String id, User user, BigDecimal money) {
        this.id = id;
        this.user = user;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
