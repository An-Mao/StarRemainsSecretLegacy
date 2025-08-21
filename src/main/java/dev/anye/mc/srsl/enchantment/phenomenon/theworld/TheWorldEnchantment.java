package dev.anye.mc.srsl.enchantment.phenomenon.theworld;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.phenomenon.PhenomenonEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TheWorldEnchantment extends PhenomenonEnchantment {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.THE_WORLD);
    public TheWorldEnchantment() {
        super(EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND,EquipmentSlot.MAINHAND,EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET);
    }

}
