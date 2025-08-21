package dev.anye.mc.srsl.event;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.gui.ToriNoUtaGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
    private static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.TORI_NO_UTA);
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
        if (ENABLE) {
            event.registerAboveAll(ToriNoUtaGui.id, ToriNoUtaGui::render);
        }
    }

}
