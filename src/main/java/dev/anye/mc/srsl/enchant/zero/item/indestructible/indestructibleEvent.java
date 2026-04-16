package dev.anye.mc.srsl.enchant.zero.item.indestructible;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

@EventBusSubscriber(modid = SRSL.MOD_ID)
public class indestructibleEvent {
    private static final int max = Short.MAX_VALUE -1;
    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (Indestructible.ENABLE) {
            ItemEntity itemEntity = event.getEntity();
            if (EnchantHelper.hasEnchant(itemEntity.getItem(), Enchants.zi_indestructible)) {
                event.setExtraLife(max);
            }
        }
    }
}
