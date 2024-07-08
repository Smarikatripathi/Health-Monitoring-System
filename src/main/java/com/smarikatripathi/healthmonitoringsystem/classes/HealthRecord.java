package com.smarikatripathi.healthmonitoringsystem.classes;

import javafx.beans.property.*;

public class HealthRecord {
    private final IntegerProperty id;
    private final StringProperty userName;
    private final StringProperty userAddress;
    private final IntegerProperty userAge;
    private final FloatProperty weight;
    private final StringProperty exercise;
    private final StringProperty timestamp;

    public HealthRecord(int id, String userName, String userAddress, int userAge, float weight, String exercise, String timestamp) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.userAddress = new SimpleStringProperty(userAddress);
        this.userAge = new SimpleIntegerProperty(userAge);
        this.weight = new SimpleFloatProperty(weight);
        this.exercise = new SimpleStringProperty(exercise);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public String getUserAddress() {
        return userAddress.get();
    }

    public StringProperty userAddressProperty() {
        return userAddress;
    }

    public int getUserAge() {
        return userAge.get();
    }

    public IntegerProperty userAgeProperty() {
        return userAge;
    }

    public float getWeight() {
        return weight.get();
    }

    public FloatProperty weightProperty() {
        return weight;
    }

    public String getExercise() {
        return exercise.get();
    }

    public StringProperty exerciseProperty() {
        return exercise;
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }
}
