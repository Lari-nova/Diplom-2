import API.UserAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import API.OrderAPI;
import org.junit.Before;
import org.junit.Test;
import pojo.OrderRequest;
import pojo.UserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static test_data.OrderRequestTestData.*;
import static test_data.UserRequestTestData.getUserRequestAllRequiredField;

public class OrderRequestTest {

    private static UserAPI userAPI;
    private UserRequest userRequest;
    private String token;

    @Before
    public void setUp() {
        userAPI = new UserAPI();
    }

    @After
    public void deleteUser() {
        if (token != null) {
            userAPI.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с ингредиентами")
    @Description("Проверяем создание заказа под существующим юзером и с ингредиентами")
    public void createOrderWithAuthorizationAndIngredients() {
        userRequest = getUserRequestAllRequiredField();
        token = userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderRequest orderCreateRequest = getOrderWithIngredients();
        OrderAPI orderPageObject = new OrderAPI();
        Response response = orderPageObject.createOrderWithAuthorization(orderCreateRequest, token);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    @Description("Проверяем создание заказа без авторизации и с ингредиентами")
    public void createOrderWithoutAuthorization() {
        OrderRequest orderCreateRequest = getOrderWithIngredients();
        OrderAPI orderAPI = new OrderAPI();
        Response response = orderAPI.createOrderWithoutAuthorization(orderCreateRequest);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без ингредиентов")
    @Description("Проверяем создание заказа под существующим юзером и без ингредиентов")
    public void createOrderWithoutIngredients() {
        userRequest = getUserRequestAllRequiredField();
        ValidatableResponse vResponse = userAPI.createUser(userRequest);
        token = vResponse
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderRequest orderCreateRequest = getOrderWithoutIngredients();
        OrderAPI orderPageObject = new OrderAPI();
        Response response = orderPageObject.createOrderWithAuthorization(orderCreateRequest, token);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с некорректным хэшем ингредиентов")
    @Description("Проверяем создание заказа под существующим юзером и с некорректным хэшем ингредиентов")
    public void createOrderWithIncorrectIngredients() {
        userRequest = getUserRequestAllRequiredField();
        ValidatableResponse vResponse = userAPI.createUser(userRequest);
        token = vResponse
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderRequest orderCreateRequest = getOrderWithIncorrectIngredients();
        OrderAPI orderAPI = new OrderAPI();
        Response response = orderAPI.createOrderWithAuthorization(orderCreateRequest, token);
        response.then()
                .statusCode(500);
    }
}