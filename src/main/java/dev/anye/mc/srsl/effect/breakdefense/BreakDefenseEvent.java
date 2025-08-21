package dev.anye.mc.srsl.effect.breakdefense;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.effect.Effects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class BreakDefenseEvent {
    @Mod.EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class BDE{
        @SubscribeEvent
        public static void onDamage(LivingDamageEvent event){
            if (event.getEntity().level().isClientSide){
                return;
            }
            if (event.getEntity().hasEffect(Effects.BREAK_DEFENSE.get())){
                event.setAmount(event.getAmount()*2);
            }
        }
    }
}
