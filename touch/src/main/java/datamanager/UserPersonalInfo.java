package datamanager;

public class UserPersonalInfo {

    private String fullName;
    private String location;
    private String customerSince;
    private String email;
    private String channelUsername;
    private String phone;

    public UserPersonalInfo(String fullName, String location, String customerSince, String email, String channelUsername, String phone) {
        this.fullName = fullName;
        this.location = location;
        this.customerSince = customerSince;
        this.email = email;
        this.channelUsername = channelUsername;
        this.phone = phone;
    }

    public UserPersonalInfo setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }


    public UserPersonalInfo setLocation(String location) {
        this.location = location;
        return this;
    }

    public UserPersonalInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserPersonalInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserPersonalInfo setChannelUsername(String channelUsername) {
        this.channelUsername = channelUsername;
        return this;
    }



    public String getFullName() {
        return fullName;
    }


    public String getLocation() {
        return location;
    }

    public String getCustomerSince() {
        return customerSince;
    }

    public String getEmail() {
        return email;
    }

    public String getChannelUsername() {
        return channelUsername;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object object) {
        UserPersonalInfo another =  (UserPersonalInfo) object;
        return this.fullName.trim().equals(another.getFullName().trim()) && this.location.equals(another.getLocation()) &&
                this.customerSince.equals(another.getCustomerSince()) && this.email.equals(another.getEmail()) &&
//                this.channelUsername.equals(another.getChannelUsername()) &&
                this.phone.equals(another.getPhone()) ;
    }

    @Override
    public String toString() {
        return "UserPersonalInfo{" +
                "fullName='" + fullName + "'\n" +
                ", location='" + location + "'\n" +
                ", customerSince='" + customerSince + "'\n" +
                ", email='" + email + '\'' +
                ", channelUsername='" + channelUsername + "'\n" +
                ", phone='" + phone + '\'' +
                "}\n";
    }
}
