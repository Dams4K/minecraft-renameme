package fr.dams4k.rmm.events;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import fr.dams4k.rmm.configs.ModConfig;
import fr.dams4k.rmm.configs.WordConfig;
import fr.dams4k.rmm.gui.GuiModConfig;
import fr.dams4k.rmm.proxy.ClientProxy;
import fr.dams4k.rmm.utils.JsonSimplifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEvents {
    private static String testName = "§b§oDams4K";

    @SubscribeEvent
    public void onServerChat(ClientChatReceivedEvent event) {
        event.setCanceled(true);
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

        IChatComponent message = event.message;
        String content = message.getFormattedText();    
        
        for (JsonObject jsonObject : ModConfig.configsJsonObjects) {
            WordConfig config = (WordConfig) JsonSimplifier.readString(jsonObject.get("config"), WordConfig.class);
            String replaced = formatString(config.getWordReplaced());
            String replacer = formatString(config.getWordReplacer());
            Pattern p = Pattern.compile("(?i)" + replaced);
            content = p.matcher(content).replaceAll(replacer);
        }
        
        if (event.type == 0 || event.type == 1) { // Standard (0) and System (1) messages
            player.addChatMessage(new ChatComponentText(content));
        } else if (event.type == 2) { // message above action bar
            Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(content);
        } else {
            event.setCanceled(false); // what the heck is this message???
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST) onPlayerList(event);
    }

    public void onPlayerList(RenderGameOverlayEvent event) {
        Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        for (NetworkPlayerInfo playerInfo : players) {
            UUID thePlayerUUID = Minecraft.getMinecraft().thePlayer.getGameProfile().getId();
            UUID playerUUID = playerInfo.getGameProfile().getId();
            
            if (thePlayerUUID.equals(playerUUID)) {
                String prefix = "§r";
                String suffix = "§r";
                if (playerInfo.getPlayerTeam() != null) {
                    prefix = playerInfo.getPlayerTeam().getColorPrefix();
                    suffix = playerInfo.getPlayerTeam().getColorSuffix();
                }
                playerInfo.setDisplayName(new ChatComponentText(prefix + testName + suffix));
            }
        }
    }

    public String formatString(String str) {
        String thePlayerName = Minecraft.getMinecraft().thePlayer.getName();
        Pattern p = Pattern.compile("(?i)\\{username\\}");
        str = p.matcher(str).replaceAll(thePlayerName);
        str = str.replace("&", "§");
        return str;
    }

    @SubscribeEvent
    public void onKeyboardKeyPressed(InputEvent.KeyInputEvent event) {
        if (ClientProxy.RENAMEMEMOD_CONFIG_KEY.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiModConfig());
        }
    }
}