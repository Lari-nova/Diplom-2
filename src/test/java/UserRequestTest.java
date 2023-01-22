import API.UserAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static test_data.UserRequestTestData.getUserRequestAllRequiredField;

public class UserRequestTest {
    private UserAPI userAPI;
    private String token;

    @Before
    public void setUp() {
        userAPI = new UserAPI();
    }

    @After
    public void tearDown() {
        if (token != null) {
            userAPI.deleteUser(token)
                    .assertThat()
                    .statusCode(SC_ACCEPTED)
                    .body("success", is(true));
        }
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверяю корректное создание пользователя.")
    public void createUserTest() {
        UserRequest userRequest = getUserRequestAllRequiredField();
        token = userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");

    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка уникальности передаваемых атрибутов пользователя. Ошибка создания пользователя")
    public void userMustNotBeCreatedLoginIsNotUniqueTest() {
        UserRequest userRequest = getUserRequestAllRequiredField();
        token = userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
        userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя с пустым email. Ошибка создания пользователя")
    public void userMustNotBeCreatedEmailIsEmptyTest() {
        UserRequest randomUserRequest = getUserRequestAllRequiredField();
        randomUserRequest.setEmail("");
        token = userAPI.createUser(randomUserRequest)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя с пустым name. Ошибка создания пользователя")
    public void userMustNotBeCreatedNameIsEmptyTest() {
        UserRequest userRequest = getUserRequestAllRequiredField();
        userRequest.setName("");
        token = userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка создания пользователя с пустым password. Ошибка создания пользователя")
    public void userMustNotBeCreatedPasswordIsEmptyTest() {
        UserRequest userRequest = getUserRequestAllRequiredField();
        userRequest.setPassword("");
        token = userAPI.createUser(userRequest)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }
}
