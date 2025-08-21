package dev.anye.mc.srsl.effect.yanling;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.Effects;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class YanLingEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class YLE{
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event){
            if (event.getEntity().level().isClientSide){
                return;
            }
            if (event.getEntity().hasEffect(Effects.YAN_LING.get())){
                event.setCanceled(true);
            }
        }
    }
}
