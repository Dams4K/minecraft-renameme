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
    
    private static List<String> getFilesFrom(String path) {
        File dir = new File(path);
        if (!dir.isDirectory()) return new ArrayList<>();

        return Stream.of(dir.listFiles())
            .filter(file -> file.isFile())
            .filter(file -> file.getName().endsWith(".cfg"))
            .map(File::getName)
            .collect(Collectors.toList());
    }

    public static void loadWordConfigs() {
        wordConfigs.clear();

        List<String> configFilenames = ModConfig.getFilesFrom(MOD_FOLDER);
        for (String filename : configFilenames) {
            String sId = filename.replace(".cfg", "");
            try {
                int id = Integer.parseInt(sId);

                WordConfig wordConfig = new WordConfig(id);
                if (wordConfig.isDefault()) {
                    wordConfig.delete();
                } else {
                    wordConfigs.add(wordConfig);
                }

            } catch (NumberFormatException e) {}
        }
    }

    public static WordConfig createNewWordConfig() {
        int id = 0;
        for (WordConfig wordConfig : wordConfigs) {
            if (id < wordConfig.getId()) {
                id = wordConfig.getId();
            }
        }
        WordConfig newWordConfig = new WordConfig(id+1);
        wordConfigs.add(newWordConfig);
        return newWordConfig;
    }
}
