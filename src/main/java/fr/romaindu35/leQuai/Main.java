package fr.romaindu35.leQuai;

import fr.romaindu35.leQuai.view.Launcher;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Stage stagePrincipal;
    private static AnchorPane conteneurPrincipal;
    private static FXMLLoader loaderConteneurPrincipal;
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stagePrincipal = primaryStage;
        stagePrincipal.initStyle(StageStyle.UNDECORATED);

        initialisation();

        conteneurPrincipal.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        conteneurPrincipal.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stagePrincipal.setX(event.getScreenX() - xOffset);
                stagePrincipal.setY(event.getScreenY() - yOffset);
            }
        });
        stagePrincipal.getIcons().add(new Image("https://www.envium.fr/Envium/logo_envium.png"));
        stagePrincipal.show();
    }

    private void initialisation() {
        loaderConteneurPrincipal = new FXMLLoader();
        loaderConteneurPrincipal.setLocation(fr.romaindu35.leQuai.Main.class.getResource("/view/launcher.fxml"));

        try {
            this.conteneurPrincipal = loaderConteneurPrincipal.load();
            Launcher controller = loaderConteneurPrincipal.getController();
            controller.getProgressBar().setVisible(false);
            controller.getStep().setVisible(false);
            Scene scene = new Scene(this.conteneurPrincipal);

            stagePrincipal.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public static AnchorPane getConteneurPrincipal() {
        return conteneurPrincipal;
    }

    public static FXMLLoader getLoaderConteneurPrincipal() {
        return loaderConteneurPrincipal;
    }

    public static void stepForge() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setLayoutX(0);
        progressBar.setLayoutY(720-20);
        progressBar.setPrefHeight(20);
        progressBar.setPrefWidth(1290);

        Label labelForge = new Label();
        labelForge.setText("Téléchargement et installation de forge ...");
        labelForge.setLayoutX(1280 / 2 - 125);
        labelForge.setLayoutY(720-20);

        conteneurPrincipal.getChildren().addAll(progressBar, labelForge);
    }
}
