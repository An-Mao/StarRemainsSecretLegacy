package dev.anye.mc.srsl.enchantment.neko.item.nekonight;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import dev.anye.mc.srsl.enchantment.neko.item.nekoday.NekoDay;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class NekoNight extends NekoEI {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_NIGHT);
    private final int maxLevel = EnchantmentsConfig.INSTANCE.getMaxLevel(EnchantmentRegister.NEKO_NIGHT);
    public NekoNight() {
        super(Rarity.RARE);
    }
    @Override
    protected boolean checkCompatibility(@NotNull Enchantment pOther) {
        return !(pOther instanceof NekoDay);
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
    @Override
    public boolean isTreasureOnly() {
        return false;
    }
}
