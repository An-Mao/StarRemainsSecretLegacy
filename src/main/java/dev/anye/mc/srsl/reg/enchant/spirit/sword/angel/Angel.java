package dev.anye.mc.srsl.reg.enchant.spirit.sword.angel;

import dev.anye.mc.cores.helper.attribute.AttributeHelper;
import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class Angel extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("baseBonus",0.5F).add("monsterBonus",0.5F);
	});
	private final float baseBonus;
	private final float monsterBonus;
	public record Data(float baseBonus,float monsterBonus) {
		public static final Data DEFAULT = new Data(0.5F,0.5F);
	}
	public Angel(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		baseBonus = config.asFloat("baseBonus");
		monsterBonus = config.asFloat("monsterBonus");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer serverPlayer) {
			Collection<AttributeModifier> attack = ItemHelper.getAttributeModifiers(serverPlayer.getMainHandItem(), EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE);
			if (!attack.isEmpty()) {
				float damage = baseBonus;
				if (pTarget.getType().getCategory() == MobCategory.MONSTER) damage *= 1 + monsterBonus;
				damage *= (float) AttributeHelper.getAttributeModifierValue(attack);
				pTarget.hurtServer(serverPlayer.level(),pAttacker.damageSources().magic(), damage);
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
