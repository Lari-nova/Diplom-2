package test_data;

import pojo.OrderRequest;

public class OrderRequestTestData {

    public static String[] getIngredients() {
        OrderRequest orderClient = new OrderRequest();
        return orderClient.getIngredients();
    }

    public static OrderRequest getOrderWithIngredients() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(new String[]{"61c0c5a71d1f82001bdaaa72",
                "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa6c"});
        return orderRequest;
    }

    public static OrderRequest getOrderWithoutIngredients() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIngredients(new String[]{});
        return orderRequest;
    }

    public static OrderRequest getOrderWithIncorrectIngredients() {
        OrderRequest orderCreateRequest = new OrderRequest();
        orderCreateRequest.setIngredients(new String[]{"61c0c5a71d1f8earad2001bdaaa72",
                "61c0c5a71dcvz1f82001bdaaa74", "61c0c5a71d1fzdfb82001bdaaa6c"});
        return orderCreateRequest;
    }
}
