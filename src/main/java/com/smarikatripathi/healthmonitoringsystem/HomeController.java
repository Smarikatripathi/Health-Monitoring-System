package com.smarikatripathi.healthmonitoringsystem;


import com.smarikatripathi.healthmonitoringsystem.classes.API;
import com.smarikatripathi.healthmonitoringsystem.classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HomeController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, String > address;
    @FXML
    private TableColumn<User, Integer> age;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField ageField;

    @FXML
    private Label responseLabel;


    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<User,String>("address"));
        age.setCellValueFactory(new PropertyValueFactory<User,Integer>("age"));

        tableView.setItems(userList);

        // Fetch products and populate the table
        try {
            String json = API.getData("http://localhost:8080/healthmonitoringsystem/api/users","GET");
            List<User> users = parseUsers(json);
            userList.addAll(users);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<User> parseUsers(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<User> users = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            User user = new User(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("address"),jsonObject.getInt("age"));
            users.add(user);
        }

        return users;
    }
    @FXML
    private void addUser() {
        String name = this.nameField.getText();
        String address = this.addressField.getText();
        String age = this.ageField.getText();
        try{
            String postData = "name=" + name+ "&address="+address + "&age="+ age;

            String response = API.sendPost("http://localhost:8080/healthmonitoringsystem/api/users",postData);
            if(response.equals("user_created_successfully")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add User");
                alert.setHeaderText(null);
                alert.setContentText("User created successfully");
                alert.showAndWait();
                this.nameField.clear();
                this.addressField.clear();
                this.ageField.clear();
                refreshTable();
            }else{
                responseLabel.setText(response);
                responseLabel.setStyle("-fx-text-fill: red");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseLabel.setText("Error while adding user");
        }

    }

    private void refreshTable() {
        try {
            String json = API.getData("http://localhost:8080/healthmonitoringsystem/api/users","GET");
            List<User> users = parseUsers(json);

            // Clear old data and add new data
            userList.clear();
            userList.addAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToHealthRecord() throws IOException{
        App.setRoot("healthRecord");
    }



}
