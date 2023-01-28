package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.LoginRequest;
import pojo.UserRequest;

import static config.Config.getBaseUrl;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class UserAPI {

    @Step("Создание юзера")
    public ValidatableResponse createUser(UserRequest request) {
        return given()
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(request)
           .post(USER_CREATE)
           .then();
    }

    @Step("Авторизация созданного юзера")
    public ValidatableResponse userLogin(LoginRequest request) {
        return given()
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(request)
           .post(USER_LOGIN)
           .then();
    }

    @Step("Редактирование юзера с авторизацией")
    public ValidatableResponse editUserWithAuthorization(UserRequest request, String accessToken) {
        return given()
           .header("Authorization", BEARER + accessToken)
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(request)
           .log()
           .all()
           .patch(USER_EDIT)
           .then();
    }

    @Step("Редактирование юзера без авторизации")
    public ValidatableResponse editUserWithoutAuthorization(UserRequest request) {
        return given()
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(request)
           .patch(USER_EDIT)
           .then();
    }

    @Step("Разлогин юзера")
    public ValidatableResponse userLogout(UserRequest request) {
        return given()
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(request)
           .post(USER_LOGOUT)
           .then();
    }

    @Step("Удаление юзера")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
           .header("Authorization", BEARER + accessToken)
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .delete(USER_EDIT)
           .then();
    }
}
