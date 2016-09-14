package com.clickatell.engines;

import com.clickatell.models.MessageResponse;
import com.clickatell.utils.ConfigApp;
import com.clickatell.utils.ConfigAppMC2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by sbryt on 6/7/2016.
 */
public class RequestEngineMC2 {

    public RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("Accept", "application/json")
            .setBaseUri(ConfigAppMC2.BASE_API_URL)
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .build();

    public RequestEngineMC2() {

    }

    public Response postRequest(String endpoint, Object body) {
        return postRequest(endpoint, null, null, body);
    }

    public Response postRequest(String endpoint, String id, Object body) {
        return postRequest(endpoint, id, null, body);
    }

    /**
     * Make POST request with form parameters
     *
     * @param endpoint       endpoint for POST request
     * @param formParameters form parameters as {@link Map}
     * @return {@link Response} object
     */
    public Response postRequestWithFormParameters(String endpoint, Map<String, Object> formParameters) {
        return given(requestSpecification)
                .formParams(formParameters)
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Make POST request with form parameters
     *
     * @param endpoint       endpoint for POST request
     * @param paramName      first parameter name (required)
     * @param paramValue     first parameter value (required)
     * @param formParameters other form parameters for request(optional)
     * @return {@link Response} object
     */
    public Response postRequestWithFormParameters(String endpoint, String paramName, Object paramValue, Object... formParameters) {
        return given(requestSpecification)
                .formParams(paramName, paramValue, formParameters)
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response postRequest(String endpoint, String id, String id1, Object body, Header... header) {
        RequestSpecification rs = given(requestSpecification);
        if (body != null) {
            try {
                rs.body(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        Response response = null;
        if (header.length == 0) {
            rs.header(new Header("Solution", "PLATFORM"));
        } else {
            rs.headers(new Headers(header));
        }
        if (id == null && id1 == null) {
            response = rs.post(endpoint);
        } else if (id != null) {
            if (id1 != null) {
                response = rs.post(endpoint, id, id1);
            } else {
                response = rs.post(endpoint, id);
            }
        }
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response postRequestWithMultiPart(String endpoint, Boolean firstRowHeader, String fieldSeparator, File csvFile) {
        return given(requestSpecification)
                .multiPart("csvfile", csvFile)
                .multiPart("firstRowHeader", firstRowHeader)
                .multiPart("fieldSeparator", fieldSeparator)
                .when()
                .contentType("multipart/form-data")
                .post(endpoint)
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public Response postRequest(String endpoint, Object body, Header header, boolean replaceHeader) {
        if (replaceHeader) {
            try {
                return given().log().everything()
                        .header(header.getName(), header.getValue())
                        .contentType(ContentType.JSON)
                        .baseUri(ConfigApp.BASE_API_URL)
                        .body(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body))
                        .when()
                        .post(endpoint)
                        .then()
                        .log()
                        .all()
                        .extract()
                        .response();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return postRequest(endpoint, null, null, body, header);
    }

    public Response postRequest(String endpoint, String id1, String id2, Header... header) {
        return postRequest(endpoint, id1, id2, null, header);
    }

    public Response getRequest(String endpoint, String id, String id1, Object body, Header... header) {
        RequestSpecification rs = given(requestSpecification);
        if (body != null) {
            try {
                rs.body(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        Response response = null;
        if (header.length == 0) {
            rs.header(new Header("Solution", "PLATFORM"));
        } else {
            rs.headers(new Headers(header));
        }
        if (id == null && id1 == null) {
            response = rs.get(endpoint);
        } else if (id != null) {
            if (id1 != null) {
                response = rs.get(endpoint, id, id1);
            } else {
                response = rs.get(endpoint, id);
            }
        }
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public <T> T getRequest(String endpoint, Class<T> clazz, Header... header) {
        return getRequest(endpoint, null, null, null, header).as(clazz);
    }

    public <T> T getRequest(String endpoint, Class<T> clazz, String id, Header... header) {
        return getRequest(endpoint, id, null, null, header).as(clazz);
    }

    public Response getRequest(String endpoint, Header... header) {
        return getRequest(endpoint, null, null, null, header);
    }

    public Response getRequest(String endpoint, String id, Header... header) {
        return getRequest(endpoint, id, null, null, header);
    }

    public Response putRequest(String endpoint, String id, String id1, Object body, Header... header) {
        RequestSpecification rs = given(requestSpecification);
        if (body != null && !(body instanceof String)) {
            try {
                rs.body(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (body instanceof String) {
            rs.body(body);
        }
        Response response = null;
        if (header.length == 0) {
            rs.header(new Header("Solution", "PLATFORM"));
        } else {
            rs.headers(new Headers(header));
        }
        if (id == null && id1 == null) {
            response = rs.put(endpoint);
        } else if (id != null) {
            if (id1 != null) {
                response = rs.put(endpoint, id, id1);
            } else {
                response = rs.put(endpoint, id);
            }
        }
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    public MessageResponse putRequest(String endpoint, String id, String body) {
        return putRequest(endpoint, id, null, body).as(MessageResponse.class);
    }

    public Response putRequest(String endpoint, String id, Object body) {
        return putRequest(endpoint, id, null, body);
    }

    public Response putRequest(String endpoint, Object body, Header... header) {
        return putRequest(endpoint, null, null, body, header);
    }

    public Response putRequest(String endpoint, String id, Header... header) {
        return putRequest(endpoint, id, null, null, header);
    }

    public Response deleteRequest(String endpoint, String... id) {
        RequestSpecification rs = given(requestSpecification).header(new Header("Solution", "PLATFORM")).when();
        Response response = null;
        switch (id.length) {
            case 0:
                response = rs.delete(endpoint);
                break;
            case 1:
                response = rs.delete(endpoint, id[0]);
                break;
            case 2:
                response = rs.delete(endpoint, id[0], id[1]);
        }
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();
    }
}
