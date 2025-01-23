package com.statway.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppBlockedController implements Initializable {
    public Label contactoLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactoLabel.setOnMouseClicked(e -> {
            openWhatsapp();
            System.exit(0);
        });

    }

    private void openWhatsapp(){
        String whatsappUrl = "https://wa.me/868660661";
        try {
            Desktop.getDesktop().browse(new URI(whatsappUrl));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
