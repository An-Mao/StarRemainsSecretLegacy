package dev.anye.mc.srsl.enchant.zero.item.indestructible;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.item.ZeroItemE;
import net.minecraft.world.item.ItemStack;

public class Indestructible extends ZeroItemE {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_INDESTRUCTIBLE);
    public Indestructible() {
        super();
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return true;
    }
}
