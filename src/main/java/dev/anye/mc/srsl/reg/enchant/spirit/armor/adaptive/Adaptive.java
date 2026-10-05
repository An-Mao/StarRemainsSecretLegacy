package dev.anye.mc.srsl.reg.enchant.spirit.armor.adaptive;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.CoolDownData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class Adaptive extends SAE {
	private static final String INDEX_ITEM = "AdaptiveArmorCD";
	private static final String INDEX_TIME = "AdaptiveArmorTime";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("damageLimit",0.35F)
				.add("minDamage",3F)
				.add("span",600)
				.add("cooldown",3000)
		;
	});

	private final float damageLimit;
	private final float minDamage;
	private final int span;
	private final int cooldown;
	public record Data(float damageLimit,float minDamage,int span,int cooldown) {
		public static final Data DEFAULT = new Data(
				0.35F,
				3F,
				600,
				3000);
	}
	public Adaptive(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		damageLimit = config.asFloat("damageLimit");
		minDamage = config.asFloat("minDamage");
		span = config.asInt("span");
		cooldown = config.asInt("cooldown");
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHurt);
		modEventBus.addListener(this::onDeath);
	}

	@SubscribeEvent
	public void onHurt(LivingIncomingDamageEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer && EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_adaptive)) {
			CoolDownData coolDownData = CoolDownData.getCooldownData(serverPlayer);
			if (coolDownData.isCoolDown(INDEX_TIME)) {
				serverPlayer.heal(event.getAmount());
				event.setCanceled(true);
				return;
			}
			if (event.getSource().getEntity() instanceof ServerPlayer player && player.getUUID() == serverPlayer.getUUID()) {
				event.setCanceled(true);
				return;
			}
			float a = serverPlayer.getHealth() * damageLimit;
			if (event.getAmount() > a) {
				event.setAmount(Math.max(a, minDamage));
			}
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof ServerPlayer player && EnchantHelper.hasEnchant(player.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_adaptive)) {
			CoolDownData coolDownData = CoolDownData.getCooldownData(player);
			if (coolDownData.isCoolDown(INDEX_TIME)) {
				event.setCanceled(true);
			} else {
				coolDownData.setCooldown(INDEX_ITEM, cooldown);
				coolDownData.setCooldown(INDEX_TIME, span);
				player.clearFire();
				player.removeAllEffects();
				player.setHealth(1.0F);
				event.setCanceled(true);
			}
		}
	}
}
