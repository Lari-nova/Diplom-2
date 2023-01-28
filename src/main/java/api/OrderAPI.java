package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.OrderRequest;

import static config.Config.getBaseUrl;
import static constants.Constants.BEARER;
import static constants.Constants.ORDERS;
import static io.restassured.RestAssured.given;

public class OrderAPI {

    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuthorization(OrderRequest orderRequest, String accessToken) {
        return given()
           .header("Authorization", BEARER + accessToken)
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(orderRequest)
           .post(ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(OrderRequest orderRequest) {
        return given()
           .header("Content-type", "application/json")
           .baseUri(getBaseUrl())
           .body(orderRequest)
           .post(ORDERS);
    }

    @Step("Получение заказа с авторизацией")
    public Response getOrdersWithAuthorization(String accessToken) {
        return given()
           .baseUri(getBaseUrl())
           .header("Authorization", BEARER + accessToken)
           .header("Content-type", "application/json")
           .get(ORDERS);
    }

    @Step("Получение заказа без авторизации")
    public Response getOrdersWithoutAuthorization() {
        return given()
           .baseUri(getBaseUrl())
           .header("Content-type", "application/json")
           .get(ORDERS);
    }
}