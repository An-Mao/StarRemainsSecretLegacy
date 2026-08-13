package dev.anye.mc.srsl.reg.enchant.blood.vampirism;

import dev.anye.core.math._Math;
import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.blood.BloodEnchant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Vampirism extends BloodEnchant {
	private final int maxLevel;

	public Vampirism(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,EnchantConfigData.create(defaultConfig -> {
			defaultConfig.putInt("maxLevel",5);
		}));
		maxLevel = config.getIntOr("maxLevel",5);
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pTarget instanceof LivingEntity) {
			int base = Math.max(2, 7 - pLevel);
			//ItemHelper.getAttributeModifiers(pAttacker.getMainHandItem(),EquipmentSlot.MAINHAND);
			Collection<AttributeModifier> atk = ItemHelper.getAttributeModifiers(pAttacker.getMainHandItem(), EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
			//pAttacker.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
			float heal = _Math.log(AttributeHelper.getAttributeModifierValue(atk), base);
			if (pAttacker.getHealth() < pAttacker.getMaxHealth()) {
				pAttacker.heal(heal);
			} else {
				if (pAttacker instanceof ServerPlayer player) {
					int food = player.getFoodData().getFoodLevel();
					if (food < 20) {
						player.getFoodData().setFoodLevel((int) Math.min(20, food + heal));
					} else {
						pAttacker.setAbsorptionAmount(Math.min(pAttacker.getMaxHealth(), pAttacker.getAbsorptionAmount() + heal));
					}
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
