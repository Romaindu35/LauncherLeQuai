package fr.romaindu35.leQuai.util;

import fr.flowarg.flowupdater.download.json.CurseModInfos;
import fr.flowarg.flowupdater.download.json.Mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mods {

    public static List<CurseModInfos> curseModInfosList = new ArrayList<>();
    public static List<Mod> modList = new ArrayList<>();

    public static void generateMod() {
        addCurseMod(238222, 3039710, "jei-1.15.2-6.0.3.15.jar");
        addCurseMod(267602, 3035673, "CTM - MC1.15.2-1.1.1.13");
        addCurseMod(223852, 2887674, "StorageDrawers-1.15.2-7.0.2.jar");
        addCurseMod(263420, 3074708, "Xaero's Minimapç v20.25.0 for Forge 1.15.2");
        addCurseMod(245211, 3040732, "TheOneProbe - 1.15-2.0.7");
        //addCurseMod(372196, 3066181, "OptiForge-MC1.15.2-0.1.29");

    }

    public static void addCurseMod(int projectID, int fileID, String modName) {
        curseModInfosList.add(new CurseModInfos(projectID, fileID));
    }

    public static List<CurseModInfos> getCurseModInfosList() {
        generateMod();
        return curseModInfosList;
    }

    public static void addOptifine() {
        modList.add(new Mod("preview_OptiFine_1.15.2_HD_U_G1_pre30.jar",
                "bdbd92e5252c0afc0f22d6d5840f96762e5d01ea",
                5561066,
                "https://www.envium.fr/Envium/Launcher/mods/optifine/OptiFine_1.15.2.jar"));
    }

    public static List<Mod> getModInfosList() {
        return modList;
    }

    public static void deleteMod(File file) {
        //Arrays.stream(file.listFiles()).iterator().forEachRemaining(File::delete);
    }
}
