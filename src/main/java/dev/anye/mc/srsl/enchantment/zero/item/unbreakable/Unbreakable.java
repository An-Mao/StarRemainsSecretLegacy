package dev.anye.mc.srsl.enchantment.zero.item.unbreakable;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.item.ZeroItemE;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Unbreakable extends ZeroItemE {
    public static final String ENCHANTMENT_KEY_UNBREAKABLE = "unbreakable";
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.Z_UNBREAKABLE);
    private static final String TOOLTIPS_REFINE = "tooltips.srsl.refine";
    public Unbreakable() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getMaxDamage()>0;
    }

    @Override
    public @NotNull Component getFullname(int pLevel) {
        return super.getFullname(pLevel);
    }
}
