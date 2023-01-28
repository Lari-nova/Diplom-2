import api.UserAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static test_data.EditUserDataRequestTestData.*;
import static test_data.UserRequestTestData.getUserRequestAllRequiredField;

public class EditUserDataRequestTest {
    private UserAPI userAPI;
    private String token;

    @Before
    public void setUp() {
        userAPI = new UserAPI();
        UserRequest userRequest = getUserRequestAllRequiredField();
        token = userAPI.createUser(userRequest)
           .assertThat()
           .statusCode(SC_OK)
           .and()
           .body("success", is(true))
           .extract()
           .path("accessToken");
    }

    @After
    public void tearDown() {
        if (token != null) {
            userAPI.deleteUser(token)
               .assertThat()
               .statusCode(SC_ACCEPTED)
               .body("success", equalTo(true));
        }
    }

    @Test
    @DisplayName("Проверка изменения email с авторизацией")
    @Description("Email успешно изменен")
    public void emailShouldBeChangedTest() {
        String email = String.format("%s@yandex.ru", RandomStringUtils.randomAlphabetic(6).toLowerCase());
        UserRequest userEditRequest = getEditUserEmailRequest(email);
        userAPI.editUserWithAuthorization(userEditRequest, token)
           .assertThat()
           .statusCode(SC_OK)
           .and()
           .body("user.email", equalTo(email));
    }

    @Test
    @DisplayName("Проверка изменения имени с авторизацией")
    @Description("Имя успешно изменено")
    public void nameShouldBeChangedTest() {
        UserRequest userEditRequest = getEditUserNameRequest("ivan");
        userAPI.editUserWithAuthorization(userEditRequest, token)
           .assertThat()
           .statusCode(SC_OK)
           .and()
           .body("user.name", equalTo("ivan"));
    }

    @Test
    @DisplayName("Проверка изменения пароля с авторизацией")
    @Description("Пароль успешно изменен")
    public void passwordShouldBeChangedTest() {
        UserRequest userEditRequest = getEditUserPasswordRequest("qwerty123");
        userAPI.editUserWithAuthorization(userEditRequest, token)
           .assertThat()
           .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Проверка изменения email без авторизации")
    @Description("Должна произойти ошибка авторизации")
    public void emailShouldNotBeChangedTest() {
        String email = String.format("%s@yandex.ru", RandomStringUtils.randomAlphabetic(6));
        UserRequest userEditRequest = getEditUserEmailRequest(email);
        userAPI.editUserWithoutAuthorization(userEditRequest)
           .assertThat()
           .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка изменения имени без авторизации")
    @Description("Должна произойти ошибка авторизации")
    public void nameShouldNotBeChangedTest() {
        UserRequest userEditRequest = getEditUserNameRequest("ivan");
        userAPI.editUserWithoutAuthorization(userEditRequest)
           .assertThat()
           .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Проверка изменения пароля без авторизации")
    @Description("Должна произойти ошибка авторизации")
    public void passwordShouldNotBeChangedTest() {
        UserRequest userEditRequest = getEditUserPasswordRequest("qwerty123");
        userAPI.editUserWithoutAuthorization(userEditRequest)
           .assertThat()
           .statusCode(SC_UNAUTHORIZED);
    }
}
