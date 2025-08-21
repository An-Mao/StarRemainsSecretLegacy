package dev.anye.mc.srsl.enchant.neko.armor;

import dev.anye.mc.srsl.enchant.neko.NekoEC;
import net.minecraft.world.item.ItemStack;

public class NekoEA extends NekoEC {
    protected NekoEA() {
        super();
    }@Override
    public boolean canEnchant(ItemStack stack) {
        if (isArmor(stack)) {
            return true;
        }
        return super.canEnchant(stack);
    }
}
