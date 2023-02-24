package fr.dams4k.renameme.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.dams4k.colorpicker.ColorPicker;
import fr.dams4k.renameme.References;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class ModGuiScreen extends GuiScreen {
    protected int top = 20;

    public List<GuiTextField> textFieldList = new ArrayList<>();
    
    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();
        labelList.clear();
        textFieldList.clear();

        String title = String.format("%s - v%s", References.MOD_NAME, References.MOD_VERSION);
        GuiLabel titleLabel = new GuiLabel(mc.fontRendererObj, -1, width/2-mc.fontRendererObj.getStringWidth(title)/2, top-10, 150, 20, 0xffffff);
        titleLabel.func_175202_a(title);
        labelList.add(titleLabel);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.run();
    }

    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		for (GuiTextField field : this.textFieldList) {
			field.textboxKeyTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}

    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (GuiTextField field : this.textFieldList) {
			field.mouseClicked(mouseX, mouseY, mouseButton);
		}
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
	public void updateScreen() {
		for (GuiTextField field : this.textFieldList) {
			field.updateCursorCounter();
		}
		super.updateScreen();
	}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (GuiTextField field : this.textFieldList) {
			field.drawTextBox();
		}
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
