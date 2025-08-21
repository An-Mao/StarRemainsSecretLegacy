package dev.anye.mc.srsl.enchant.neko.armor.nekolife;

import dev.anye.core.math._Math;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

public class NekoLifeEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NLE {
        @SubscribeEvent
        public static void onHurt(LivingDamageEvent.Pre event){
            if (NekoLife.ENABLE && event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide){
                Iterable<ItemStack> slotlist = player.getArmorSlots();
                for (ItemStack slot : slotlist){
                    int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_life);
                    if (lvl > 0){
                        if (lvl * 15 > _Math.RD.getIntRandomNumber(1,100)){
                            double min_damage = player.getMaxHealth() * 0.2;
                            if (event.getNewDamage() > min_damage && event.getNewDamage() < player.getMaxHealth() * 1.5 ){
                                event.setNewDamage((float) (min_damage));
                            }
                        }

                    }
                }
            }
        }
    }
}
