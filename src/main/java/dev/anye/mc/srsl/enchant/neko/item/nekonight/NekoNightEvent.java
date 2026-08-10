package dev.anye.mc.srsl.enchant.neko.item.nekonight;

import dev.anye.mc.cores.helper.time.TimeHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NekoNightEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NNE{
        @SubscribeEvent
        public static void onAttack(LivingDamageEvent.Pre event){
            if (NekoNight.ENABLE && !event.getEntity().level().isClientSide()){
                if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity livingEntity){
                    int lvl     = EnchantHelper.getEnchantLevel(livingEntity.getMainHandItem(), Enchants.ni_night);
                    float damage  = 1 + lvl * 0.5F;
                    if (TimeHelper.isNight(TimeHelper.getDayTime(livingEntity))){
                        event.setNewDamage(event.getNewDamage() + damage);
                    }else {
                        event.setNewDamage(event.getNewDamage() - damage);
                    }
                }
            }
        }
    }
}
