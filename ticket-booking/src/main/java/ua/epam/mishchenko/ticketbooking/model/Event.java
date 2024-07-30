package ua.epam.mishchenko.ticketbooking.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static ua.epam.mishchenko.ticketbooking.utils.Constants.DATE_FORMATTER;

/**
 * The type Event.
 */

@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String title;

    private Date date;

    private BigDecimal ticketPrice;

    @DBRef
    private List<Ticket> tickets;

    public Event() {
    }

    public Event(String title, Date date, BigDecimal ticketPrice, List<Ticket> tickets) {
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.tickets = tickets;
    }

    public Event(String id, String title, Date date, BigDecimal ticketPrice, List<Ticket> tickets) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.tickets = tickets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setEvent(this);
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(date, event.date) && Objects.equals(ticketPrice, event.ticketPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, ticketPrice);
    }

    @Override
    public String toString() {
        return "{" +
                "'id' : " + id +
                ", 'title' : '" + title + '\'' +
                ", 'date' : '" + DATE_FORMATTER.format(date) +
                "', 'ticket_price' : " + ticketPrice +
                "}";
    }

}
