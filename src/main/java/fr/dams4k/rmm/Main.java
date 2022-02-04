package fr.dams4k.rmm;

import fr.dams4k.rmm.configs.ModConfig;
import fr.dams4k.rmm.proxy.ClientProxy;
import fr.dams4k.rmm.utils.References;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = References.MOD_ID, version = References.MOD_VERSION, clientSideOnly = true, canBeDeactivated = true)
public class Main {
    // TODO: remplacer dans le title, le scoreboard, etc..... https://hypixel.net/threads/how-to-remove-title-message-from-displaying.4049206/
    // TODO: rajouter un moyen de dire à partir de quel endroit on replace le mot, exemple: "Dams4K: i say §b§oDams4K
    //                                                                                                  i wrote Dams4K word
    @SidedProxy(clientSide = "fr.dams4k.rmm.proxy.ClientProxy")
    public static ClientProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        ModConfig.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}
