package interfaces;

import io.restassured.response.Response;

public interface APIHelper {
    Response executeGetProviders(String authorization, String endpoint);
}