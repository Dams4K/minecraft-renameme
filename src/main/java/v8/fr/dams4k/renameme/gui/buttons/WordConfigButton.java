package fr.dams4k.renameme.gui.buttons;

import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.GuiWordConfig;
import fr.dams4k.renameme.gui.GuiWordsList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class WordConfigButton implements ModButtonListener {
    private final Minecraft mc = Minecraft.getMinecraft();
    
    private int id;
    private WordConfig wordConfig;

    private ModButton guiAccessButton;
    private ModConfirmButton removeButton;

    public WordConfigButton(int id, int x, int y, int width, int height, WordConfig wordConfig) {
        this.id = id;
        this.wordConfig = wordConfig;

        String removeText = I18n.format("renameme.button.remove", new Object[0]);
        int removeTextWidth = mc.fontRendererObj.getStringWidth(removeText);
        int removeButtonWidth = Math.max(removeTextWidth + 10, 20);

        guiAccessButton = new ModButton(id, x, y, width - removeButtonWidth - 5, height, wordConfig.getFinalWord("placehoder"));
        removeButton = new ModConfirmButton(id + 1, x + width - removeButtonWidth, y, removeButtonWidth, height, removeText);

        guiAccessButton.addButtonListener(this);
        removeButton.addButtonListener(this);
    }

    public ModButton[] getButtons() {
        return new ModButton[] { guiAccessButton, removeButton };
    }

    @Override
    public void buttonPressed(int buttonId, Minecraft mc, int mouseX, int mouseY) {

    }

    @Override
    public void buttonDragged(int buttonId, Minecraft mc, int mouseX, int mouseY) {
        
    }

    @Override
    public void buttonReleased(int buttonId, int mouseX, int mouseY) {
        if (buttonId == this.id) {
            mc.displayGuiScreen(new GuiWordConfig(this.wordConfig));
        } else if (buttonId == this.id + 1) {
            wordConfig.delete();
            mc.displayGuiScreen(new GuiWordsList());
        }
    }
}
