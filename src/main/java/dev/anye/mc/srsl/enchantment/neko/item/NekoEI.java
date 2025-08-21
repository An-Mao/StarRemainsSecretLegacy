package dev.anye.mc.srsl.enchantment.neko.item;

import dev.anye.mc.srsl.enchantment.neko.NekoEC;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class NekoEI extends NekoEC {
    protected NekoEI(Rarity pRarity) {
        super(pRarity, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
    }
}
