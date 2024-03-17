package request;

import enums.APIErrorCodes;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import jline.internal.Nullable;

import static io.restassured.RestAssured.with;

public class WeatherRequest extends APIRequest{

    private static final String API_KEY = "3ce5b27b2a2f4922b82130935241703";

    @Step("запросить актуальную погоду для города: {city}")
    public static ValidatableResponse getCurrentWeatherForCity(String city) {
        return with()
                .spec(createRequestSpec())
                .when()
                .param("q", city)
                .param("key", API_KEY)
                .get("/current.json")
                .then();
    }

    @Step("запросить актуальную погоду")
    public static ValidatableResponse getCurrentWeather(APIErrorCodes apiErrorCodes, @Nullable String city) {
        switch (apiErrorCodes) {
            case Q_PARAMETER_MISSED:
                return Allure.step(apiErrorCodes.getDescription(), () -> with()
                        .spec(createRequestSpec())
                        .when()
                        .param("q", "")
                        .param("key", API_KEY)
                        .get("/current.json")
                        .then());
            case API_REQUEST_INVALID:
                return  Allure.step(apiErrorCodes.getDescription(), () -> with()
                        .spec(createRequestSpec())
                        .when()
                        .param("q", city)
                        .param("key", API_KEY)
                        .get("/currented.json")
                        .then());
            case NO_FOUND_LOCATION_Q :
                return  Allure.step(apiErrorCodes.getDescription(), () -> with()
                        .spec(createRequestSpec())
                        .when()
                        .param("q", "FakeLocation")
                        .param("key", API_KEY)
                        .get("/current.json")
                        .then());
            case API_KEY_DISABLED :
                return  Allure.step(apiErrorCodes.getDescription(), () -> with()
                        .spec(createRequestSpec())
                        .when()
                        .param("key", API_KEY + "a")
                        .param("q", city)
                        .get("/current.json")
                        .then());
            default:
                throw new RuntimeException("Invalid Data type not found");
        }

    }
}
