package com.fancode;

import com.fancode.Utils.APIUtils;
import io.restassured.response.Response;
import com.fancode.models.Todo;
import com.fancode.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoCheck {

    private static final String USERS_URL = "http://jsonplaceholder.typicode.com/users";
    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";

    public List<String> checkTodosForFanCodeUsers() {
        List<String> results = new ArrayList<>();

        Response usersResponse = APIUtils.getRequest(USERS_URL); // No query parameters needed
        List<User> users = usersResponse.jsonPath().getList("", User.class);

        for (User user : users) {
            if (isFanCodeCity(user)) {
                // Prepare query parameters for todos
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("userId", user.getId());

                // Get the response for todos for each user
                Response todosResponse = APIUtils.getRequest(TODOS_URL, queryParams);
                List<Todo> todos = todosResponse.jsonPath().getList("", Todo.class);
                double completionRate = calculateCompletionRate(todos);

                if (completionRate <= 50) {
                    results.add("User " + user.getName() + " does not have more than 50% tasks completed.");
                } else {
                    results.add("User " + user.getName() + " has more than 50% tasks completed.");
                }
            }
        }
        return results;
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
