package test_data;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.UserRequest;

public class UserRequestTestData {

    private static final String EMAIL = RandomStringUtils.randomAlphanumeric(6);
    private static final String NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(8);

    public static UserRequest getUserRequestAllRequiredField() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(String.format("%s@gmail.com", EMAIL.toLowerCase()));
        userRequest.setName(NAME);
        userRequest.setPassword(PASSWORD);
        return userRequest;
    }
}