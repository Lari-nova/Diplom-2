package test_data;

import pojo.UserRequest;

public class EditUserDataRequestTestData {

    public static UserRequest getEditUserNameRequest(String name) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        return userRequest;
    }

    public static UserRequest getEditUserPasswordRequest(String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword(password);
        return userRequest;
    }

    public static UserRequest getEditUserEmailRequest(String email) {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email);
        return userRequest;
    }
}