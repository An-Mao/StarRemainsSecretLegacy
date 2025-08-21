package dev.anye.mc.srsl.enchantment.neko.item.nekochop;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;

public class NekoChop extends NekoEI {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_CHOP);
    private final int maxLevel = EnchantmentsConfig.INSTANCE.getMaxLevel(EnchantmentRegister.NEKO_CHOP);
    public NekoChop() {
        super(Rarity.RARE);
    }
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}
