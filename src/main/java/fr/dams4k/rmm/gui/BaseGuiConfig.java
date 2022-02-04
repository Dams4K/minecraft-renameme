package fr.dams4k.rmm.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;

import fr.dams4k.rmm.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class BaseGuiConfig extends GuiScreen {
    public List<JsonObject> jsonGuiConfig;

    public static BaseGuiConfig instance;

    public HashMap<Integer, GuiTextField> guiTextFieldArray;
    public ArrayList<GuiText> guiTexts;
    
    @Override
    public void initGui() {
        if (jsonGuiConfig == null) return;

        guiTextFieldArray = new HashMap<Integer, GuiTextField>();
        guiTexts = new ArrayList<GuiText>();

        try {
            for (JsonObject e : jsonGuiConfig) {
                String eType = e.get("type").getAsString();
                String eText = null;
                int eWidth = 0;
                int eHeight = 0;
                int ePosX = 0;
                int ePosY = 0;
                int eId = 0;
                String eColor = null;

                if (e.get("text") != null) eText = e.get("text").getAsString();
                if (e.get("width") != null) eWidth = e.get("width").getAsInt();
                if (e.get("height") != null) eHeight =  e.get("height").getAsInt();
                if (e.get("posX") != null) ePosX = (int) Utils.eval(formatString(e.get("posX").getAsString(), eWidth, eHeight, eText));
                if (e.get("posY") != null) ePosY = (int) Utils.eval(formatString(e.get("posY").getAsString(), eWidth, eHeight, eText));
                if (e.get("id") != null) eId = e.get("id").getAsInt();
                if (e.get("color") != null) eColor = e.get("color").getAsString();

                if (GuiTextField.class.toString().equalsIgnoreCase(eType)) {
                    GuiTextField newGuiTextField = new GuiTextField(
                        eId, fontRendererObj, 
                        ePosX,
                        ePosY,
                        eWidth,
                        eHeight
                    );
                    if (eText != null) newGuiTextField.setText(eText);
                    guiTextFieldArray.put(eId, newGuiTextField);
                } else if (GuiButtonExt.class.toString().equalsIgnoreCase(eType)) {
                    GuiButtonExt newGuiButtonExt = new GuiButtonExt(eId, ePosX, ePosY, eWidth, eHeight, eText);
                    buttonList.add(newGuiButtonExt);
                } else if (GuiText.class.toString().equalsIgnoreCase(eType)) {
                    GuiText newGuiText = new GuiText(eText, ePosX, ePosY, eColor);
                    guiTexts.add(newGuiText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (GuiTextField e : guiTextFieldArray.values()) {
            e.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiTextField e : guiTextFieldArray.values()) {
            e.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        for (GuiTextField e : guiTextFieldArray.values()) {
            e.updateCursorCounter();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        for (GuiTextField e : guiTextFieldArray.values()) {
            e.drawTextBox();
        }
        for (GuiText e : guiTexts) {
            e.drawString(this);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private String formatString(String str, Integer elementWidth, Integer elementHeight, String text) {
        str = str.replaceAll("(?i)\\{w\\}", Integer.toString(width)); // display width
        str = str.replaceAll("(?i)\\{h\\}", Integer.toString(height));  // display height
        str = str.replaceAll("(?i)\\{ew\\}", Integer.toString(elementWidth));   // element width
        str = str.replaceAll("(?i)\\{eh\\}", Integer.toString(elementHeight));  // element height
        str = str.replaceAll("(?i)\\{tw\\}", Integer.toString(fontRendererObj.getStringWidth(text)));
        return str;
    }
}
