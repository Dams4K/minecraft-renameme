package fr.dams4k.rmm.v1_8.configs;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.dams4k.rmm.v1_8.utils.JsonSimplifier;
import net.minecraftforge.fml.common.Loader;

public class ModConfig {
    private static final Gson gson = new Gson();
    private static String filePath = Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), "renameme.json").toString();
    public static List<JsonObject> configsJsonObjects = new ArrayList<JsonObject>();

    public static void preInit() {
        loadConfig();
    }

    public static void saveConfig() {
        String jsonString = gson.toJson(configsJsonObjects);
        try {
            jsonString = JsonSimplifier.addIndent(jsonString, 4);

            FileWriter writer = new FileWriter(filePath);
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        try {
            configsJsonObjects = Arrays.asList(JsonSimplifier.readFile(filePath, JsonObject[].class));
            if (configsJsonObjects == null) {
                saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
