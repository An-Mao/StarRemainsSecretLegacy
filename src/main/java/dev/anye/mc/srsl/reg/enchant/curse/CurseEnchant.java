package dev.anye.mc.srsl.reg.enchant.curse;

import dev.anye.mc.srsl.core.Enchant;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public class CurseEnchant extends Enchant {
	protected CurseEnchant() {
		super(ChatFormatting.RED);
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return true;
	}
}
