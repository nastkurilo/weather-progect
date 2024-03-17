package tests

import Assertion.AssertionsAPI
import enums.APIErrorCodes
import helpers.Cities
import io.qameta.allure.Story
import io.restassured.response.ValidatableResponse
import model.DataClass
import model.ErrorMessage
import request.WeatherRequest
import spock.lang.Specification
import spock.lang.Tag


class WeatherApiTest extends Specification {

    @Tag("Positive")
    @Story("Получение актуальной погоды")
    def "Актуальня погода успешно получена для города: #city_name "() {
        given: "Используя название любого города для параметра 'q'"
        when: "Отправить запрос /current.json для получения актуальной погоды"
        ValidatableResponse response = WeatherRequest.getCurrentWeatherForCity(city_name)

        then: "Проверить что данные местоположения актуальной погоды корректны"
        AssertionsAPI.assertStatusCodeIs(response, 200)
        AssertionsAPI.assertLocationCityIs(city_name, response.extract().as(DataClass.class))

        where:
        city_name << Cities.CITIES
    }

    @Tag("Negative")
    @Story("Получение ошибки по запросу актуальной погоды")
    def "Отображается корректная ошибка: #error_message"() {
        given: "Используя невалидные данные запроса"

        when: "Отправить запрос /current.json"
        ValidatableResponse response = WeatherRequest.getCurrentWeather(invalid_data, city)

        then: "Проверить что код и сообщение из ошибки корректны"
        AssertionsAPI.assertStatusCodeIs(response, status_code)

        var error = response.extract().jsonPath().getObject("error", ErrorMessage.class)
        error != null

        AssertionsAPI.assertErrorCodeIs(error_code, error)
        AssertionsAPI.assertErrorMessageIs(error_message, error)

        where:
        invalid_data                      | city                 || status_code | error_code | error_message
        APIErrorCodes.Q_PARAMETER_MISSED  | null                 || 400         | 1003       | "Parameter q is missing."
        APIErrorCodes.API_REQUEST_INVALID | Cities.CITIES.get(1) || 400         | 1005       | "API URL is invalid."
        APIErrorCodes.NO_FOUND_LOCATION_Q | null                 || 400         | 1006       | "No matching location found."
        APIErrorCodes.API_KEY_DISABLED    | Cities.CITIES.get(1) || 403         | 2008       | "API key has been disabled."
    }

}
