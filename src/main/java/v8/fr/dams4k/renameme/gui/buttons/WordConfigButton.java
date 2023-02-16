package fr.dams4k.renameme.gui.buttons;

import fr.dams4k.renameme.configs.WordConfig;
import fr.dams4k.renameme.gui.GuiWordConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class WordConfigButton extends GuiButton {
    private final Minecraft mc = Minecraft.getMinecraft();

    private WordConfig wordConfig;

    public WordConfigButton(int id, int x, int y, int width, int height, WordConfig wordConfig) {
        super(id, x, y, width, height, "");
        this.wordConfig = wordConfig;
        this.displayString = wordConfig.getFinalWord();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);
        mc.displayGuiScreen(new GuiWordConfig(this.wordConfig));
    }
}
