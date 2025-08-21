package dev.anye.mc.srsl.enchant.neko.item.nekoday;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.neko.item.NekoEI;

public class NekoDay extends NekoEI {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_DAY);
    private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_DAY);
    public NekoDay() {
        super();
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

}
