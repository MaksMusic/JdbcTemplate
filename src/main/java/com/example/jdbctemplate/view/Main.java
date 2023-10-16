package com.example.jdbctemplate.view;

import com.example.jdbctemplate.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        // Параметры подключения к базе данных
        //чтение конфигураций из config.properties
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get("src/config.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        //создание JdbcTemplate и его настройка
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(new DriverManagerDataSource(url, username, password));

        UserDao userManagement = new UserDao(jdbcTemplate);
        Scanner scanner = new Scanner(System.in);

        int choice;
        boolean whilieOn = true;

        while (whilieOn){
            System.out.println("---------------------------------");
            System.out.println("Меню:");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Удалить пользователя");
            System.out.println("3. Редактировать пользователя");
            System.out.println("4. Получить информацию о пользователе");
            System.out.println("5. Получить список всех пользователей");
            System.out.println("6. Выйти");
            System.out.print("Выберите опцию: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userManagement.addUser();
                    break;
                case 2:
                    userManagement.deleteUser();
                    break;
                case 3:
                    userManagement.editUser();
                    break;
                case 4:
                    userManagement.getUserInfo();
                    break;
                case 5:
                    userManagement.getAllUsers();
                    break;
                case 6:
                    System.out.println("Выход из программы...");
                    whilieOn = false;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
                    break;
            }
        }

        scanner.close();
    }
}