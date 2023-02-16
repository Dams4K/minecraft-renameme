package fr.dams4k.renameme.configs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.dams4k.renameme.References;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WordConfig {
    private Configuration config;
   
    private static final String D_ORIGINAL_WORD = "";
    private static final String D_FINAL_WORD = "";

    public static final String CATEGORY_GENERAL = "general";

    private String originalWord = D_ORIGINAL_WORD;
    private String finalWord = D_FINAL_WORD;

    private Property originalWordProperty;
    private Property finalWordProperty;

    public WordConfig(int id) {
        Path path = Paths.get(ModConfig.MOD_FOLDER, Integer.toString(id) + ".cfg");
        File configFile = new File(path.toString());

        config = new Configuration(configFile, References.MOD_VERSION);
        this.load();
    }

    public WordConfig(File file) {
        config = new Configuration(file, References.MOD_VERSION);
        this.load();
    }

    public void load() {
        originalWordProperty = config.get(CATEGORY_GENERAL, "original_word", D_ORIGINAL_WORD);
        finalWordProperty = config.get(CATEGORY_GENERAL, "final_word", D_FINAL_WORD);

        originalWord = originalWordProperty.getString();
        finalWord = finalWordProperty.getString();
    }
    public void save() {
        originalWordProperty.set(originalWord);
        finalWordProperty.set(finalWord);

        if (config.hasChanged()) config.save();
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
        this.save();
    }
    public void setFinalWord(String finalWord) {
        this.finalWord = finalWord;
        this.save();
    }

    public String getOriginalWord() {
        return originalWord;
    }
    public String getFinalWord() {
        return finalWord;
    }

    public boolean isDefault() {
        return D_ORIGINAL_WORD == this.originalWord && D_FINAL_WORD == this.finalWord;
    }
}
