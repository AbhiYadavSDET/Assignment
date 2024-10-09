package com.fancode;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TodoCheckTest {

    @Test
    public void testCheckTodosForFanCodeUsers() {
        // Initialize the TodoCheck class
        TodoCheck todoCheck = new TodoCheck();

        // Get the results from the checkTodosForFanCodeUsers method
        List<String> results = todoCheck.checkTodosForFanCodeUsers();

        // SoftAssert allows multiple assertions to be checked together
        SoftAssert softAssert = new SoftAssert();

        // Flags to track the task completion status
        boolean hasLessThan50 = false;
        boolean hasMoreThan50 = false;

        // Iterate through the results to check the task completion percentage
        for (String result : results) {
            if (result.contains("does not have more than 50% tasks completed")) {
                hasLessThan50 = true;
            }
            if (result.contains("has more than 50% tasks completed")) {
                hasMoreThan50 = true;
            }
        }

     /*   // Perform soft assertions
        softAssert.assertTrue(hasLessThan50, "Expected at least one user to have less than 50% tasks completed");
        softAssert.assertTrue(hasMoreThan50, "Expected at least one user to have more than 50% tasks completed");

        // Collate all the soft assertions and trigger failures if any
        softAssert.assertAll();*/
    }
}
