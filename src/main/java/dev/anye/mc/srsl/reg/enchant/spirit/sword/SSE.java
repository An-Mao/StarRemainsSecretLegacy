package dev.anye.mc.srsl.reg.enchant.spirit.sword;

import dev.anye.mc.srsl.reg.enchant.spirit.SpiritE;
import net.minecraft.world.item.ItemStack;

public class SSE extends SpiritE {
	protected SSE() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
