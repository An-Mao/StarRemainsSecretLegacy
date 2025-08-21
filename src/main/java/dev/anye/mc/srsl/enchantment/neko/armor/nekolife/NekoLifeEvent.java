package dev.anye.mc.srsl.enchantment.neko.armor.nekolife;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NekoLifeEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NLE {
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            if (NekoLife.ENABLE && event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide){
                Iterable<ItemStack> slotlist = player.getArmorSlots();
                for (ItemStack slot : slotlist){
                    int lvl = slot.getEnchantmentLevel(NekoEnchantments.na_life);
                    if (lvl > 0){
                        if (lvl * 15 > _Math.RD.getIntRandomNumber(1,100)){
                            double min_damage = player.getMaxHealth() * 0.2;
                            if (event.getAmount() > min_damage && event.getAmount() < player.getMaxHealth() * 1.5 ){
                                event.setAmount((float) (min_damage));
                            }
                        }

                    }
                }
            }
        }
    }
}
