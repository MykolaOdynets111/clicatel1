package apitests.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class TestUtils {
    private TestUtils() {
    }

    public static RequestSpecification jsonRequestBuilder() {
        return RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON);
    }

    public static String addDateTime(String name) {
        return name + " " + ZonedDateTime.now(ZoneOffset.UTC).toString();
    }
}
