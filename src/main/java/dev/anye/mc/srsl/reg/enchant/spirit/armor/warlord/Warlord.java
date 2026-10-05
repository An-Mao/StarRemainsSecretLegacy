package dev.anye.mc.srsl.reg.enchant.spirit.armor.warlord;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.CoolDownData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class Warlord extends SAE {
	private static final String KEY_CD = "WarlordArmorCD";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("cooldown",2000).add("health",3F).add("maxBonus",1.5F).add("injured",0.4F);
	});
	private final int cooldown;
	private final float health;
	private final float maxBonus;
	private final float injured;

	public record Data(int cooldown,float health,float maxBonus,float injured) {
		public static final Data DEFAULT = new Data(2000,3,1.5F,0.4F);
	}

	public Warlord(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		cooldown = config.asInt("cooldown");
		health = config.asFloat("health");
		maxBonus = config.asFloat("maxBonus");
		injured = config.asFloat("injured");
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onDeath);
		modEventBus.addListener(this::onHurt);
	}

	public void onDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
			if (EnchantHelper.hasEnchant(item, Enchants.oa_warlord)) {
				CoolDownData coolDownData = CoolDownData.getCooldownData(serverPlayer);
				if (!coolDownData.isCoolDown(KEY_CD)) {
					//System.out.println("health:" + serverPlayer.getHealth());
					if (serverPlayer.getHealth() == serverPlayer.getMaxHealth()) {
						coolDownData.setCooldown(KEY_CD, cooldown);
						serverPlayer.setHealth(health);
						event.setCanceled(true);
					}
				}

			}
		}
	}

	public void onHurt(LivingDamageEvent.Pre event) {
			if (event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
				ItemStack item = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
				if (EnchantHelper.hasEnchant(item, Enchants.oa_warlord)) {
					event.setNewDamage(event.getNewDamage() * (1 + serverPlayer.getHealth() / serverPlayer.getMaxHealth() * maxBonus));
				}
			}
			if (event.getEntity() instanceof ServerPlayer serverPlayer) {
				ItemStack oItem = serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get();
				if (EnchantHelper.hasEnchant(oItem, Enchants.oa_warlord)) {
					event.setNewDamage(event.getNewDamage() * (1 + injured));
				}
			}

	}
}
