package com.touch.engines;

import com.touch.models.MessageResponse;
import com.touch.utils.ConfigApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;


public class RequestEngine {

    public RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("Accept", "application/json")
            .setBaseUri(ConfigApp.BASE_API_URL)
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .build();
    public RequestSpecification requestSpecificationForMultipart = new RequestSpecBuilder()
            .addHeader("Accept", "text/plain")
            .addHeader("Content-Type", "multipart/form-data")
            .setBaseUri(ConfigApp.BASE_API_URL)
            .log(LogDetail.ALL)
            .build();

    public RequestEngine() {
//        RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT));
    }


    public Response postRequestWithQueryParameters(String endpoint, String id, Map<String, Object> formParameters) {
        RequestSpecification rs = given(requestSpecification).queryParams(formParameters);
        Response responce = null;
        if (id != null) {
            responce = rs.post(endpoint, id);
        } else {
            responce = rs.post(endpoint);
        }
        return responce
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response postRequestWithFormParameters(String endpoint, String id1, String id2, Map<String, Object> formParameters, String contentType) {
        RequestSpecification rs = given(requestSpecification).formParams(formParameters);
        if (contentType != null)
            rs = rs.contentType(contentType);
        Response response = null;
        if (id1 == null && id2 == null) {
            response = rs.post(endpoint);
        } else if (id1 != null) {
            if (id2 != null) {
                response = rs.post(endpoint, id1, id2);
            } else {
                response = rs.post(endpoint, id1);
            }
        }
        return response
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response postRequestWithFormParametersAndFile(String endpoint, String id, Map<String, Object> formParameters, File file) {
        RequestSpecification rs = null;
        if (file != null) {
            rs = given().baseUri(ConfigApp.BASE_API_URL).multiPart("file", file).formParams(formParameters);
        } else {
            rs = given().baseUri(ConfigApp.BASE_API_URL).multiPart("file", "").formParams(formParameters);
        }
        Response responce = null;
        if (id != null) {
            responce = rs.post(endpoint, id);
        } else {
            responce = rs.post(endpoint);
        }
        return responce
                .then()
                .log().all()
                .extract()
                .response();
    }

//    /**
//     * Make POST request with form parameters
//     *
//     * @param endpoint       endpoint for POST request
//     * @param paramName      first parameter name (required)
//     * @param paramValue     first parameter value (required)
//     * @param formParameters other form parameters for request(optional)
//     * @return {@link Response} object
//     */
//    public Response postRequestWithFormParameters(String endpoint, String paramName, Object paramValue, Object... formParameters) {
//        return given(requestSpecification)
//                .formParams(paramName, paramValue, formParameters)
//                .post(endpoint)
//                .then()
//                .log().all()
//                .extract()
//                .response();
//    }

    public Response postRequest(String endpoint, Object body) {
        return postRequest(endpoint, null, null, body);
    }

    public Response postRequest(String endpoint, String id, Object body) {
        return postRequest(endpoint, id, null, body);
    }

    public Response postRequest(String endpoint, String id, String id1, Object body, Header... header) {
        RequestSpecification rs = given(requestSpecification);
        if (body != null) {
            try {
                String s = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(body);
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

    public Response postRequestWithFile(String endpoint, String id, String id1, File file) {
        RequestSpecification rs = given(requestSpecification)
                .multiPart(file)
                .when()
                .contentType("multipart/form-data");
        Response response = null;
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

    public Response putFile(String endpoint, String id, File file) {
        RequestSpecification rs = given(requestSpecification)
                .multiPart(file)
                .when()
                .contentType("multipart/form-data");
        Response response = null;

        if (id != null) {
            response = rs.put(endpoint, id);
        } else {
            response = rs.put(endpoint);
        }
        return response
                .then()
                .log()
                .all()
                .extract()
                .response();

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

    public Response deleteRequestWithQueryParameters(String endpoint, String id, Map<String, Object> parameters) {
        RequestSpecification rs = given(requestSpecification).queryParams(parameters);
        Response responce = null;
        if (id != null) {
            responce = rs.delete(endpoint, id);
        } else {
            responce = rs.delete(endpoint);
        }
        return responce
                .then()
                .log().all()
                .extract()
                .response();
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
