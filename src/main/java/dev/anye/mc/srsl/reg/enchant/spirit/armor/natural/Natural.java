package dev.anye.mc.srsl.reg.enchant.spirit.armor.natural;

import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import dev.anye.mc.srsl.SRSL;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.data.CoolDownData;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class Natural extends SAE {
	private static final String KEY_CD = "NaturalArmorCD";
	private static final Identifier HEALTH_KEY = Identifier.fromNamespaceAndPath(SRSL.MOD_ID,"health.natural");
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("operation",1)
				.add("amount",2D)
				.add("healthCooldown",60)
				.add("healthAmount",0.1F)
		;
	});

	private final AttributeModifier.Operation operation;
	private final double amount;
	private final int healthCooldown;
	private final float healthAmount;
	public record Data(int operation,double amount,int healthCooldown,float healthAmount) {
		public static final Data DEFAULT = new Data(1,2,60,0.1F);
	}
	public Natural(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		operation = switch (config.asInt("operation")){
			case 1 -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
			case 2 -> AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
			default -> AttributeModifier.Operation.ADD_VALUE;
		};
		amount = config.asDouble("amount");
		healthCooldown = config.asInt("healthCooldown");
		healthAmount = config.asFloat("healthAmount");
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onEquipmentChange);
		modEventBus.addListener(this::onServerTick);
	}

	public void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getSlot() == EquipmentSlot.CHEST) {
				ItemStack item = event.getFrom();
				if (EnchantHelper.hasEnchant(item, Enchants.oa_natural)) {
					AttributeInstance attributeInstance = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
					if (attributeInstance != null){
						attributeInstance.removeModifier(HEALTH_KEY);
					}
				}
				item = event.getTo();
				if (EnchantHelper.hasEnchant(item, Enchants.oa_natural)) {
					AttributeInstance attributeInstance = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
					if (attributeInstance != null){
						attributeInstance.addOrReplacePermanentModifier(new AttributeModifier(HEALTH_KEY,amount, operation));
					}
				}
				if (serverPlayer.getHealth() > serverPlayer.getMaxHealth()) {
					serverPlayer.setHealth(serverPlayer.getMaxHealth());
				}
			}

	}

	public void onServerTick(PlayerTickEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer && EnchantHelper.hasEnchant(serverPlayer.getSlot(PlayerHelper.SLOT_CHEST).get(), Enchants.oa_natural)) {
				CoolDownData coolDownData = CoolDownData.getCooldownData(serverPlayer);
				if (!coolDownData.isCoolDown(KEY_CD)) {
					coolDownData.setCooldown(KEY_CD, healthCooldown);
					serverPlayer.heal(serverPlayer.getMaxHealth() * healthAmount);
				}
			}


	}
}
