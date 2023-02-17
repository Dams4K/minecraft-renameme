package fr.dams4k.renameme.gui.buttons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ModButton extends GuiButton {
    public int NORMAL_COLOR = 0xE0E0E0;
    public int DISABLE_COLOR = 0xA0A0A0;
    public int HOVERED_COLOR = 0xFFFFA0;
    
    private List<ModButtonListener> buttonListeners = new ArrayList<>();

    public ModButton(int id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height, displayString);
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);

            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            hovered = this.isOver(mouseX, mouseY);
            
            int i = getHoverState(this.hovered);
            int texturePos = 46 + i * 20;

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);

            drawTexturedModalRect(this.xPosition, this.yPosition, 0, texturePos, this.width / 2, this.height);
            drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, texturePos, this.width / 2, this.height);
            
            int textColor = NORMAL_COLOR;

            if (!enabled) {
                textColor = DISABLE_COLOR;
            } else if (hovered) {
                textColor = HOVERED_COLOR;
            }

            drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, textColor);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean buttonPressed = super.mousePressed(mc, mouseX, mouseY);
        if (buttonPressed) {
            for (ModButtonListener listener : this.buttonListeners) {
                listener.buttonPressed(id, mc, mouseX, mouseY);
            }
        }

        return buttonPressed;
    }
    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        super.mouseDragged(mc, mouseX, mouseY);
        for (ModButtonListener listener : this.buttonListeners) {
            listener.buttonDragged(id, mc, mouseX, mouseY);
        }
    }
    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);
        for (ModButtonListener listener : this.buttonListeners) {
            listener.buttonReleased(id, mouseX, mouseY);
        }
    }

    public void addButtonListener(ModButtonListener listener) {
        this.buttonListeners.add(listener);
    }

    public boolean isOver(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }
}
