package dev.anye.mc.srsl.reg.effect.yanling;

import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.reg.effect.Effects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class YanLingEvent {
	@EventBusSubscriber(modid = SRSL.MOD_ID)
	public static class YLE {
		@SubscribeEvent
		public static void onAttack(LivingIncomingDamageEvent event) {
			if (event.getEntity().level().isClientSide()) {
				return;
			}
			if (event.getEntity().hasEffect(Effects.YAN_LING.getDelegate())) {
				event.setCanceled(true);
			}
		}
	}
}
