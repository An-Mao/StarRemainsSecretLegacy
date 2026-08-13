package dev.anye.mc.srsl.reg.enchant.zero.item.unbreakable;

import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Unbreakable extends ZeroItemE {
	public static final String ENCHANTMENT_KEY_UNBREAKABLE = "unbreakable";
	private static final String TOOLTIPS_REFINE = "tooltips.srsl.refine";

	public Unbreakable() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return pStack.getMaxDamage() > 0;
	}

	@Override
	public @NotNull Component getFullName(int pLevel) {
		return super.getFullName(pLevel);
	}
}
