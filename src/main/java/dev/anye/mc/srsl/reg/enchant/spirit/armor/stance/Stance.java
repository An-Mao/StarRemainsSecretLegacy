package dev.anye.mc.srsl.reg.enchant.spirit.armor.stance;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class Stance extends SAE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("damageReduction",0.6F).add("damageIncrease",1.5F);
	});
	private final float damageReduction;
	private final float damageIncrease;
	public record Data(float damageReduction,float damageIncrease) {
		public static final Data DEFAULT = new Data(0.6F,1.5F);
	}
	public Stance(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		damageReduction = config.asFloat("damageReduction");
		damageIncrease = config.asFloat("damageIncrease");
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHurt);
	}

	public void onHurt(LivingDamageEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
			if (EnchantHelper.hasEnchant(item, Enchants.oa_stance)) {
				if (event.getSource().getEntity() != null) {
					double dis = serverPlayer.getEyePosition().distanceTo(event.getSource().getEntity().getEyePosition());
					if (dis > 3) {
						event.setNewDamage(event.getNewDamage() * damageReduction);
					} else {
						event.setNewDamage(event.getNewDamage() * damageIncrease);
					}
				}
			}
		}
	}
}
