package datamanager;

public class VMQuoteRequestUserData {

    private final String firstName;

    private final String lastName;

    private final String contactNumber;

    private final String email;

    public VMQuoteRequestUserData(String firstName, String lastName, String contactNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getWidgetPresentationOfPersonalInfoInput(){
        return "I am " +firstName + " " + lastName + " and you can contact me on " +
                contactNumber + " and mail me at " + email;
    }
}
