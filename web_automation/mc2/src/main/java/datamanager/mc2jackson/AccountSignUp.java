package datamanager.mc2jackson;


import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountName",
        "solutions",
        "password",
        "email",
        "firstName",
        "lastName"
})
public class AccountSignUp {

    @JsonProperty("accountName")
    private String accountName;
    @JsonProperty("solutions")
    private List<String> solutions = null;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;

    public AccountSignUp(){
        solutions = Arrays.asList("Platform");
    }

    @JsonProperty("accountName")
    public String getAccountName() {
        return accountName;
    }

    @JsonProperty("accountName")
    public AccountSignUp setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    @JsonProperty("solutions")
    public List<String> getSolutions() {
        return solutions;
    }

    @JsonProperty("solutions")
    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public AccountSignUp setPassword(String password) {
        this.password = password;
        return this;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public AccountSignUp setEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public AccountSignUp setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public AccountSignUp setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }



}
