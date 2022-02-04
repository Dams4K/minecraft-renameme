package fr.dams4k.rmm.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import fr.dams4k.rmm.configs.ModConfig;
import fr.dams4k.rmm.utils.JsonSimplifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiModConfig extends BaseGuiConfig {
    private Minecraft mc = Minecraft.getMinecraft();
    private int page = 0;
    private int maxElementPerPage = height/27-2;

    public GuiModConfig(int page) {
        this.page = page;
    }

    public GuiModConfig() {}

    @Override
    public void initGui() {
        try {
            jsonGuiConfig = new ArrayList<JsonObject>();
            maxElementPerPage = height/27-2;
            
            for (int i = maxElementPerPage * page; i < ModConfig.configsJsonObjects.size() && i < maxElementPerPage * (page +1); i++) {
                if (ModConfig.configsJsonObjects.get(i).get("config") == null) continue;
                JsonObject jsonObjectConfig = ModConfig.configsJsonObjects.get(i).get("config").getAsJsonObject();
                JsonObject newJsonGuiElement = new JsonObject();

                newJsonGuiElement.addProperty("id", 100+i);
                newJsonGuiElement.addProperty("type", "class net.minecraftforge.fml.client.config.GuiButtonExt");
                newJsonGuiElement.addProperty("posX", "{w}/2-{ew}/2");
                newJsonGuiElement.addProperty("posY", "{eh}/2+" + 25 * (i - maxElementPerPage * page));
                newJsonGuiElement.addProperty("width", 150);
                newJsonGuiElement.addProperty("height", 20);
                newJsonGuiElement.addProperty("text", jsonObjectConfig.get("wordReplacer").getAsString());
                
                jsonGuiConfig.add(newJsonGuiElement);
            }

            for (JsonObject jsonObject : JsonSimplifier.readResourcesFile(getClass().getResource("/GuiModConfig.json").toString(), JsonObject[].class)) {
                jsonObject.remove("posY");
                if (maxElementPerPage < ModConfig.configsJsonObjects.size()) {
                    jsonObject.addProperty("posY", maxElementPerPage*27);
                } else {
                    jsonObject.addProperty("posY", ModConfig.configsJsonObjects.size()*27);
                }
                jsonGuiConfig.add(jsonObject);
            }

            JsonObject pageIndicatorObject = new JsonObject();
            pageIndicatorObject.addProperty("type", "class fr.dams4k.rmm.gui.GuiText");
            pageIndicatorObject.addProperty("posX", "{w}/2-{ew}/2");
            pageIndicatorObject.addProperty("posY", Integer.toString((ModConfig.configsJsonObjects.size() + 1)*27) );
            pageIndicatorObject.addProperty("text", "qsd");
            // System.out.println((ModConfig.configsJsonObjects.size() + 1)*27);
            // pageIndicatorObject.addProperty("text", page + "/" + ModConfig.configsJsonObjects.size()/maxElementPerPage);
            jsonGuiConfig.add(pageIndicatorObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id >= 100) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiWordConfig(button.id-100));
        } else if (button.id == -1) { // to the left
            if (page > 0) {
                page--;
            } else {
                page = ModConfig.configsJsonObjects.size() / maxElementPerPage;
            }
            
            mc.displayGuiScreen(new GuiModConfig(page));
        } else if (button.id == 1) { // to the right
            if (page < ModConfig.configsJsonObjects.size() / maxElementPerPage) {
                page++;
            } else {
                page = 0;
            }

            mc.displayGuiScreen(new GuiModConfig(page));
        }
    }
}
