import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import pojo.LoginRequest;
import pojo.UserRequest;
import test_data.LoginRequestTestData;
import api.UserAPI;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static test_data.LoginRequestTestData.requestWithoutRequiredField;
import static test_data.UserRequestTestData.getUserRequestAllRequiredField;

public class LoginRequestTest {

    private static UserAPI userAPI;
    private static String token;

    @Before
    public void setUp() {
        userAPI = new UserAPI();
    }

    @AfterClass
    public static void tearDown() {
        if (token != null) {
            userAPI.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Авторизация пользователя с валидным логином и паролем")
    @Step("Проверяем, авторизацию пользователя с корректным логином и паролем")
    public void userAuthorizationWithCorrectlyPasswordLogin() {
        UserRequest userRequest = getUserRequestAllRequiredField();
        userAPI.createUser(userRequest);

        LoginRequest loginRequest = LoginRequestTestData.from(userRequest);
        userAPI.userLogin(loginRequest)
           .assertThat()
           .statusCode(SC_OK)
           .and()
           .body("success", equalTo(true))
           .extract()
           .path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя без одного обязательного поля в запросе")
    @Step("Проверяем, если какого-то поля нет, запрос возвращает ошибку")
    public void courierAuthorizationWithoutRequiredFieldInRequest() {
        LoginRequest loginRequest = requestWithoutRequiredField();
        userAPI.userLogin(loginRequest)
           .statusCode(SC_UNAUTHORIZED)
           .and()
           .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}