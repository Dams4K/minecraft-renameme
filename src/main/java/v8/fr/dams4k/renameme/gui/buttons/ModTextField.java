package fr.dams4k.renameme.gui.buttons;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class ModTextField extends GuiTextField {
    private final FontRenderer fontRendererInstance;
    private int disabledColor = 7368816;

    public String placeHolder = "";

    public ModTextField(int id, FontRenderer fontRenderer, int x, int y, int width, int height, String placeHolder, String text) {
        super(id, fontRenderer, x, y, width, height);
        this.fontRendererInstance = fontRenderer;
        this.placeHolder = placeHolder;
        this.setText(text);
    }

    @Override
    public void drawTextBox() {
        super.drawTextBox();

        if (this.getText().isEmpty()) {
            int color = this.disabledColor;
            int x = this.getEnableBackgroundDrawing() ? this.xPosition + 4 : this.xPosition;
            int y = this.getEnableBackgroundDrawing() ? this.yPosition + (this.height - 8) / 2 : this.yPosition;
            
            this.fontRendererInstance.drawStringWithShadow(this.placeHolder, x, y, color);
        }
    }
}
