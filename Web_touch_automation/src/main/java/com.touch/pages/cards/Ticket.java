package com.touch.pages.cards;

/**
 * Created by kmakohoniuk on 11/23/2016.
 */
public class Ticket {
   private String subject;
    private String status;
    private String description;
    private String ticketNumber;
    private String assignedTo;

    public Ticket(String subject, String status, String description, String ticketNumber, String assignedTo) {
        this.subject = subject;
        this.status = status;
        this.description = description;
        this.ticketNumber = ticketNumber;
        this.assignedTo = assignedTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (!subject.equals(ticket.subject)) return false;
        if (!status.equals(ticket.status)) return false;
        if (!description.equals(ticket.description)) return false;
        if (!ticketNumber.equals(ticket.ticketNumber)) return false;
        return assignedTo.equals(ticket.assignedTo);

    }

    @Override
    public int hashCode() {
        int result = subject.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + ticketNumber.hashCode();
        result = 31 * result + assignedTo.hashCode();
        return result;
    }
}
