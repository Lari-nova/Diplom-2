package API;

import io.restassured.response.Response;
import pojo.OrderRequest;

import static config.Config.getBaseUrl;
import static constants.Constants.BEARER;
import static constants.Constants.ORDERS;
import static io.restassured.RestAssured.given;

public class OrderAPI {

    public Response createOrderWithAuthorization(OrderRequest orderRequest, String accessToken) {
        return given()
                .header("Authorization", BEARER + accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(orderRequest)
                .post(ORDERS);
    }


    public Response createOrderWithoutAuthorization(OrderRequest orderRequest) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUrl())
                .body(orderRequest)
                .post(ORDERS);
    }


    public Response getOrdersWithAuthorization(String accessToken) {
        return given()
                .baseUri(getBaseUrl())
                .header("Authorization", BEARER + accessToken)
                .header("Content-type", "application/json")
                .get(ORDERS);
    }


    public Response getOrdersWithoutAuthorization() {
        return given()
                .baseUri(getBaseUrl())
                .header("Content-type", "application/json")
                .get(ORDERS);
    }
}