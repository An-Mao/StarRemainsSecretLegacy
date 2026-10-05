package dev.anye.mc.srsl.reg.enchant.spirit.sword.cross;

import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Cross extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("multiplier",100F);
	});
	private final float multiplier;
	public record Data(float multiplier) {
		public static final Data DEFAULT = new Data(100);
	}
	public Cross(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		multiplier = config.asFloat("multiplier");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer serverPlayer) {
			if (pTarget instanceof Bat) {
				Collection<AttributeModifier> attack = ItemHelper.getAttributeModifiers(serverPlayer.getMainHandItem(), EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
				if (!attack.isEmpty()) {
					double damage = AttributeHelper.getAttributeModifierValue(attack) * multiplier;
					pTarget.hurtServer(serverPlayer.level(),pAttacker.damageSources().fellOutOfWorld(), (float) damage);
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
