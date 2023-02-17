package fr.dams4k.renameme.gui.buttons;

import net.minecraft.client.Minecraft;

public interface ModButtonListener {
    void buttonPressed(int buttonId, Minecraft mc, int mouseX, int mouseY);
    void buttonDragged(int buttonId, Minecraft mc, int mouseX, int mouseY);
    void buttonReleased(int buttonId, int mouseX, int mouseY);
}
