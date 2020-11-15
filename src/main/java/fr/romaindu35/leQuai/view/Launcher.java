package fr.romaindu35.leQuai.view;


import fr.flowarg.openlauncherlib.NewForgeVersionDiscriminator;
import fr.romaindu35.leQuai.Main;
import fr.romaindu35.leQuai.util.LaunchMinecraft;
import fr.romaindu35.leQuai.util.Mods;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.minecraft.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.UUID;

public class Launcher {

    @FXML
    private TextField pseudo;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label step;

    private int maxValueDownload;
    private int valueDownload;
    private double maxValueDownloadDouble;
    private double valueDownloadDouble;
    private String labelText;

    public static GameInfos infos = new GameInfos("leQuai", new GameVersion("1.16.3", GameType.V1_13_HIGER_FORGE.setNewForgeVersionDiscriminator(
            new NewForgeVersionDiscriminator("35.0.17", "1.16.4", "net.minecraftforge", "20201102.104115"))),
            new GameTweak[] {});

    public void inconified() {
        Main.getStagePrincipal().setIconified(true);
    }
    public void close() {
        Main.getStagePrincipal().close();
    }

    public void play() {
        if (pseudo.getText() == null || pseudo.getLength() == 0) {
            error("Erreur", "Veuillez renseigner un pseudo");
            return;
        }
        if (pseudo.getLength() <4 ) {
            error("Erreur", "Votre pseudo doit contenir plus de 3 caractère");
            return;
        }
        System.out.println("ok");
        update(pseudo.getText());
    }

    public void update(String pseudo) {
        Mods.deleteMod(new File(infos.getGameDir() + "/mods/"));
        progressBar.setVisible(true);
        step.setVisible(true);
        Thread thread = new Thread("launch") {
            @Override
            public void run() {

                AuthInfos authInfos = new AuthInfos(pseudo, "", UUID.randomUUID().toString());
                try {
                    LaunchMinecraft.updateForge(new File(String.valueOf(infos.getGameDir())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    LaunchMinecraft.LaunchMinecraft(infos, authInfos);
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            Main.getStagePrincipal().close();
                            this.interrupt();
                        }
                    };

                    if (Platform.isFxApplicationThread()) {
                        t.start();
                    } else {
                        Platform.runLater(t);
                    }
                } catch (LaunchException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void error(String title, String headerText) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(headerText);

        error.showAndWait();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Label getStep() {
        return step;
    }

    public synchronized void initialise() {
        this.step.setText(this.labelText + " : " + valueDownload + "/" + maxValueDownload);
        if(maxValueDownload != 0)
            this.valueDownloadDouble = valueDownload * 100 / maxValueDownload;
        this.progressBar.setProgress(valueDownloadDouble / 100);
    }

    public synchronized void setValue(int valueDownload, int maxValueDownload) {
        this.valueDownload = valueDownload;
        this.maxValueDownload = maxValueDownload - 1;
        initialise();
    }

    public synchronized void setText(String text) {
        this.labelText = text;
        initialise();
    }
}