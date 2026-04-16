package dev.anye.mc.srsl.enchant.zero.item;

import dev.anye.mc.srsl.enchant.zero.ZeroEnchant;
import net.minecraft.world.item.ItemStack;

public class ZeroItemE extends ZeroEnchant {
    protected ZeroItemE() {
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
