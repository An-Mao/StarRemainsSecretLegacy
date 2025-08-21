package dev.anye.mc.srsl.enchant.neko.item;

import dev.anye.mc.srsl.enchant.neko.NekoEC;
import net.minecraft.world.item.ItemStack;

public class NekoEI extends NekoEC {
    protected NekoEI() {
        super();
    }@Override
    public boolean canEnchant(ItemStack stack) {
        if (isWeapon(stack)) {
            return true;
        }
        return super.canEnchant(stack);
    }
}
