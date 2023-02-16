package fr.dams4k.renameme.proxies;

import org.lwjgl.input.Keyboard;

import fr.dams4k.renameme.events.ModEventHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    public static final KeyBinding RENAMEME_CONFIG = new KeyBinding("renameme.key.opengui", Keyboard.KEY_O, "renameme.category.renameme");

    @Override
	public void preInit() {
	}

	@Override
	public void init() {
		ClientRegistry.registerKeyBinding(RENAMEME_CONFIG);
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
	}
}
