package test_data;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.LoginRequest;
import pojo.UserRequest;

public class LoginRequestTestData {

    private static final String EMAIL = RandomStringUtils.randomAlphanumeric(5);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(5);

    public static LoginRequest from(UserRequest userRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userRequest.getEmail());
        loginRequest.setPassword(userRequest.getPassword());
        return loginRequest;
    }

    public static LoginRequest invalidLoginPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);
        return loginRequest;
    }

    public static LoginRequest requestWithoutRequiredField() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(PASSWORD);
        return loginRequest;
    }
}
