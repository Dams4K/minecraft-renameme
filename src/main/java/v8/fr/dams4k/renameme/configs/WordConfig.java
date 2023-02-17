package fr.dams4k.renameme.configs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.dams4k.renameme.References;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class WordConfig {
    private Configuration config;
   
    private int id;

    private static final String D_ORIGINAL_WORD = "";
    private static final String D_FINAL_WORD = "";

    public static final String CATEGORY_GENERAL = "general";

    private String originalWord = D_ORIGINAL_WORD;
    private String finalWord = D_FINAL_WORD;

    private Property originalWordProperty;
    private Property finalWordProperty;

    public WordConfig(int id) {
        this.id = id;

        Path path = Paths.get(ModConfig.MOD_FOLDER, Integer.toString(id) + ".cfg");
        File configFile = new File(path.toString());

        config = new Configuration(configFile, References.MOD_VERSION);
        this.load();
    }

    public String format(String str) {
        Pattern pattern = Pattern.compile(this.getOriginalWord());
        Matcher matcher = pattern.matcher(str);

        StringBuilder builder = new StringBuilder();

        int lastIndex = 0;
        while (matcher.find()) {
            String original = matcher.group();
            builder.append(str.subSequence(lastIndex, matcher.start()));
            builder.append(this.getFinalWord(original));
            lastIndex = matcher.end();
        }
        builder.append(str.subSequence(lastIndex, str.length()));

        return builder.toString();
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

    public int getId() {
        return id;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord.replace("&", "§");
        this.save();
    }
    public void setFinalWord(String finalWord) {
        this.finalWord = finalWord.replace("&", "§");
        this.save();
    }

    public String getOriginalWord() {
        return replaceTokens(this.originalWord, Pattern.compile("\\$\\{(?<placeholder>[A-Za-z0-9-_]+)}"), match -> this.getPlaceholderValues().get(match.group("placeholder")));
    }
    public String getUnformattedOriginalWord() {
        return originalWord.replace("§", "&");
    }
    public String getFinalWord(String original) {
        Map<String, String> placeholderValues = this.getPlaceholderValues();
        placeholderValues.put("original", original);

        return replaceTokens(this.finalWord, Pattern.compile("\\$\\{(?<placeholder>[A-Za-z0-9-_]+)}"), match -> placeholderValues.get(match.group("placeholder")));
    }

    public String getUnformattedFinalWord() {
        return finalWord.replace("§", "&");
    }

    //- Thanks to Ashley Frieze for this method, very cool
    public static String replaceTokens(String original, Pattern tokenPattern, Function<Matcher, String> converter) {
        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = tokenPattern.matcher(original);
        while (matcher.find()) {
            output.append(original, lastIndex, matcher.start())
                    .append(converter.apply(matcher));

            lastIndex = matcher.end();
        }
        if (lastIndex < original.length()) {
            output.append(original, lastIndex, original.length());
        }
        return output.toString();
    }

    public Map<String, String> getPlaceholderValues() {
        Map<String, String> placeholderValues = new HashMap<>();
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer != null) {
            placeholderValues.put("username", mc.thePlayer.getName());
            placeholderValues.put("uuid", mc.thePlayer.getUniqueID().toString());
            placeholderValues.put("customnametag", mc.thePlayer.getCustomNameTag());
            placeholderValues.put("displayname", mc.thePlayer.getDisplayNameString());
            placeholderValues.put("health", Float.toString(mc.thePlayer.getHealth()));
            placeholderValues.put("saturation", Float.toString(mc.thePlayer.getFoodStats().getSaturationLevel()));
        }

        if (mc.theWorld != null) {
            placeholderValues.put("worldname", mc.theWorld.getProviderName());
        }

        if (mc.getCurrentServerData() != null) {
            placeholderValues.put("serverip", mc.getCurrentServerData().serverIP);
            placeholderValues.put("servername", mc.getCurrentServerData().serverName);
        }

        return placeholderValues;
    }

    public boolean isDefault() {
        return D_ORIGINAL_WORD == this.originalWord && D_FINAL_WORD == this.finalWord;
    }

    public void delete() {
        config.getConfigFile().delete();
    }
}
