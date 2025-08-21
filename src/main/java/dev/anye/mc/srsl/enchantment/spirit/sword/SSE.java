package dev.anye.mc.srsl.enchantment.spirit.sword;

import dev.anye.mc.srsl.enchantment.spirit.SpiritE;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SSE extends SpiritE {
    protected SSE(Enchantment.Rarity pRarity) {
        super(pRarity, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
    }
}
