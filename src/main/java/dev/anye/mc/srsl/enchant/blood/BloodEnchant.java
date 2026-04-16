package dev.anye.mc.srsl.enchant.blood;

import dev.anye.mc.srsl.core.Enchant;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public class BloodEnchant extends Enchant {
    protected BloodEnchant() {
        super( ChatFormatting.DARK_RED);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        if (isWeapon(stack)) {
            return true;
        }
        return super.canEnchant(stack);
    }
}
