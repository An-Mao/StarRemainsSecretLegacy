package dev.anye.mc.srsl.enchantment.zero.tool;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.ZeroEnchantmentCore;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ChainHarvesting extends ZeroEnchantmentCore {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.TORI_NO_UTA);
    public static final int radius = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.ChainHarvesting,"radius");
    public static final int depths = (int) EnchantmentsConfig.INSTANCE.getValue(EnchantmentRegister.ChainHarvesting,"depths");
    public ChainHarvesting() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND);
    }
}
