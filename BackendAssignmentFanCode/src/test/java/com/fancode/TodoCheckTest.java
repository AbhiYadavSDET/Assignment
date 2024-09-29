package com.fancode;


import org.testng.annotations.Test;

public class TodoCheckTest {

    @Test
    public void testCheckTodosForFanCodeUsers() {
        TodoCheck todoCheck = new TodoCheck();
        todoCheck.checkTodosForFanCodeUsers();
    }
}
