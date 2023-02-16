package fr.dams4k.renameme.gui;

import java.io.IOException;

import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.buttons.ModTextField;
import net.minecraft.client.gui.GuiButton;

public class GuiWordConfig extends ModGuiScreen {
    private WordConfig wordConfig;

    private ModTextField originalWordTextField;
    private ModTextField finalWordTextField;

    public GuiWordConfig(WordConfig wordConfig) {
        this.wordConfig = wordConfig;
    }

    @Override
    public void initGui() {
        super.initGui();

        int textFieldWidth = 150;

        originalWordTextField = new ModTextField(
            0, fontRendererObj, width / 2 - textFieldWidth / 2, top + 10 + 25 * 1,
            textFieldWidth, 20,
            "original word", wordConfig.getUnformattedOriginalWord()
        );

        finalWordTextField = new ModTextField(
            1, fontRendererObj, width / 2 - textFieldWidth / 2, top + 10 + 25 * 2,
            textFieldWidth, 20,
            "final word", wordConfig.getUnformattedFinalWord()
        );

        textFieldList.add(originalWordTextField);
        textFieldList.add(finalWordTextField);

        buttonList.add(new GuiButton(99, width / 2 - 75, top + 10 + 25 * 4, 70, 20, "Cancel"));
        buttonList.add(new GuiButton(100, width / 2 + 5, top + 10 + 25 * 4, 70, 20, "Ok"));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        super.actionPerformed(guiButton);

        int buttonId = guiButton.id;
        if (buttonId == 99) {
            //- Cancel
            mc.displayGuiScreen(new GuiWordsList());
        } else if (buttonId == 100) {
            //- Save
            this.wordConfig.setOriginalWord(originalWordTextField.getText());
            this.wordConfig.setFinalWord(finalWordTextField.getText());
            mc.displayGuiScreen(new GuiWordsList());
        }
    }
}
