package datamanager.model.wa.template.enums;

public enum WaHsmTemplateTag {
    ACCOUNT_UPDATE(1, "Account update"),
    PAYMENT_UPDATE(2, "Payment update"),
    PERSONAL_FINANCE_UPDATE(3, "Personal finance update"),
    SHIPPING_UPDATE(4, "Shipping update"),
    RESERVATION_UPDATE(5, "Reservation update"),
    ISSUE_RESOLUTION(6, "Issue resolution"),
    APPOINTMENT_UPDATE(7, "Appointment update"),
    TRANSPORTATION_UPDATE(8, "Transportation update"),
    TICKET_UPDATE(9, "Ticket update"),
    OTHERS(10, "Others");

    private int id;
    private String name;

    WaHsmTemplateTag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
