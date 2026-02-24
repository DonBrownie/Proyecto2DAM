module es.cifpcarlos3.tarea401 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires log4j;
    requires org.xerial.sqlitejdbc;
    requires jasperreports;
    requires jdk.jdi;
    requires javafx.graphics;
    requires java.net.http;
    requires com.google.gson;

    opens es.cifpcarlos3.tarea401 to javafx.fxml;
    opens es.cifpcarlos3.tarea401.controller to javafx.fxml;
    opens es.cifpcarlos3.tarea401.model to com.google.gson;

    exports es.cifpcarlos3.tarea401;
    exports es.cifpcarlos3.tarea401.controller;
    exports es.cifpcarlos3.tarea401.model;
}