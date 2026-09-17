package com.example.home2608;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    private static final String URL = "jdbc:postgresql://localhost:5432/top_top";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    @FXML
    private TextField aField;

    @FXML
    private TextField bField;

    @FXML
    private Label resultLabel;

    @FXML
    protected void onPlus() {
        try {
            int a = Integer.parseInt(aField.getText());
            int b = Integer.parseInt(bField.getText());
            int c = a + b;
            saveCalc(a, b, c);
            resultLabel.setText(String.valueOf(getLastCalc()));
        } catch (NumberFormatException e) {
            resultLabel.setText("Введите целые числа");
        } catch (SQLException e) {
            System.out.println("Ошибка БД: " + e.getMessage());
        }
    }

    @FXML
    protected void onMinus() {
        try {
            int a = Integer.parseInt(aField.getText());
            int b = Integer.parseInt(bField.getText());
            int dif = a - b;
            saveMinus(a, b, dif);
            resultLabel.setText(String.valueOf(getLastMinus()));
        } catch (NumberFormatException e) {
            resultLabel.setText("Введите целые числа");
        } catch (SQLException e) {
            System.out.println("Ошибка БД: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void saveCalc(int a, int b, int c) throws SQLException {
        String sql = "INSERT INTO calc (a, b, c) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, a);
            statement.setInt(2, b);
            statement.setInt(3, c);
            statement.executeUpdate();
        }
    }

    private void saveMinus(int a, int b, int dif) throws SQLException {
        String sql = "INSERT INTO minus (a, b, dif) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, a);
            statement.setInt(2, b);
            statement.setInt(3, dif);
            statement.executeUpdate();
        }
    }

    private int getLastCalc() throws SQLException {
        String sql = "SELECT c FROM calc ORDER BY id DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("c");
            }
            return 0;
        }
    }

    private int getLastMinus() throws SQLException {
        String sql = "SELECT dif FROM minus ORDER BY id DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("dif");
            }
            return 0;
        }
    }
}