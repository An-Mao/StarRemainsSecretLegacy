package dev.anye.mc.srsl.reg.enchant.neko.item.nekolove;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.world.item.ItemStack;

public class NekoLove extends NekoEI {
	public static final String ENCHANTMENT_KEY_LOVE = "love";
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_LOVE);

	public NekoLove() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return true;
	}
}
