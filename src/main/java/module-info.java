module com.statway {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires javafx.base;
    requires com.jfoenix;
    requires java.prefs;
    requires java.desktop;

    exports com.statway.applications;
    opens com.statway.applications to javafx.fxml;
    exports com.statway.controllers;
    opens com.statway.controllers to javafx.fxml;
    exports com.statway.models;
    opens com.statway.models to javafx.base;
}