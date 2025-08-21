package dev.anye.mc.srsl.enchant.neko.armor.nekomirror;

import dev.anye.core.math._Math;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

public class NekoMirrorEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NME{
        @SubscribeEvent
        public static void onHurt(LivingIncomingDamageEvent event){
            if(NekoMirror.ENABLE && !event.getEntity().level().isClientSide){
                if (event.getEntity() instanceof ServerPlayer serverPlayer){
                    Iterable<ItemStack> slotlist = serverPlayer.getArmorSlots();
                    for (ItemStack slot : slotlist){
                        int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_mirror);
                        if (lvl > 0){
                            if (lvl * 2 > _Math.RD.getIntRandomNumber(1,100)){
                                if (event.getSource().getEntity() instanceof LivingEntity livingEntity){
                                    livingEntity.hurt(event.getSource(),event.getAmount());
                                    event.setCanceled(true);
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
