package dev.anye.mc.srsl.enchantment.phenomenon.myriadphenomena;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.phenomenon.PhenomenonEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MyriadPhenomena extends PhenomenonEnchantment {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.MYRIAD_PHENOMENA);
    public MyriadPhenomena() {
        super(EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
    }
}
