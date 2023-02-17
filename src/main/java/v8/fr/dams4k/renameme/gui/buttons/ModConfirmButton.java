package fr.dams4k.renameme.gui.buttons;

import net.minecraft.client.Minecraft;

public class ModConfirmButton extends ModButton {
    public int DEFAULT_NORMAL_COLOR = 0xE0E0E0;
    public int DEFAULT_HOVERED_COLOR = 0xFFFFA0;

    public int CONFIRM_NORMAL_COLOR = 0xE00106;
    public int CONFIRM_HOVERED_COLOR = 0xFE4100;

    private long confirmTime = 0l;
    private boolean inConfirmState = false;

    public ModConfirmButton(int id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height, displayString);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (inConfirmState && confirmTime + 6000l < System.currentTimeMillis()) {
            inConfirmState = false;
            NORMAL_COLOR = this.DEFAULT_NORMAL_COLOR;
            HOVERED_COLOR = this.DEFAULT_HOVERED_COLOR;
        }
        super.drawButton(mc, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        if (this.inConfirmState) {
            super.mouseReleased(mouseX, mouseY);
        } else {
            this.confirmTime = System.currentTimeMillis();
            this.inConfirmState = true;
            
            NORMAL_COLOR = this.CONFIRM_NORMAL_COLOR;
            HOVERED_COLOR = this.CONFIRM_HOVERED_COLOR;
        }
    }
}
