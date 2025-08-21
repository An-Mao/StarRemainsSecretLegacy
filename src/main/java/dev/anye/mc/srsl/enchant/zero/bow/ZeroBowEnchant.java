package dev.anye.mc.srsl.enchant.zero.bow;

import dev.anye.mc.srsl.enchant.zero.ZeroEnchant;
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
