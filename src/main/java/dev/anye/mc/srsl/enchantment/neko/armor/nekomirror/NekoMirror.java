package dev.anye.mc.srsl.enchantment.neko.armor.nekomirror;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.armor.NekoEA;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class NekoMirror extends NekoEA {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_MIRROR);
    private final int maxLevel = EnchantmentsConfig.INSTANCE.getMaxLevel(EnchantmentRegister.NEKO_MIRROR);
    public NekoMirror() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem || super.canEnchant(pStack);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
