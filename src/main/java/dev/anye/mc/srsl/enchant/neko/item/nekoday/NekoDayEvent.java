package dev.anye.mc.srsl.enchant.neko.item.nekoday;

import dev.anye.mc.cores.helper.time.TimeHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.core.Enchants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NekoDayEvent {
    @EventBusSubscriber(modid = SRSL.MOD_ID)
    public static class NDE{
        @SubscribeEvent
        public static void onAttack(LivingIncomingDamageEvent event){
            if (NekoDay.ENABLE && !event.getEntity().level().isClientSide()){
                if (event.getSource().getEntity() != null && event.getSource().getEntity() instanceof LivingEntity livingEntity){
					int lvl = EnchantHelper.getEnchantLevel(livingEntity.getMainHandItem(), Enchants.ni_day);
					float damage  = 1 + lvl * 0.5F;
                    if (TimeHelper.isDay(TimeHelper.getDayTime(livingEntity))){
                        event.setAmount(event.getAmount() + damage);
                    }else {
                        event.setAmount(event.getAmount() - damage);
                    }
                }
            }
        }
    }
}
