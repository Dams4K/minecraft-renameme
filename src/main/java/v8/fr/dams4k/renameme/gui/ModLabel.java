package fr.dams4k.renameme.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;

public class ModLabel extends GuiLabel {
    private float scale;

    public ModLabel(FontRenderer fontRendererObj, int id, int x, int y, int width, int height, int color, float scale) {
        super(fontRendererObj, id, Math.round(x/scale), Math.round(y/scale), Math.round(width/scale), Math.round(height/scale), color);
        this.scale = scale;
    }
    
    public void drawLabel(Minecraft mc, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glScaled(this.scale, this.scale, 1f);
        super.drawLabel(mc, mouseX, mouseY);
        GL11.glPopMatrix();
    }
}
