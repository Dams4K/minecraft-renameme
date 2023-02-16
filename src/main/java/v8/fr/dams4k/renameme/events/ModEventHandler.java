package fr.dams4k.renameme.events;

import java.util.regex.Pattern;

import fr.dams4k.renameme.configs.ModConfig;
import fr.dams4k.renameme.configs.WordConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler {
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onServerChat(ClientChatReceivedEvent event) {
        event.setCanceled(true);
        
        EntityPlayerSP player = mc.thePlayer;
        IChatComponent message = event.message;
        IChatComponent finalMessage = this.renameComponent(message);

        if ((event.type == 0 || event.type == 1) && player != null) {
            player.addChatMessage(finalMessage);
        } else {
            event.setCanceled(false);
        }
    }

    private IChatComponent renameComponent(IChatComponent component) {
        IChatComponent renamedComponent = new ChatComponentText("");

        for (IChatComponent sibling : component) {
            String content = sibling.getUnformattedTextForChat();
        
            for (WordConfig wordConfig : ModConfig.wordConfigs) {
                Pattern pattern = Pattern.compile("(?i)" + wordConfig.getOriginalWord());
                content = pattern.matcher(content).replaceAll(wordConfig.getFinalWord());
            }

            IChatComponent renamedSibling = new ChatComponentText(content);
            renamedSibling.setChatStyle(sibling.getChatStyle());

            renamedComponent.appendSibling(renamedSibling);
        }

        // String componentText = component.getUnformattedTextForChat();
        // // System.out.printf("uf: %s f: %s\n", componentText, component.getFormattedText());

        // StringBuilder stringbuilder = new StringBuilder();

        // for (IChatComponent ichatcomponent : component) {
        //     System.out.println(ichatcomponent.getUnformattedTextForChat());

        //     stringbuilder.append(ichatcomponent.getChatStyle().getFormattingCode());
        //     stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
        //     stringbuilder.append(EnumChatFormatting.RESET);
        // }
        // System.out.println(stringbuilder.toString());

        // for (WordConfig wordConfig : ModConfig.wordConfigs) {
        //     Pattern pattern = Pattern.compile("(?i)" + wordConfig.getOriginalWord());
        //     componentText = pattern.matcher(componentText).replaceAll(wordConfig.getFinalWord());
        // }
        
        // IChatComponent renamedComponent = new ChatComponentText(componentText);
        // renamedComponent.setChatStyle(component.getChatStyle());

        // for (IChatComponent sibling : component.getSiblings()) {
        //     renamedComponent.appendSibling(this.renameComponent(sibling));
        // }

        return renamedComponent;
    }
}
