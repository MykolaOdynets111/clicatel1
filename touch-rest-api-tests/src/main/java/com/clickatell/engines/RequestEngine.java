package com.clickatell.engines;

import com.clickatell.models.MessageResponse;
import com.clickatell.utils.ConfigApp;
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
public class RequestEngine {

    public RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("Accept", "application/json")
            .setBaseUri(ConfigApp.BASE_API_URL)
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .build();

    public RequestEngine() {

    }

    //    public Response postRequestWithParameters(String endpoint,String id, Map<String, Object> parameters) {
//        RequestSpecification rs = given(requestSpecification);
//
//        String parametersString="?";
//        int parametersAmount=parameters.size();
//        for(String key: parameters.keySet()){
//            parametersString+=key+"="+parameters.get(key);
//            parametersAmount--;
//            if(parametersAmount>0){
//                parametersString+="&";
//            }
//        }
//        Response responce = null;
//      if (id != null) {
//          responce = rs.post(endpoint+parametersString, id);
//      }else{
//          responce = rs.post(endpoint);
//      }
//        return responce
//                .then()
//                .log().all()
//                .extract()
//                .response();
//    }
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

    public Response postRequestWithFormParametersT(String endpoint, String id) {

        RequestSpecification rs = given(requestSpecification);
//        for(int i=0;i+1<parameters.length;i=i+2){
//            rs.queryParam(parameters[i],parameters[i+1]);
//        }

        Response responce = null;
        if (id != null) {
            responce = rs.post(endpoint + "?name=test1&value=test2", id);
        } else {
            responce = rs.post(endpoint);
        }
        return responce
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
