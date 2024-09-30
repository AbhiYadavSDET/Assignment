package com.fancode;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TodoCheckTest {

    @Test
    public void testCheckTodosForFanCodeUsers() {
        TodoCheck todoCheck = new TodoCheck();
        List<String> results = todoCheck.checkTodosForFanCodeUsers();

        boolean hasLessThan50 = false;
        boolean hasMoreThan50 = false;

        for (String result : results) {
            if (result.contains("does not have more than 50% tasks completed")) {
                hasLessThan50 = true;
            }
            if (result.contains("has more than 50% tasks completed")) {
                hasMoreThan50 = true;
            }
        }
        Assert.assertTrue(hasLessThan50, "Expected at least one user to have less than 50% tasks completed");
        Assert.assertTrue(hasMoreThan50, "Expected at least one user to have more than 50% tasks completed");
    }
}
