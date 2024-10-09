package com.fancode.Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class APIUtils {


    public static Response getRequest( String url) {
        return RestAssured.given()
                .log().all()
                .when()
                .get(url )
                .then()
                .log().ifError()
                .log().all()
                .extract().response();
    }
    public static Response getRequest(String url, Map<String, Object> queryParams) {
        return RestAssured.given()
                .log().all()
                .queryParams(queryParams) // Add query parameters dynamically
                .when()
                .get(url)
                .then()
                .log().ifError()
                .log().all()
                .extract().response();
    }

    public static Response postRequest(String url, Object requestBody) {
        return RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json") // Set the content type
                .body(requestBody) // Add the request body
                .when()
                .post(url)
                .then()
                .log().ifError()
                .log().all()
                .extract().response();
    }


    public static Response putRequest(String url, Object requestBody) {
        return RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json") // Set the content type
                .body(requestBody) // Add the request body
                .when()
                .put(url)
                .then()
                .log().ifError()
                .log().all()
                .extract().response();
    }
}
