package dev.anye.mc.srsl.enchant.neko.item.nekonight;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.neko.item.NekoEI;

public class NekoNight extends NekoEI {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_NIGHT);
    private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_NIGHT);
    public NekoNight() {
        super();
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}
