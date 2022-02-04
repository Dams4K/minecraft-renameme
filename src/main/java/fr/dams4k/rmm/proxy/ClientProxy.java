package fr.dams4k.rmm.proxy;

import org.lwjgl.input.Keyboard;

import fr.dams4k.rmm.events.ClientEvents;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy {
    public static final KeyBinding RENAMEMEMOD_CONFIG_KEY = new KeyBinding("rmm.key.opengui", Keyboard.KEY_O, "category.4kmods");

    public void preInit() {}

    public void init() {
        ClientRegistry.registerKeyBinding(RENAMEMEMOD_CONFIG_KEY);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
