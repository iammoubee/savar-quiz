package com.savarquiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.savarquiz.util.DBConnection;

public class ParticipantDAO {

    public int addParticipant(String name, String roll) {
        String sql = "INSERT INTO participants (name, roll) VALUES (?, ?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION FAILED");
                System.out.println("=================================");
                return -1;
            }
            
            PreparedStatement statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            
            statement.setString(1, name);
            statement.setString(2, roll);
            
            statement.executeUpdate();
            
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int participantId = -1;
            
            if (generatedKeys.next()) {
                participantId = generatedKeys.getInt(1);
                
                System.out.println("=================================");
                System.out.println("✓ Participant registered");
                System.out.println("ID: " + participantId);
                System.out.println("Name: " + name);
                System.out.println("Roll: " + roll);
                System.out.println("=================================");
            }
            
            generatedKeys.close();
            statement.close();
            connection.close();
            
            return participantId;
            
        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println("ERROR ADDING PARTICIPANT!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=================================");
            return -1;
        }
    }
    
    public String getParticipantName(int participantId) {
        String sql = "SELECT name FROM participants WHERE id = ?";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                return "Unknown";
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, participantId);
            
            ResultSet resultSet = statement.executeQuery();
            String name = "Unknown";
            
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
            
            resultSet.close();
            statement.close();
            connection.close();
            
            return name;
            
        } catch (Exception e) {
            System.out.println("Error getting participant name: " + e.getMessage());
            return "Unknown";
        }
    }
}
