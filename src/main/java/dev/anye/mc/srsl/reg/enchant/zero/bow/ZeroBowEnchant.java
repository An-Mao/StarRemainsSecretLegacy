package dev.anye.mc.srsl.reg.enchant.zero.bow;

import dev.anye.mc.srsl.reg.enchant.zero.ZeroEnchant;
import net.minecraft.world.item.ItemStack;

public class ZeroBowEnchant extends ZeroEnchant {
	public ZeroBowEnchant() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isBow(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
