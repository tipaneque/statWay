module com.statway {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.base;
    requires com.jfoenix;
    requires java.prefs;

    exports com.statway.applications;
    opens com.statway.applications to javafx.fxml;
    exports com.statway.controllers;
    opens com.statway.controllers to javafx.fxml;
    exports com.statway.models;
    opens com.statway.models to javafx.base;
}