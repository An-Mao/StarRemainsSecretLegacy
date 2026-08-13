package dev.anye.mc.srsl.reg.enchant.neko.armor.nekomirror;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NekoMirror extends NekoEA {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",5);
	});
	private final int maxLevel;

	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(5);
	}

	public NekoMirror(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		maxLevel = config.asInt("maxLevel");
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHurt);
	}

	public void onHurt(LivingIncomingDamageEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(serverPlayer);
			for (ItemStack slot : armorItemStacks) {
				int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_mirror);
				if (lvl > 0 && lvl * 2 > _Math.RD.getIntRandomNumber(1, 100) && event.getSource().getEntity() instanceof LivingEntity livingEntity) {
					livingEntity.hurtServer(serverPlayer.level(), event.getSource(), event.getAmount());
					event.setCanceled(true);
					break;
				}
			}
		}
	}
}
