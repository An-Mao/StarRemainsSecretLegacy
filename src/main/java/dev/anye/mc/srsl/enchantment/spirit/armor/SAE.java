package dev.anye.mc.srsl.enchantment.spirit.armor;

import dev.anye.mc.srsl.enchantment.spirit.SpiritE;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SAE extends SpiritE {
    protected SAE(Enchantment.Rarity pRarity) {
        super(pRarity,  EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST);
    }
}
