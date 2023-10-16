package com.example.jdbctemplate.dao;

import com.example.jdbctemplate.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private Scanner scanner;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.scanner = new Scanner(System.in);
    }

    //добавления User в бд
    public void addUser() {
        System.out.println("Введите данные нового пользователя:");
        System.out.print("Логин: ");
        String login = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

       // System.out.print("Дата создания (гггг-мм-дд): ");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDate = dateFormat.format(new Date());

        String sql = "INSERT INTO users (login, password, email, creation_date) VALUES (?, ?, ?, ?::timestamp)";
        jdbcTemplate.update(sql, login, password, email, creationDate);

        System.out.println("Пользователь успешно добавлен в базу данных.");
    }

    public void deleteUser() {//удаление User из бд
        System.out.print("Введите ID пользователя, которого хотите удалить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        //запрос на удалдение
        String sql = "DELETE FROM users WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected > 0) {
            System.out.println("Пользователь успешно удален из базы данных.");
        } else {
            System.out.println("Пользователь с указанным ID не найден в базе данных.");
        }
    }

    public void editUser() {
        System.out.print("Введите ID пользователя, которого хотите отредактировать: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        //Метод jdbcTemplate.queryForObject() используется для выполнения запроса к базе данных
        // и получения одной строки результата в виде объекта User
        //параметры метода queryForObject
        // sql запрос
        //new Object[]{id} массив параметром - то что ставиться вместо знакомв ?
        //getUserRowMapper - меод мапинга в объект полученных данных возвращает RowMapper<User>

        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, getUserRowMapper());

        if (user != null) {
            System.out.println("Выбранный пользователь: " + user);
            System.out.println("Введите новые данные:");

            System.out.print("Логин: ");
            String login = scanner.nextLine();
            System.out.print("Пароль: ");
            String password = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Дата создания (гггг-мм-дд): ");
            String creation_date = scanner.nextLine();

            //запрос на обновление
            sql = "UPDATE users " +
                    "SET login = ?, password = ?, email = ?, creation_date = ?::timestamp WHERE id = ?";
            jdbcTemplate.update(sql, login, password, email, creation_date, id);

            System.out.println("Пользователь успешно отредактирован.");
        } else {
            System.out.println("Пользователь с указанным ID не найден в базе данных.");
        }
    }

    public void getUserInfo() {
        System.out.print("Введите ID или имя пользователя: ");
        String input = scanner.nextLine();
        int id = -1;

        //проверка число ли это
        boolean isNumeric = input.chars().allMatch( Character::isDigit );
        if (isNumeric) id = Integer.parseInt(input);

        //запрос на получение списка пользователей по имени или id
        String sql = "SELECT * FROM users WHERE id = ? OR login = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{id, input}, getUserRowMapper());

        if (!users.isEmpty()) {
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    public void getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, getUserRowMapper());

        if (!users.isEmpty()) {
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Нет пользователей в базе данных.");
        }
    }

    private RowMapper<User> getUserRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setCreationDate(resultSet.getString("creation_date"));
                return user;
            }
        };
    }

}