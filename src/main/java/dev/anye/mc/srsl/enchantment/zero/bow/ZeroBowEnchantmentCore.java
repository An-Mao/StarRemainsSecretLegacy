package dev.anye.mc.srsl.enchantment.zero.bow;

import dev.anye.mc.srsl.enchantment.zero.ZeroEnchantmentCore;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ZeroBowEnchantmentCore extends ZeroEnchantmentCore {
    public ZeroBowEnchantmentCore() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
