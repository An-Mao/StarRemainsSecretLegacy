package dev.anye.mc.srsl.reg.enchant.spirit.sword.judgment;

import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Judgment extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",2F);
	});
	private final float quota;
	public record Data(float quota) {
		public static final Data DEFAULT = new Data(2);
	}
	public Judgment(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);

		quota = config.asFloat("quota");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (!pAttacker.level().isClientSide() && pTarget instanceof Mob mob) {
			Collection<AttributeModifier> attack = ItemHelper.getAttributeModifiers(pAttacker.getMainHandItem(), EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);

			if (!attack.isEmpty()) {
				double damage = AttributeHelper.getAttributeModifierValue(attack);
				if (mob.getHealth() <= damage * quota) {
					mob.kill((ServerLevel) pAttacker.level());
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
