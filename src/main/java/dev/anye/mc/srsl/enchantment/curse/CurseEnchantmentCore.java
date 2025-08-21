package dev.anye.mc.srsl.enchantment.curse;

import dev.anye.mc.srsl.enchantment.EnchantmentCore;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CurseEnchantmentCore extends EnchantmentCore {
    protected CurseEnchantmentCore() {
        super(ChatFormatting.RED,Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return true;
    }
}
