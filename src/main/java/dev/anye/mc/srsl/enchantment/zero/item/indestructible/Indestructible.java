package dev.anye.mc.srsl.enchantment.zero.item.indestructible;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.item.ZeroItemE;
import net.minecraft.world.item.ItemStack;

public class Indestructible extends ZeroItemE {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.Z_INDESTRUCTIBLE);
    public Indestructible() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return true;
    }
}
