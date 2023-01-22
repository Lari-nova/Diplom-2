package API;

import io.restassured.response.ValidatableResponse;
import pojo.LoginRequest;
import pojo.UserRequest;

import static config.Config.getBaseUrl;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class UserAPI {

    public ValidatableResponse createUser(UserRequest request) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(request)
                .post(USER_CREATE)
                .then();
    }

    public ValidatableResponse userLogin(LoginRequest request) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(request)
                .post(USER_LOGIN)
                .then();
    }

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

    public ValidatableResponse editUserWithoutAuthorization(UserRequest request) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(request)
                .patch(USER_EDIT)
                .then();
    }

    public ValidatableResponse userLogout(UserRequest request) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(request)
                .post(USER_LOGOUT)
                .then();
    }


    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .header("Authorization", BEARER + accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .delete(USER_EDIT)
                .then();
    }
}
