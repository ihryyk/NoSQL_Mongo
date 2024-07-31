package ua.epam.mishchenko.ticketbooking.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Objects;

/**
 * The type Ticket.
 */
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Event event;

    private Integer place;

    private Category category;

    public Ticket() {
    }

    public Ticket(String id, User user, Event event, int place, Category category) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public Ticket(User user, Event event, int place, Category category) {
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(user, ticket.user) && Objects.equals(event, ticket.event) && Objects.equals(place, ticket.place) && category == ticket.category;
    }

    public int hashCode() {
        return Objects.hash(id, user, event, place, category);
    }

    public String toString() {
        return "{" +
                "'id' : " + id +
                ", 'userId' : " + user.getId() +
                ", 'eventId' : " + event.getId() +
                ", 'place' : " + place +
                ", 'category' : '" + category +
                "'}";
    }
}
