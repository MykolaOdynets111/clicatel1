package datamanager.model;

import com.github.javafaker.Faker;

public class NewUserModel {

    private String firstName;
    private String lastName;
    private String email;
    private Faker faker;

    public NewUserModel(){
        this.faker = new Faker();
        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.email = "aqamc277+" + System.currentTimeMillis() + "@gmail.com";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
