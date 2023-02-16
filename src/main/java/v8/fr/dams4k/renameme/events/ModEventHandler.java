package fr.dams4k.renameme.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
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
        IChatComponent renamedComponent = new ChatComponentText(component.getUnformattedTextForChat().replace("a", "b"));
        renamedComponent.setChatStyle(component.getChatStyle());

        for (IChatComponent sibling : component.getSiblings()) {
            renamedComponent.appendSibling(this.renameComponent(sibling));
        }

        return renamedComponent;
    }
}
