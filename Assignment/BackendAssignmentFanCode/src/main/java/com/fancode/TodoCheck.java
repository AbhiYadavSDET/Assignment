package com.fancode;

import com.fancode.Utils.APIUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoCheck {

    private static final String USERS_URL = "http://jsonplaceholder.typicode.com/users";
    private static final String TODOS_URL = "http://jsonplaceholder.typicode.com/todos";


    public List<String> checkTodosForFanCodeUsers() {
        List<String> results = new ArrayList<>();

        // Fetch the users
        Response usersResponse = APIUtils.getRequest(USERS_URL);
        JsonPath usersJsonPath = usersResponse.jsonPath();

        // Iterate over the list of users
        List<Map<String, Object>> users = usersJsonPath.getList("");

        for (Map<String, Object> user : users) {
            // Extract address and geo information dynamically from the response
            Map<String, Object> address = (Map<String, Object>) user.get("address");
            Map<String, Object> geo = (Map<String, Object>) address.get("geo");

            double lat = Double.parseDouble(geo.get("lat").toString());
            double lng = Double.parseDouble(geo.get("lng").toString());

            // Check if user is from FanCode city
            if (lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100) {
                String userName = (String) user.get("name");
                int userId = (Integer) user.get("id");

                // Fetch todos for the current user
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("userId", userId);

                Response todosResponse = APIUtils.getRequest(TODOS_URL, queryParams);
                JsonPath todosJsonPath = todosResponse.jsonPath();
                List<Map<String, Object>> todos = todosJsonPath.getList("");

                double completionRate = calculateCompletionRate(todos);

                if (completionRate <= 50) {
                    results.add("User " + userName + " does not have more than 50% tasks completed.");
                } else {
                    results.add("User " + userName + " has more than 50% tasks completed.");
                }
            }
        }
        return results;  // Return the results list
    }

    private double calculateCompletionRate(List<Map<String, Object>> todos) {
        if (todos.isEmpty()) {
            return 0.0;
        }

        int completedCount = 0;
        for (Map<String, Object> todo : todos) {
            boolean isCompleted = (Boolean) todo.get("completed");
            if (isCompleted) {
                completedCount++;
            }
        }

        return (double) completedCount / todos.size() * 100;
    }
}
