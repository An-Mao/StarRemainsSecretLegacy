package dev.anye.mc.srsl.reg.enchant.phenomenon.duality;

import dev.anye.mc.cores.helper.item.ItemHelper;
import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.phenomenon.PhenomenonEnchant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Duality extends PhenomenonEnchant {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.DUALITY);
	private final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.DUALITY, "quota");

	public Duality() {
		super();
	}


	@Override
	public int getMaxLevel() {
		return super.getMaxLevel();
	}

	@Override
	public float getDamageBonus(int level, Entity mobType, ItemStack enchantedItem) {
		CompoundTag nbt = EnchantHelper.getEnchantData(enchantedItem).getTagCopy();
		CompoundTag dualityData = nbt.getCompound("duality.data").orElse(new CompoundTag());
		float duality = dualityData.getInt("duality").orElse(0);
		if (duality < 0) {
			duality = (float) (ItemHelper.getItemDamage(enchantedItem) * -duality / quota);
			return duality;
		}
		return super.getDamageBonus(level, mobType, enchantedItem);
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
