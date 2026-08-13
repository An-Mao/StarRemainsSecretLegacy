package dev.anye.mc.srsl.reg.enchant.spirit.armor.redlotus;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.effect.Effects;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class RedLotus extends SAE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();
	public RedLotus(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHurt);
		modEventBus.addListener(this::onDamage);
	}

	public void onHurt(LivingDamageEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer && EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_red_lotus)) {
			if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
				livingEntity.hurtServer(serverPlayer.level(), serverPlayer.damageSources().fellOutOfWorld(), event.getNewDamage() * 0.5f);
				livingEntity.addEffect(new MobEffectInstance(Effects.EVIL_CREATURE.getDelegate(), 60, 1));
			}
		}
	}


	public void onDamage(LivingDamageEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
			if (EnchantHelper.hasEnchant(item, Enchants.oa_red_lotus) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
				if (livingEntity.hasEffect(Effects.EVIL_CREATURE.getDelegate())) {
					item.setDamageValue(Math.min(item.getDamageValue() + 1, item.getMaxDamage()));
				}
			}
		}
	}
}
