package dev.anye.mc.srsl.enchantment.neko.item.nekolove;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.item.NekoEI;
import net.minecraft.world.item.ItemStack;

public class NekoLove extends NekoEI {
    public static final String ENCHANTMENT_KEY_LOVE = "love";
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_LOVE);
    public NekoLove() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return true;
    }
}
