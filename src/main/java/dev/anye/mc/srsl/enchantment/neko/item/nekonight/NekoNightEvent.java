package dev.anye.mc.srsl.enchantment.neko.item.nekonight;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.enchantment.NekoEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class NekoNightEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NNE{
        @SubscribeEvent
        public static void onAttack(LivingHurtEvent event){
            if (NekoNight.ENABLE && !event.getEntity().level().isClientSide){
                if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity livingEntity){
                    int lvl = livingEntity.getMainHandItem().getEnchantmentLevel(NekoEnchantments.ni_night);
                    int daytime = (int) livingEntity.level().getDayTime();
                    float damage = 1 + lvl * 0.5F;
                    if (daytime < 7200 || daytime > 22800){
                        event.setAmount(event.getAmount() + damage);
                    }else {
                        event.setAmount(event.getAmount() - damage);
                    }
                }
            }
        }
    }
}
