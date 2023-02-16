package fr.dams4k.renameme.gui;

import java.io.IOException;

import fr.dams4k.renameme.configs.ModConfig;
import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.buttons.WordConfigButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;

public class GuiWordsList extends ModGuiScreen {
    private int currentPage = 0;

    public GuiWordsList() {}

    public GuiWordsList(int page) {
        this.currentPage = page;
    }

    @Override
    public void initGui() {
        super.initGui();

        int buttonWidth = 150;
        ModConfig.loadWordConfigs();

        int availableHeight = height - (top + 10) - 50;
        int maximumButtonPerPage = availableHeight / 25;

        for (int i = 0; i + maximumButtonPerPage * this.currentPage < ModConfig.wordConfigs.size() && i < maximumButtonPerPage; i++) {
            WordConfig wordConfig = ModConfig.wordConfigs.get(i + maximumButtonPerPage * this.currentPage);
            WordConfigButton wordConfigButton = new WordConfigButton(i+10, width / 2 - buttonWidth / 2, top + 10 + i*25, 150, 20, wordConfig);
            buttonList.add(wordConfigButton);
        }

        this.createPageLabel(this.currentPage+1, maximumButtonPerPage);

        buttonList.add(new GuiButton(1, width / 2 - buttonWidth / 2, height - 25, 150, 20, "+"));
        GuiButton leftButton = new GuiButton(2, width / 2 - buttonWidth / 2 - 25, height - 25, 20, 20, "<");
        if (this.currentPage == 0) leftButton.enabled = false;
        buttonList.add(leftButton);

        GuiButton rightButton = new GuiButton(3, width / 2 + buttonWidth / 2 + 5, height - 25, 20, 20, ">");
        if (this.currentPage == this.getTotalPages(maximumButtonPerPage)) rightButton.enabled = false;
        buttonList.add(rightButton);
    }

    private void createPageLabel(int currentPage, int maximumButtonPerPage) {

        String pageTitle = String.format("%s/%s", currentPage, this.getTotalPages(maximumButtonPerPage));
        int labelWidth = mc.fontRendererObj.getStringWidth(pageTitle);
        int labelX = width/2-labelWidth/2;

        GuiLabel pageLabel = new GuiLabel(fontRendererObj, 0, labelX, height - 50, labelWidth, 20, 0xffffff);
        pageLabel.func_175202_a(pageTitle);
        labelList.add(pageLabel);
    }

    private int getTotalPages(int maximumButtonPerPage) {
        return (int) Math.ceil(ModConfig.wordConfigs.size() / (float) maximumButtonPerPage);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            //- Add
            mc.displayGuiScreen(new GuiWordConfig(ModConfig.createNewWordConfig()));
        } else if (button.id == 2) {
            //- Left
            mc.displayGuiScreen(new GuiWordsList(currentPage-1));
        } else if (button.id == 3) {
            //- Right
            mc.displayGuiScreen(new GuiWordsList(currentPage+1));
        }
    }
}
