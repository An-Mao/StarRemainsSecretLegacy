package dev.anye.mc.srsl.reg.enchant.zero.tool;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.ZeroEnchant;
import net.minecraft.world.item.ItemStack;

public class ChainHarvesting extends ZeroEnchant {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("radius",8).add("depths",3);
	});
	public final int radius;
	public final int depths;
	public record Data(int radius,int depths) {
		public static final Data DEFAULT = new Data(8,3);
	}
	public ChainHarvesting(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		radius = config.asInt("radius");
		depths = config.asInt("depths");
	}

	public int radius(){
		return radius;
	}
	public int depths(){
		return depths;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isDigger(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
