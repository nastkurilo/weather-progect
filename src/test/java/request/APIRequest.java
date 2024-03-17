package request;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;

public abstract class APIRequest {
    private static final String BASE_URL = "https://api.weatherapi.com/v1";

    public static RequestSpecification createRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY)
                .log(LogDetail.ALL)
                .setUrlEncodingEnabled(false)
                .build();
    }
}
