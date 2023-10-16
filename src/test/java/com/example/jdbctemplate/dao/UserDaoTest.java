package com.example.jdbctemplate.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void editUser() {
    }

    @Test
    void getUserInfo() {
        String someString = "123123";
        boolean isNumeric = someString.chars().allMatch( Character::isDigit );
        System.out.println(isNumeric);
        Assertions.assertEquals(true,isNumeric);

    }

    @Test
    void getAllUsers() {
    }
}