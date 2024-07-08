package com.smarikatripathi.healthmonitoringsystem;

import com.smarikatripathi.healthmonitoringsystem.classes.API;
import com.smarikatripathi.healthmonitoringsystem.classes.HealthRecord;
import com.smarikatripathi.healthmonitoringsystem.classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class HealthRecordController {

    @FXML
    private TableView<HealthRecord> tableView;
    @FXML
    private TableColumn<HealthRecord, Integer> idColumn;
    @FXML
    private TableColumn<HealthRecord, String> nameColumn;
    @FXML
    private TableColumn<HealthRecord, String> addressColumn;
    @FXML
    private TableColumn<HealthRecord, Integer> ageColumn;
    @FXML
    private TableColumn<HealthRecord, Float> weightColumn;
    @FXML
    private TableColumn<HealthRecord, String> exerciseColumn;
    @FXML
    private TableColumn<HealthRecord, String> timestampColumn;

    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private TextField weightField;
    @FXML
    private TextField exerciseField;
    @FXML
    private TextField timestampField;
    @FXML
    private Label responseLabel;

    @FXML
    public void initialize() {
        // Initialize the table columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().userAddressProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().userAgeProperty().asObject());
        weightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
        exerciseColumn.setCellValueFactory(cellData -> cellData.getValue().exerciseProperty());
        timestampColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());

        // Load user data into the ComboBox
        loadUsers();
        // Load health records into the table
        loadHealthRecords();
    }

    private void loadUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            String json = API.getData("http://localhost:8080/healthmonitoringsystem/api/users","GET");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = new User(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("address"),jsonObject.getInt("age"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        userComboBox.setItems(usersList);

        userComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getName(); // Display user's name in ComboBox
            }

            @Override
            public User fromString(String string) {
                return null; // Not needed for ComboBox
            }
        });
    }

    @FXML
    private void addHealthRecord() {
        User selectedUser = userComboBox.getValue();
        String weight = weightField.getText();
        String exercise = exerciseField.getText();
        String timestamp = timestampField.getText();

        if (selectedUser == null || weight.isEmpty() || exercise.isEmpty() || timestamp.isEmpty()) {
            responseLabel.setText("All fields are required!");
            return;
        }

        try {
            String postData = "userId=" + selectedUser.getId()+ "&weight="+Float.parseFloat(weight) + "&exercise="+ exercise + "&timestamp="+timestamp;

            String response = API.sendPost("http://localhost:8080/healthmonitoringsystem/api/healthRecord",postData);
            if(response.equals("health_record_created_successfully")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Health Record");
                alert.setHeaderText(null);
                alert.setContentText("Health record successfully");
                alert.showAndWait();
                this.weightField.clear();
                this.exerciseField.clear();
                this.timestampField.clear();
            }else{
                responseLabel.setText(response);
                responseLabel.setStyle("-fx-text-fill: red");
            }
            loadHealthRecords();
        } catch (SQLException e) {
            e.printStackTrace();
            responseLabel.setText("Database error: " + e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            responseLabel.setText(" error: " + e.getMessage());
        }
    }

    private void loadHealthRecords() {
        ObservableList<HealthRecord> healthRecordList = FXCollections.observableArrayList();
        try{
            String json = API.getData("http://localhost:8080/healthmonitoringsystem/api/healthRecord","GET");

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject resultSet = jsonArray.getJSONObject(i);
                int id = resultSet.getInt("id");
                String userName = resultSet.getString("userName");
                String userAddress = resultSet.getString("userAddress");
                int userAge = resultSet.getInt("userAge");
                float weight = resultSet.getFloat("weight");
                String exercise = resultSet.getString("exercise");
                String timestamp = resultSet.getString("timestamp");
                healthRecordList.add(new HealthRecord(id, userName, userAddress, userAge, weight, exercise, timestamp));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.setItems(healthRecordList);
    }

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

}
