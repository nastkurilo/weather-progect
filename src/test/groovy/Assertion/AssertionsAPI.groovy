package Assertion

import enums.APIErrorCodes
import io.qameta.allure.Step
import io.restassured.response.ValidatableResponse
import model.DataClass
import model.ErrorMessage

class AssertionsAPI {

    @Step("Статус код ответа должен быть: {statusCode}")
    static void assertStatusCodeIs(ValidatableResponse response, int statusCode) {
        response.statusCode(statusCode)
    }

    @Step("Актуальная погода предоставлена для города: {expectedCity}")
    static void assertLocationCityIs(String expectedCity, DataClass dataClass) {
        assert dataClass.getLocation().getName().containsIgnoreCase(expectedCity)
    }

    @Step("Код ошибки должен быть: {expectedCode}")
    static void assertErrorCodeIs(int expectedCode, ErrorMessage error) {
        assert error.getCode() == expectedCode
    }

    @Step("Сообщение ошибки должно быть: {expectedMessage}")
    static void assertErrorMessageIs(String expectedMessage, ErrorMessage error) {
        assert error.getMessage() == expectedMessage
    }
}
