package dev.anye.mc.srsl.enchantment.neko.armor.nekoblessing;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.neko.armor.NekoEA;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class NekoBlessing extends NekoEA {
    public static final String ENCHANTMENT_KEY_BLESSING = "blessing";
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.NEKO_BLESSING);
    public NekoBlessing() {
        super(Rarity.VERY_RARE);
    }

    /*
    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.getEntity() instanceof ServerPlayer serverPlayer) {
            Iterable<ItemStack> slots = serverPlayer.getArmorSlots();
            int a = 1;
            for (ItemStack sl : slots) {
                int lvl = sl.getEnchantmentLevel(EnchantmentRegister.NEKO_BLESSING.get());
                a += lvl;
            }
            return a;
        }
        return 0;
    }
     */

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem || super.canEnchant(pStack);
    }
    @Override
    public int getMaxLevel() {
        return 5;
    }
}
