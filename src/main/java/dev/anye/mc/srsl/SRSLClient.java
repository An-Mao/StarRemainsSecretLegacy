package dev.anye.mc.srsl;

import dev.anye.mc.srsl.gui.ToriNoUtaGui;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SRSL.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SRSL.MOD_ID, value = Dist.CLIENT)
public class SRSLClient {
    public SRSLClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
    }
    @SubscribeEvent
    static void onGuiReg(RegisterGuiLayersEvent event){
        event.registerAboveAll(ToriNoUtaGui.RESOURCE_LOCATION, ToriNoUtaGui::render);
    }
}
