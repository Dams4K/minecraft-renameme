package fr.dams4k.renameme.configs;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.dams4k.renameme.References;
import net.minecraftforge.fml.common.Loader;

public class ModConfig {
    public final static String MOD_FOLDER = Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), References.MOD_ID).toString();

    public static List<WordConfig> wordConfigs = new ArrayList<>();

    public static void preInit() {
        loadWordConfigs();
    }
    
    private static List<File> getFilesFrom(String path) {
        File dir = new File(path);
        if (!dir.isDirectory()) return new ArrayList<>();

        return Stream.of(dir.listFiles()).filter(file -> file.isFile()).filter(file -> file.getName().endsWith(".cfg")).collect(Collectors.toList());
    }

    public static void loadWordConfigs() {
        List<File> configFiles = ModConfig.getFilesFrom(MOD_FOLDER);

        for (File configFile : configFiles) {
            WordConfig wordConfig;
            wordConfig = new WordConfig(configFile);

            if (wordConfig.isDefault()) continue;
            
            wordConfigs.add(wordConfig);
        }
    }
}
