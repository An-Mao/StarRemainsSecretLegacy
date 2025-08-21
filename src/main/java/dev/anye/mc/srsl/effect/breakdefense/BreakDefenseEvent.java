package dev.anye.mc.srsl.effect.breakdefense;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.Effects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BreakDefenseEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class BDE{
        @SubscribeEvent
        public static void onDamage(LivingDamageEvent.Pre event){
            if (event.getEntity().level().isClientSide){
                return;
            }
            if (event.getEntity().hasEffect(Effects.BREAK_DEFENSE.getDelegate())){
                event.setNewDamage(event.getNewDamage()*2);
            }
        }
    }
}
