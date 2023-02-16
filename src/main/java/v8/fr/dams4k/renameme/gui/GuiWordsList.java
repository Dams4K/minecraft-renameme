package fr.dams4k.renameme.gui;

import fr.dams4k.renameme.configs.ModConfig;
import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.buttons.WordConfigButton;

public class GuiWordsList extends ModGuiScreen {

    @Override
    public void initGui() {
        super.initGui();

        for (int i = 0; i < ModConfig.wordConfigs.size(); i++) {
            WordConfig wordConfig = ModConfig.wordConfigs.get(i);

            int buttonWidth = 150;

            WordConfigButton wordConfigButton = new WordConfigButton(i+10, width / 2 - buttonWidth / 2, top + 10 + i*25, 150, 20, wordConfig);
            buttonList.add(wordConfigButton);
        }
    }
}
