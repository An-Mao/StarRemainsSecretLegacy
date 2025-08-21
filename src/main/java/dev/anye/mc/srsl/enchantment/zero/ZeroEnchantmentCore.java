package dev.anye.mc.srsl.enchantment.zero;

import dev.anye.mc.srsl.enchantment.EnchantmentCore;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ZeroEnchantmentCore extends EnchantmentCore {
    protected ZeroEnchantmentCore(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(ChatFormatting.DARK_PURPLE,pRarity, pCategory, pApplicableSlots);
    }
}
