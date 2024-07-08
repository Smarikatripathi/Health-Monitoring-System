module com.smarikatripathi.healthmonitoringsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires servlet.api;
    requires org.json;
    requires java.sql;
    requires javafx.base;

    opens com.smarikatripathi.healthmonitoringsystem to javafx.fxml;
    exports com.smarikatripathi.healthmonitoringsystem;

    opens com.smarikatripathi.healthmonitoringsystem.classes to javafx.base;
    exports com.smarikatripathi.healthmonitoringsystem.classes;
}