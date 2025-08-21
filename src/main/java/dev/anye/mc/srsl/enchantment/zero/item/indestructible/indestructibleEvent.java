package dev.anye.mc.srsl.enchantment.zero.item.indestructible;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
public class indestructibleEvent {
    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (Indestructible.ENABLE) {
            ItemEntity itemEntity = event.getEntity();
            if (itemEntity != null && itemEntity.getItem().getEnchantmentLevel(NekoEnchantments.zi_indestructible) > 0) {
                event.setCanceled(true);
            }
        }
    }
}
