package dev.anye.mc.srsl.enchantment.neko;

import dev.anye.mc.srsl.enchantment.EnchantmentCore;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class NekoEC extends EnchantmentCore {
    protected NekoEC(Rarity pRarity,EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(ChatFormatting.LIGHT_PURPLE,pRarity, pCategory, pApplicableSlots);
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
