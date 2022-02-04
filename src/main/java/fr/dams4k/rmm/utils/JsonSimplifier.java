package fr.dams4k.rmm.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.apache.commons.io.IOUtils;

public class JsonSimplifier {
    private static String increaseIndentChar = "{[";
    private static String decreaseIndentChar = "}]";
    private static Gson gson = new Gson();

    public static <T> List<T> readFileForList(String path, Class<T[]> clazz) throws Exception {
        FileReader fr = new FileReader(path);
        T[] arr = gson.fromJson(fr, clazz);
        return java.util.Arrays.asList(arr);
    }


    public static <T> T readFile(String path, Class<T> clazz) throws Exception {
        FileReader fr = new FileReader(path);
        return gson.fromJson(fr, clazz);
    }

    
    public static <T> List<T> readResourcesFile(String path, Class<T[]> clazz) throws Exception {
        T[] arr = gson.fromJson(IOUtils.toString(new URL(path), StandardCharsets.UTF_8), clazz);
        return  java.util.Arrays.asList(arr);
    }

    public static <T> List<T> readStringForList(JsonElement jsonElement, Class<T[]> clazz) {
        T[] arr = gson.fromJson(jsonElement, clazz);
        return Arrays.asList(arr);
    }

    public static <T> Object readString(JsonElement jsonElement, Class<T> clazz) {
        return gson.fromJson(jsonElement, clazz);
    }


    public static void writeFile(String path, Object object) throws IOException {
        String jsonString = gson.toJson(object);
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(jsonString);
        fileWriter.close();
    }


    // I don't found a way to add indentation to a json si i tried to make one ahaha
    public static String addIndent(String str, int indentSize) {
        String indent = String.join("", Collections.nCopies(indentSize, " "));

        String strIndented = ""; 
        Integer indentLevel = 0;
        boolean inItem = false;

        for (int i = 0; i < str.length(); i++) {
            String seq = str.subSequence(i, i+1).toString();
            
            if (seq.equalsIgnoreCase("\"") && !str.subSequence(i-1, i+1).toString().equalsIgnoreCase("\\\"")) inItem = !inItem;
            
            if (!inItem) {
                if (increaseIndentChar.contains(seq) && i <= str.length()-1 && !decreaseIndentChar.contains(str.subSequence(i+1, i+2))) {
                    indentLevel++;
                    seq += "\n" + String.join("", Collections.nCopies(indentLevel, indent));
                } else if (decreaseIndentChar.contains(seq) && i > 0 && !increaseIndentChar.contains(str.subSequence(i-1, i))) {
                    indentLevel--;
                    seq = "\n" + String.join("", Collections.nCopies(indentLevel, indent)) + seq;
                } else if (seq.equalsIgnoreCase(",")) {
                    seq += "\n" + String.join("", Collections.nCopies(indentLevel, indent));
                } else if (seq.equalsIgnoreCase(":")) {
                    seq += " ";
                }
            }
            
            strIndented += seq;
        }

        return strIndented;
    }
}
