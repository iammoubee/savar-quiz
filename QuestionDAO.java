package com.savarquiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.savarquiz.model.Question;
import com.savarquiz.util.DBConnection;

public class QuestionDAO {

    public List<Question> getAllQuestions() {

        List<Question> questions = new ArrayList<>();

        String sql = "SELECT * FROM questions ORDER BY id";

        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION FAILED: Connection is null");
                System.out.println("Check if MySQL server is running");
                System.out.println("Check database credentials in DBConnection.java");
                System.out.println("=================================");
                return questions;
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Question question = new Question(
                    resultSet.getInt("id"),
                    resultSet.getString("question"),
                    resultSet.getString("option_a"),
                    resultSet.getString("option_b"),
                    resultSet.getString("option_c"),
                    resultSet.getString("option_d"),
                    resultSet.getString("correct_answer")
                );

                questions.add(question);
            }

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println("=================================");
            System.out.println("QUESTIONS LOADED: " + questions.size());
            System.out.println("=================================");

        } catch (Exception e) {

            System.out.println("=================================");
            System.out.println("DATABASE ERROR!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
            System.out.println("=================================");

            e.printStackTrace();
        }

        return questions;
    }
}