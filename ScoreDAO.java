package com.savarquiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.savarquiz.util.DBConnection;

public class ScoreDAO {

    public boolean addScore(int participantId, int score, int total) {
        String sql = "INSERT INTO scores (participant_id, score, total) VALUES (?, ?, ?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.out.println("=================================");
                System.out.println("DATABASE CONNECTION FAILED");
                System.out.println("=================================");
                return false;
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, participantId);
            statement.setInt(2, score);
            statement.setInt(3, total);
            
            statement.executeUpdate();
            
            System.out.println("=================================");
            System.out.println("✓ Score saved");
            System.out.println("Participant ID: " + participantId);
            System.out.println("Score: " + score + "/" + total);
            System.out.println("=================================");
            
            statement.close();
            connection.close();
            
            return true;
            
        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println("ERROR SAVING SCORE!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=================================");
            return false;
        }
    }
    
    public List<Map<String, Object>> getLeaderboard() {
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        
        String sql = "SELECT p.id, p.name, p.roll, s.score, s.total, s.attempted_at " +
                     "FROM participants p " +
                     "LEFT JOIN scores s ON p.id = s.participant_id " +
                     "WHERE s.id IN (" +
                     "  SELECT MAX(id) FROM scores GROUP BY participant_id" +
                     ") OR s.id IS NULL " +
                     "ORDER BY s.score DESC, s.attempted_at DESC";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.out.println("DATABASE CONNECTION FAILED");
                return leaderboard;
            }
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", resultSet.getInt("id"));
                row.put("name", resultSet.getString("name"));
                row.put("roll", resultSet.getString("roll"));
                row.put("score", resultSet.getObject("score")); // can be null
                row.put("total", resultSet.getObject("total")); // can be null
                row.put("attempted_at", resultSet.getTimestamp("attempted_at"));
                
                leaderboard.add(row);
            }
            
            System.out.println("=================================");
            System.out.println("✓ Leaderboard loaded: " + leaderboard.size() + " participants");
            System.out.println("=================================");
            
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println("ERROR LOADING LEADERBOARD!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=================================");
        }
        
        return leaderboard;
    }
}
