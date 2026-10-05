package dev.anye.mc.srsl.reg.enchant.neko.item.nekoblade;


import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class NekoBlade extends NekoEI {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("baseDamage",0.15F);
	});
	private final float base;
	public record Data(float baseDamage) {
		public static final Data DEFAULT = new Data(0.15F);
	}
	public NekoBlade(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		base = config.asFloat("baseDamage");
	}


	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		Collection<AttributeModifier> att =
				ItemHelper.getAttributeModifiers(enchantedItem, EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);//enchantedItem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
		if (!att.isEmpty()) {
			double addDamage = 0;
			for (AttributeModifier al : att) {

				if (al.operation() == AttributeModifier.Operation.ADD_VALUE) {
					addDamage += al.amount();
				}
			}
			float x = enchantedItem.getDamageValue();
			float y = enchantedItem.getMaxDamage();
			return (float) (addDamage * (base + x / y));
		}
		return 0;
	}
}
