package fr.romaindu35.leQuai.util;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.OptifineInfo;
import fr.flowarg.flowupdater.utils.ModFileDeleter;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.*;
import fr.romaindu35.leQuai.Main;
import fr.romaindu35.leQuai.view.Launcher;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import javafx.application.Platform;

import java.io.File;

public class LaunchMinecraft {

    private static Launcher launcher = Main.getLoaderConteneurPrincipal().getController();

    public synchronized static void updateForge(File dir) throws Exception {
        final VanillaVersion version =
                new VanillaVersion.VanillaVersionBuilder().withVersionType(VersionType.FORGE).withName("1.16.4").build();
        final UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder()
                .withSilentRead(true)
                .withReExtractNatives(false)
                .withInstallOptifineAsMod(true)
                .build();
        final ILogger logger = new Logger("LeQuai", new File(dir, "launcher.log"));
        AbstractForgeVersion forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
                .withForgeVersion("1.16.4-35.0.17")
                .withVanillaVersion(version)
                .withLogger(logger)
                .withProgressCallback(callback)
                .withFileDeleter(new ModFileDeleter(true))
                .withOptifine(new OptifineInfo("1.16.4_HD_U_G5", false))
                .withNoGui(true)
                .build();


        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().
                withVersion(version)
                .withProgressCallback(callback)
                .withLogger(logger)
                .withUpdaterOptions(options)
                .withForgeVersion(forgeVersion)
                .build();

        updater.update(dir);
    }

    public synchronized static void LaunchMinecraft(GameInfos infos, AuthInfos authInfos) throws LaunchException {
        ExternalLaunchProfile profil = MinecraftLauncher.createExternalProfile(infos, new GameFolder("assets", "libraries", "natives", "client.jar"), authInfos);
        ExternalLauncher launcher = new ExternalLauncher(profil);

        launcher.launch();
    }

    public static final IProgressCallback callback = new IProgressCallback() {
        @Override
        public void init(ILogger logger) {

        }

        @Override
        public void step(Step step) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    if (step.name().equals(Step.FORGE.name()))
                        Main.stepForge();
                    System.out.println(step.name());
                    launcher.setText(step.name());
                    this.interrupt();
                }
            };

            if (Platform.isFxApplicationThread()) {
                t.start();
            } else {
                Platform.runLater(t);
            }
        }

        @Override
        public void update(int downloaded, int max) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    launcher.setValue(downloaded, max);
                    this.interrupt();
                }
            };

            if (Platform.isFxApplicationThread()) {
                t.start();
            } else {
                Platform.runLater(t);
            }
        }
    };

}
