package fr.dams4k.rmm.gui;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.dams4k.rmm.configs.ModConfig;
import fr.dams4k.rmm.configs.WordConfig;
import fr.dams4k.rmm.utils.JsonSimplifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiWordConfig extends BaseGuiConfig {
    private Minecraft mc = Minecraft.getMinecraft();
    private WordConfig wordConfig;
    private int elementPos;

    public GuiWordConfig(int elementPos) {
        this.elementPos = elementPos;
        
        JsonObject jsonObject = ModConfig.configsJsonObjects.get(elementPos);
        wordConfig = (WordConfig) JsonSimplifier.readString(jsonObject.get("config"), WordConfig.class);
        jsonGuiConfig = JsonSimplifier.readStringForList(jsonObject.get("gui"), JsonObject[].class);
    }


    @Override
    public void initGui() {
        super.initGui();
        guiTextFieldArray.get(112).setText(wordConfig.getWordReplaced());
        guiTextFieldArray.get(126).setText(wordConfig.getWordReplacer());
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 43) { // save button
            Gson gson = new Gson();
            ModConfig.configsJsonObjects.get(elementPos).remove("config");
            ModConfig.configsJsonObjects.get(elementPos).add("config", gson.toJsonTree(wordConfig));
            ModConfig.saveConfig();
            mc.displayGuiScreen(new GuiModConfig((int) elementPos / (width/25-2)));
        } else if (button.id == 13) { // cancel button
            mc.displayGuiScreen(new GuiModConfig((int) elementPos / (width/25-2)));
        }
        
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        wordConfig.setWordReplaced(guiTextFieldArray.get(112).getText());
        wordConfig.setWordReplacer(guiTextFieldArray.get(126).getText());
    }
}
