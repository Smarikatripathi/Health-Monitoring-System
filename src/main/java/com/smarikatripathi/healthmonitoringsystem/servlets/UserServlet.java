package com.smarikatripathi.healthmonitoringsystem.servlets;

import com.smarikatripathi.healthmonitoringsystem.classes.DatabaseConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject userJson = new JSONObject();
                userJson.put("id", resultSet.getInt("id"));
                userJson.put("name", resultSet.getString("name"));
                userJson.put("address", resultSet.getString("address"));
                userJson.put("age", resultSet.getInt("age"));
                jsonArray.put(userJson);
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
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String  age = request.getParameter("age");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(name,address,age) VALUES(?,?,?)");
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setInt(3, Integer.valueOf(age));

            int insertData = statement.executeUpdate();
            if (insertData == 1) {
                out.println("user_created_successfully");
            } else {
                out.println("error_while_creating_user");
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
