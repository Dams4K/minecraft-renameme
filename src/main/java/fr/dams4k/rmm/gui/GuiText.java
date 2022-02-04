package fr.dams4k.rmm.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

public class GuiText {
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    public String text = "";
    public int x = 0;
    public int y = 0;
    public String color = "ffffff";
    public boolean dropShadow = false;

    public GuiText(String text, int x, int y, String color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void drawString(GuiScreen gui) {
        fontRenderer.drawString(text, x, y, Integer.parseInt(color, 16), dropShadow);
    }
}
