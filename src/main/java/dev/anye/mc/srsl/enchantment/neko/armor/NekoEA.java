package dev.anye.mc.srsl.enchantment.neko.armor;

import dev.anye.mc.srsl.enchantment.neko.NekoEC;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class NekoEA extends NekoEC {
    protected NekoEA(Rarity pRarity) {
        super(pRarity, EnchantmentCategory.ARMOR, EquipmentSlot.CHEST,EquipmentSlot.FEET,EquipmentSlot.HEAD,EquipmentSlot.LEGS);
    }
}
