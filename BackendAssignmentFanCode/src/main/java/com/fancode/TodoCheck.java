package com.fancode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fancode.models.Todo;
import com.fancode.models.User;

import java.util.List;

public class TodoCheck {

    private static final String USERS_URL = "http://jsonplaceholder.typicode.com/users";
    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";

    public void checkTodosForFanCodeUsers() {
        List<User> users = getUsers();

        for (User user : users) {
            if (isFanCodeCity(user)) {
                List<Todo> todos = getTodosForUser(user.getId());
                double completionRate = calculateCompletionRate(todos);

                if (completionRate <= 50) {
                    System.out.println("User " + user.getName() + " does not have more than 50% tasks completed.");
                } else {
                    System.out.println("User " + user.getName() + " has more than 50% tasks completed.");
                }
            }
        }
    }

    private List<User> getUsers() {
        Response response = RestAssured.given()
                .log().all()
                .when()
                .get(USERS_URL)
                .then()
                .log().ifError()
                .log().all()
                .extract().response();

        return response.jsonPath().getList("", User.class);
    }

    private List<Todo> getTodosForUser(int userId) {
        Response response = RestAssured.given()
                .log().all()
                .when()
                .get(TODOS_URL + "?userId=" + userId)
                .then()
                .log().ifError()
                .log().all()
                .extract().response();

        return response.jsonPath().getList("", Todo.class);
    }

    private boolean isFanCodeCity(User user) {
        double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
        double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
        return lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100;
    }

    private double calculateCompletionRate(List<Todo> todos) {
        if (todos.isEmpty()) {
            return 0.0; // Handle case where user has no todos
        }

        int completedCount = 0;
        for (Todo todo : todos) {
            if (todo.isCompleted()) {
                completedCount++;
            }
        }

        return (double) completedCount / todos.size() * 100;
    }
}
