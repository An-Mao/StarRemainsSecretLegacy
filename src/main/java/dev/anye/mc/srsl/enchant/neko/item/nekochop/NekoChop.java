package dev.anye.mc.srsl.enchant.neko.item.nekochop;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.neko.item.NekoEI;

public class NekoChop extends NekoEI {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_CHOP);
    private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_CHOP);
    public NekoChop() {
        super();
    }
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}
