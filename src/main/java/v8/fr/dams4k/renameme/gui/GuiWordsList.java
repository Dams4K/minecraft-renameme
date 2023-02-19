package fr.dams4k.renameme.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import fr.dams4k.renameme.configs.ModConfig;
import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.buttons.ModButton;
import fr.dams4k.renameme.gui.buttons.WordConfigButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.resources.I18n;

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

        currentPage = this.clamp(currentPage, 0, this.getTotalPages(maximumButtonPerPage)-1);

        for (int i = 0; i + maximumButtonPerPage * this.currentPage < ModConfig.wordConfigs.size() && i < maximumButtonPerPage; i++) {
            WordConfig wordConfig = ModConfig.wordConfigs.get(i + maximumButtonPerPage * this.currentPage);
            WordConfigButton wordConfigButton = new WordConfigButton(i*2+10, width / 2 - buttonWidth / 2, top + 10 + i*25, 150, 20, wordConfig);
            for (ModButton button : wordConfigButton.getButtons()) {
                buttonList.add(button);
            }
        }

        this.createPageLabel(this.currentPage+1, maximumButtonPerPage);

        String addText = I18n.format("renameme.button.add", new Object[0]);
        int addTextWidth = fontRendererObj.getStringWidth(addText);
        int addButtonWidth = Math.max(addTextWidth + 10, 150);
        buttonList.add(new GuiButton(1, width / 2 - addButtonWidth / 2, height - 25, addButtonWidth, 20, addText));

        String previousText = I18n.format("renameme.button.previous", new Object[0]);
        int previousTextWidth = fontRendererObj.getStringWidth(previousText);
        int previousButtonWidth = Math.max(previousTextWidth + 10, 20);
        GuiButton leftButton = new GuiButton(2, width / 2 - addButtonWidth / 2 - previousButtonWidth - 5, height - 25, previousButtonWidth, 20, previousText);
        if (this.currentPage == 0) leftButton.enabled = false;
        buttonList.add(leftButton);

        String nextText = I18n.format("renameme.button.next", new Object[0]);
        int nextTextWidth = fontRendererObj.getStringWidth(nextText);
        int nextButtonWidth = Math.max(nextTextWidth + 10, 20);
        GuiButton rightButton = new GuiButton(3, width / 2 + addButtonWidth / 2 + 5, height - 25, nextButtonWidth, 20, nextText);
        if (this.currentPage+1 == this.getTotalPages(maximumButtonPerPage)) rightButton.enabled = false;
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
        return Math.max(1, (int) Math.ceil(ModConfig.wordConfigs.size() / (float) maximumButtonPerPage));
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

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();

        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                mc.displayGuiScreen(new GuiWordsList(currentPage-1));
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                mc.displayGuiScreen(new GuiWordsList(currentPage+1));
            }
        }
    }

    public int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
