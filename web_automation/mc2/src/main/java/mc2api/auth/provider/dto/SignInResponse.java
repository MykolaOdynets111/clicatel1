package mc2api.auth.provider.dto;

public class SignInResponse {
    private String token;

    public SignInResponse(String token) {
        this.token = token;
    }

    public SignInResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
