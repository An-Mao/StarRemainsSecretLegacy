package dev.anye.mc.srsl.enchantment.zero.item;

import dev.anye.mc.srsl.enchantment.zero.ZeroEnchantmentCore;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ZeroItemE extends ZeroEnchantmentCore {
    protected ZeroItemE(Rarity pRarity) {
        super(pRarity, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
