package com.smarikatripathi.healthmonitoringsystem.servlets;

import com.smarikatripathi.healthmonitoringsystem.classes.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthRecordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement  statement = connection.prepareStatement("SELECT hr.id, hr.weight, hr.exercise, hr.timestamp, u.name, u.address, u.age " +
                    "FROM healthRecord hr JOIN users u ON hr.user_id = u.id");
            ResultSet resultSet = statement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("id", resultSet.getInt("id"));
                recordJson.put("userName", resultSet.getString("name"));
                recordJson.put("userAddress", resultSet.getString("address"));
                recordJson.put("userAge", resultSet.getInt("age"));
                recordJson.put("weight", resultSet.getFloat("weight"));
                recordJson.put("exercise", resultSet.getString("exercise"));
                recordJson.put("timestamp", resultSet.getString("timestamp"));
                jsonArray.put(recordJson);
            }

            out.print(jsonArray.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String weight = request.getParameter("weight");
        String exercise = request.getParameter("exercise");
        String timestamp = request.getParameter("timestamp");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO healthRecord(user_id, weight, exercise, timestamp) VALUES(?, ?, ?, ?)");
            statement.setInt(1, Integer.parseInt(userId));
            statement.setFloat(2, Float.parseFloat(weight));
            statement.setString(3, exercise);
            statement.setString(4, timestamp);

            int insertData = statement.executeUpdate();
            if (insertData == 1) {
                out.println("health_record_created_successfully");
            } else {
                out.println("error_while_creating_health_record");
            }
        }catch (SQLException e){
            e.printStackTrace();

        }catch (Exception e){

            e.printStackTrace();

        }finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

}
