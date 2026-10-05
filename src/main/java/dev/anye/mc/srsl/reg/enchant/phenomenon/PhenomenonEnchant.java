package dev.anye.mc.srsl.reg.enchant.phenomenon;

import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class PhenomenonEnchant extends Enchant {
	protected PhenomenonEnchant(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData);
	}

	@Override
	public @NotNull Component getFullName(int pLevel) {
		return ComponentStyle.Flash(Component.translatable(this.getDescriptionId()).getString()).append(Component.literal("x"));
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
